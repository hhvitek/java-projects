/**
 *
 */
package app;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.photos.GooglePhotos;
import app.settings.ini.InvalidConfigFileFormatException;

/**
 * @author vitek
 * This application downloads new photos from Google Photos library
 * according to the configuration file: settings.ini.
 * Should the error occur, an email is sent.
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        logger.info("Application has started.");

        ArgsParser argsParser = new ArgsParser();
        Optional<CommandLine> line = argsParser.parse(args);
        if (line.isEmpty()) { // Failed to parse CMD arguments.
            logErrorAndExit("Failed to parse CMD.");
        }

        if (logger.isDebugEnabled()) {
            // Arrays.toString(args) will always run even if logger.debug is not.
            logger.debug("Application cmd parsing successfull: {}", Arrays.toString(args));
        }

        CommandLine cmdLine = line.get();

        Settings settings = new Settings();
        String settingsFilePath = cmdLine.getOptionValue("settings-filepath",
                DefaultValues.SETTINGS_FILEPATH);

        if (cmdLine.hasOption("create-configfile")) {
            try {
                settings.createDefaultSettingsFile(settingsFilePath);
                logger.info("Settings file created successfully: {}", settingsFilePath);
                logger.info("Application finished succesfully.");
                return;
            } catch (IOException e) {
                logErrorAndExit("IOException cannot create setting file.", e);
            }
        }

        try {
            settings.load(new File(settingsFilePath));
        } catch (IOException | InvalidConfigFileFormatException e) {
            logErrorAndExit("Failed to load configuration file.", e);
        }
        
        GooglePhotos gPhotos = new GooglePhotos();
        gPhotos.downloadPhotos(accessTokenString, lastDate, localPhotoFolder);

        logger.info("Application finished succesfully.");
    }

    private static void logErrorAndExit(String errorText) {
        logger.error(errorText);
        System.exit(1);
    }

    private static void logErrorAndExit(String errorText, Throwable t) {
        logger.error(errorText, t);
    }

}
