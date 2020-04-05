package app;

import app.ini.IIniConfig;
import app.ini.InvalidConfigFileFormatException;
import app.ini.myini.CustomIIniConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>
 * Abstraction for program configuration values.
 * The Settings class defines default values and can import values from the settings file.
 * If the settings file is loaded default values are overridden.
 * <p>
 * Inherits from CustomIIniConfig class
 */
public final class Settings {

    private static final Logger     logger = LoggerFactory.getLogger(Settings.class);
    private final        IIniConfig ini    = new CustomIIniConfig();

    /**
     * Creates configuration file with default values.
     * Beware it will truncate any existing file first!
     *
     * @param filePath the file-path for the configuration file.
     *
     * @throws IOException working with the file system.
     */
    public void createDefaultSettingsFile(String filePath)
            throws IOException {

        Files.writeString(Path.of(filePath), getDefaultSettingsAsText());
    }

    /**
     * Loads the configuration file and overrides any existing values
     * with those from the configuration file.
     *
     * @param file the configuration file
     *
     * @throws InvalidConfigFileFormatException the configuration file is in an invalid format.
     * @throws IOException                      working with the file system
     */
    public void loadSettingsFile(File file)
            throws InvalidConfigFileFormatException, IOException {
        ini.load(file);
    }

    //###APPLICATION

    public boolean isSwing() {
        if (ini.hasItem("application", "ui")) {
            String ui = ini.getValue("application", "ui");
            return "swing".equals(ui);
        }
        return false;
    }

    public boolean isConsole() {
        String ui = ini.getValue("application", "ui");
        if (ui != null) {
            return "console".equals(ui);
        }
        return false;
    }

    //###CONFIGURATION

    public String getLocalPhotosFolder() {
        return ini.getValue("configuration", "local_photos_folder");
    }

    public String getClientId() {
        return ini.getValue("configuration", "client_id");
    }

    public String getClientSecret() {
        return ini.getValue("configuration", "client_secret");
    }

    //###DATABASE

    public String getRefreshToken() {
        return ini.getValue("database", "remote_googlephotos_refreshtoken");
    }

    public String getStartDate() {
        return ini.getValue("database", "start_date");
    }

    public String getEndDate() {
        return ini.getValue("database", "end_date");
    }

    /**
     * Defines default configuration file. Used also as an input for default values.
     *
     * @return default configuration file as a {@code String}
     */
    private String getDefaultSettingsAsText() {
        return String.join(System.lineSeparator(),
                           "# This application will download all newer photos (than start_date) " +
                           "from GooglePhotos library",
                           "# to the specified folder using defined credentials.",
                           "# This file represents:", "# * the configuration of application.",
                           "# * the \"database\" of the application.", "", "[application]",
                           "# swing/console", "ui = swing", "", "[configuration]",
                           "# the location to download new photos to",
                           "local_photos_folder = " + DefaultValues.SETTINGS_LOCAL_PHOTO_FOLDER, "",
                           "# gmail account credentials",
                           "# this account is used to send emails with errors encountered",
                           "source_gmail_username = ", "source_gmail_password = ", "",
                           "# specify an email where to send error emails",
                           "destination_email_address = ", "", "# clientId and clientSecret.",
                           "client_id = <FILL_IN>", "client_secret = <FILL_IN>", "", "[database]",
                           "# https://photos.google.com/ credentials use playground",
                           "remote_googlephotos_refreshtoken = <FILL_IN>", "",
                           "# Determine last photo downloaded", "start_date = 2017-11-21", "",
                           "# Determine if the last time application encountered an error.",
                           "was_error = "
        );
    }

}
