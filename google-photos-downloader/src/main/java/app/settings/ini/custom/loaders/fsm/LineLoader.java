package app.settings.ini.custom.loaders.fsm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.settings.ini.IIniConfig;
import app.settings.ini.InvalidConfigFileFormatException;
import app.settings.ini.custom.loaders.ILoader;

/**
 * For the detailed description of configuration file see:
 * {@link ILoader}
 *
 * This is the concrete implementation of the above interface. This is one-file implementation.
 * Using as straight forward approach as possible
 * If-else is used to determine how the input line should be processed.
 * the state is spread across all the methods, which is the other downside.
 *
 * @author vitek
 *
 */
public class LineLoader implements ILoader {

    private final Logger logger = LoggerFactory.getLogger(LineLoader.class);

    private IIniConfig ini;

    // ##### STATE VARIABLES

    // FSM last accumulated comments
    // List... because it could be multiline comment
    private List<String> activeComment = new ArrayList<>();

    // FSM last section encountered
    // Used for the item mapping -> section+key=value
    private String activeSection = "";

    // FSM configuration file header comment...
    // true until the blank line or the section encountered
    private boolean isHeaderCommentPosible = true;

    // ##### END STATE VARIABLES

    // FSM patterns to parse encountered lines
    private final Pattern commentPattern = Pattern.compile("#\\s*(.*)");
    private final Pattern sectionPattern = Pattern.compile("\\[([a-zA-Z_0-9]+)\\]");
    private final Pattern itemPattern = Pattern.compile("([a-zA-Z_0-9]+)\\s*=\\s*(.*)");

    // clear FSM state
    private void init() {
        activeComment.clear();
        activeSection = "";
        isHeaderCommentPosible = true;
    }

    @Override
    public void load(IIniConfig ini, File file)
            throws IOException, InvalidConfigFileFormatException {

        init();
        this.ini = ini;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            load(ini, reader);
        }
    }

    @Override
    public void load(IIniConfig ini, BufferedReader reader)
            throws IOException, InvalidConfigFileFormatException {

        init();
        this.ini = ini;

        String line = null;
        while ((line = reader.readLine()) != null) {
            if (!processLine(line)) {
                throw new InvalidConfigFileFormatException(
                        "Parsing error encountered around the line: " + line);
            }
        }
    }

    /**
     * Process the current line in the input. Expects either comment, blank line, section or
     * item line {@link #LineLoader}
     *
     * @param line
     * @return
     */
    private boolean processLine(String line) {
        if (line.isBlank()) {
            return processEmptyLine();
        } else if (line.startsWith("#")) {
            return processCommentLine(line);
        } else if (line.startsWith("[")) {
            return processSectionLine(line);
        } else {
            return processItemLine(line);
        }
    }

    private boolean processCommentLine(String line) {
        Matcher m = commentPattern.matcher(line);
        if (m.matches()) {
            activeComment.add(m.group(1));
            return true;
        }
        return false;
    }

    private boolean processEmptyLine() {
        if (isHeaderCommentPosible && !activeComment.isEmpty()) {
            ini.putHeaderComment(String.join(System.lineSeparator(), activeComment));
            activeComment.clear();
        }

        isHeaderCommentPosible = false;
        return true;
    }

    private boolean processSectionLine(String line) {
        isHeaderCommentPosible = false;

        Matcher m = sectionPattern.matcher(line);
        if (m.matches()) {
            String sectionName = m.group(1);
            activeSection = sectionName;
            ini.putComment(sectionName, String.join(System.lineSeparator(), activeComment));
            activeComment.clear();
            return true;
        }

        return false;
    }

    private boolean processItemLine(String line) {
        if (activeSection != null && !activeSection.isBlank()) { // item line have to be
                                                                 // "inside" the section"
            Matcher m = itemPattern.matcher(line);
            if (m.matches()) { // it is "key = value" line
                String key = m.group(1);
                String value = m.group(2);
                ini.putValue(activeSection, key, value);
                ini.putComment(activeSection, key,
                        String.join(System.lineSeparator(), activeComment));
                activeComment.clear();
                return true;
            }
        }
        return false;
    }

}
