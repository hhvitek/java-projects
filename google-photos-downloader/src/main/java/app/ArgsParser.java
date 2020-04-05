package app;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Parse command line arguments, prints help message if any parsing error encountered.
 */
public class ArgsParser {

    private final        CommandLineParser parser = new DefaultParser();
    private static final Logger            logger = LoggerFactory.getLogger(ArgsParser.class);

    private Options options;

    /**
     * The sole method of this class. Parse command line arguments.
     * Prints help if any error is encountered.
     *
     * @param args standard Java command line String array.
     *
     * @return Optional of CommandLine this is the type from the apache "commons-cli" library.
     */
    public Optional<CommandLine> parse(String[] args) {
        options = initArgsParseOptions();
        CommandLine cmdLine;
        try {
            // parse the command line arguments
            cmdLine = parser.parse(options, args);
            processCommandLineOptions(cmdLine);
            return Optional.of(cmdLine);
        } catch (ParseException exp) {
            // oops, something went wrong
            logger.error("Parsing failed.", exp);
            printHelp();
            return Optional.empty();
        }
    }

    private void processCommandLineOptions(CommandLine line) {
        if (line.hasOption("help")) {
            printHelp();
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("GooglePhotos downloader", options);
    }

    private Options initArgsParseOptions() {
        Options options = new Options();
        options.addOption("c", "create-configfile", false,
                          "Creates the default " + "config file and exits the program."
        );
        options.addOption("h", "help", false, "Shows this message.");

        options.addOption(Option.builder("p")
                                .longOpt("settings-filepath")
                                .hasArg()
                                .argName("path")
                                .desc("Path to the settings file.")
                                .build());
        return options;
    }

}
