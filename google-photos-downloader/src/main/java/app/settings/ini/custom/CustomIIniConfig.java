package app.settings.ini.custom;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.settings.ini.IIniConfig;
import app.settings.ini.IIniSection;
import app.settings.ini.InvalidConfigFileFormatException;
import app.settings.ini.custom.loaders.ILoader;
import app.settings.ini.custom.loaders.fsm.LineLoader;

/**
 * Base class for representation of INI configurations.
 */
public class CustomIIniConfig implements IIniConfig, Iterable<IIniSection> {

    private static final Logger logger = LoggerFactory.getLogger(CustomIIniConfig.class);

    // ready for extension, might be used by child class. Such as Setting.
    protected Map<String, IIniSection> sections = new LinkedHashMap<>();

    private String headerComment;
    private ILoader loader = new LineLoader();

    @Override
    public IIniSection getSection(String sectionName) {
        if (sectionName == null) {
            logger.warn("Used \"null\" as an sectionName parameter.");
            return null;
        }

        return sections.get(sectionName);
    }

    public void setLoader(ILoader newLoader) {
        this.loader = newLoader;
    }

    @Override
    public String getValue(String sectionName, String key) {
        if (sectionName == null) { // the null sectionName indicates, user wants headerComment
            return headerComment;
        }

        if (containsSection(sectionName)) {
            IIniSection section = sections.get(sectionName);
            if (key == null) // the null key indicates, user wants section comment
                return section.getComment(key);
            else {
                return section.getValue(key);
            }
        }

        return null;
    }

    @Override
    public boolean containsKey(String sectionName, String key) {
        if (containsSection(sectionName)) {
            return sections.get(sectionName).containsKey(key);
        }
        return false;
    }

    @Override
    public boolean containsSection(String sectionName) {
        if (sectionName == null)
            return false;
        if (sections.get(sectionName) == null)
            return false;
        return true;
    }

    @Override
    public void load(File file) throws IOException, InvalidConfigFileFormatException {
        loader.load(this, file);
    }

    @Override
    public void load(BufferedReader reader) throws IOException, InvalidConfigFileFormatException {
        loader.load(this, reader);
    }

    @Override
    public void putComment(String sectionName, String comment) {
        putComment(sectionName, null, comment);
    }

    @Override
    public void putComment(String sectionName, String key, String comment) {
        if (sectionName == null) // input the headerComment
            this.headerComment = comment;
        else {
            if (containsSection(sectionName)) {
                sections.get(sectionName).putComment(key, comment);
            } else {
                IIniSection newSection = new CustomIIniSection(sectionName);
                newSection.putComment(key, comment);
                sections.put(sectionName, newSection);
            }
        }
    }

    @Override
    public void putValue(String sectionName, String key, String value) {
        if (sectionName == null || key == null) { // lets behave like we are putting a new comment.
            putComment(sectionName, key, value);
        } else {
            if (containsSection(sectionName)) {
                sections.get(sectionName).putValue(key, value);
            } else {
                IIniSection newSection = new CustomIIniSection(sectionName);
                newSection.putValue(key, value);
                sections.put(sectionName, newSection);
            }
        }
    }

    @Override
    public void store(File file) throws IOException {
        if (file == null) {
            logger.error("Cannot save the configuration file. The parameter file is null.");
        } else {
            // Files.writeString
            // try PrintWriter FileWriter print
            Files.write(file.toPath(),
                    toString().getBytes());
        }

    }

    public String toString() {
        String toStr = "";
        if (headerComment != null && !headerComment.isBlank()) {
            toStr += createToStringComment(headerComment) + System.lineSeparator();
            toStr += System.lineSeparator(); // empty line
        }

        for (IIniSection section : sections.values()) {
            toStr += section.toString() + System.lineSeparator();
        }

        return toStr.strip();
    }

    @Override
    public String getComment(String sectionName, String key) {
        if (sectionName == null) {
            return headerComment;
        }

        if (containsKey(sectionName, key)) {
            return getSection(sectionName).getComment(key);
        }

        return null;
    }

    @Override
    public String getHeaderComment() {
        return headerComment;
    }

    @Override
    public void putSection(String sectionName) {
        IIniSection section;
        if ((section = getSection(sectionName)) == null) {
            section = new CustomIIniSection(sectionName);
            sections.put(sectionName, section);
        }

    }

    @Override
    public void putHeaderComment(String comment) {
        this.headerComment = comment;

    }

    public static String createToStringComment(String multiline) {
        if (multiline == null || multiline.isBlank()) {
            return multiline;
        }

        return multiline.lines()
                .map("# "::concat)
                .reduce((x, y) -> x + System.lineSeparator() + y)
                .get();
    }

    @Override
    public Iterator<IIniSection> iterator() {
        return sections.values().iterator();
    }

}
