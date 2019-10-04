package app;

import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArgsParser {

    private final CommandLineParser parser = new DefaultParser();
    private final Logger logger = LoggerFactory.getLogger(ArgsParser.class);

    private Options options;

    public Optional<CommandLine> parse(String[] args) {

        options = initArgsParseOptions();
        CommandLine cmdLine;

        try {
            // parse the command line arguments
            cmdLine = parser.parse(options, args);
            processCommandLineOptions(cmdLine);
            return Optional.of(cmdLine);
        }
        catch(ParseException exp) {
            // oops, something went wrong
            logger.error("Parsing failed. Reason: {}", exp.getMessage());
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
        formatter.printHelp( "GooglePhotos downloader", options );
    }

    private Options initArgsParseOptions() {
        Options options = new Options();
        options.addOption( "c", "create-configfile", false, "Creates the default " +
                           "config file and exits the program.");
        options.addOption( "h", "help", false, "Shows this message.");

        options.addOption(
                Option.builder("p")
                    .longOpt("settings-filepath")
                    .hasArg()
                    .argName("path")
                    .desc("Path to the settings file.")
                    .build()
                );
        return options;
    }



}
