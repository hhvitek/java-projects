package app;

import app.ini.InvalidConfigFileFormatException;
import app.view.ConsoleView;
import app.view.Controller;
import app.view.IView;
import app.view.SwingView;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * This application downloads new photos from Google Photos
 * library according to the configuration file: settings.ini. Should the
 * error occur, an email is sent.
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
            throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException,
                   IllegalAccessException {

        logger.info("Application has started.");

        ArgsParser argsParser = new ArgsParser();
        Optional<CommandLine> line = argsParser.parse(args);
        if (line.isEmpty()) { // Failed to parse CMD arguments.
            logErrorAndExit("Failed to parse CMD.");
        }

        if (logger.isInfoEnabled()) {
            // Arrays.toString(args) will always run even if logger.debug is not.
            logger.info("Application cmd parsing successful: {}", Arrays.toString(args));
        }

        CommandLine cmdLine = line.get();

        Settings settings = new Settings();
        String settingsFilePath =
                cmdLine.getOptionValue("settings-filepath", DefaultValues.SETTINGS_FILEPATH);

        if (cmdLine.hasOption("create-configfile")) {
            try {
                settings.createDefaultSettingsFile(settingsFilePath);
                logger.info("Settings file created successfully: {}", settingsFilePath);
                logger.info("Application finished successfully.");
            } catch (IOException e) {
                logErrorAndExit("IOException cannot create setting file.", e);
            } finally {
                return;
            }
        }

        try {
            settings.loadSettingsFile(new File(settingsFilePath));
        } catch (InvalidConfigFileFormatException e) {
            logErrorAndExit(
                    "Failed to load configuration file. The invalid configuration file format.");
        } catch (IOException e) {
            logErrorAndExit("Failed to load configuration file.", e);
        }

        IView view;
        if (settings.isConsole()) {
            view = new ConsoleView();
        } else {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            view = new SwingView();
        }

        Controller controller = new Controller();
        view.setConfigurationFilePath(settingsFilePath);
        view.setLocalFolder(settings.getLocalPhotosFolder());
        controller.setView(view);
        view.setController(controller);
        view.startView();

    }

    private static void logErrorAndExit(String errorText) {
        logger.error(errorText);
        System.exit(1);
    }

    private static void logErrorAndExit(String errorText, Throwable t) {
        logger.error(errorText, t);
    }

}
