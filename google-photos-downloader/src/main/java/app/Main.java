package app;

import app.photos.GooglePhotos;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author vitek This application downloads new photos from Google Photos
 * library according to the configuration file: settings.ini. Should the
 * error occur, an email is sent.
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

        if (logger.isInfoEnabled()) {
            // Arrays.toString(args) will always run even if logger.debug is not.
            logger.info("Application cmd parsing successfull: {}", Arrays.toString(args));
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
        } catch (IOException e) {
            logErrorAndExit("Failed to load configuration file.", e);
        }


        GooglePhotos gPhotos;
        try {
            gPhotos = GooglePhotos.newBuilder()
                    .setRefreshToken(
                            settings.getValue("database", "remote_googlephotos_refreshtoken"))
                    .setLocalPhotoFolder(
                            Path.of(settings.getValue("configuration", "local_photos_folder")))
                    .setStartDate(settings.getValue("database", "start_date"))
                    .setClientId(settings.getValue("configuration", "client_id"))
                    .setClientSecret(settings.getValue("configuration", "client_secret"))
                    .build();

        } catch (IllegalArgumentException | DateTimeParseException e) {
            logger.error("Failed to create GooglePhotos object. ", e);
            return;
        } catch (IllegalStateException | NullPointerException e) {
            logger.error("Failed to create GooglePhotos object. ", e);
            return;
        }

        if (!gPhotos.downloadPhotos()) {
            System.err.println(gPhotos.getLastError());
        }


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
