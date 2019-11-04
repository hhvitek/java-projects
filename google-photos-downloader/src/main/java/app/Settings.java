package app;

import app.ini.IIniConfig;
import app.ini.InvalidConfigFileFormatException;
import app.ini.custom.CustomIIniConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * <p>
 * Abstraction for program configuration values.
 * The Settings class defines default values and can import values from the settings file.
 * If the settings file is loaded default values are overridden.
 * <p>
 * Inherits from CustomIIniConfig class
 */
public class Settings extends CustomIIniConfig implements IIniConfig {
    private static final Logger logger = LoggerFactory.getLogger(Settings.class);

    public Settings() {
        super();
    }

    /**
     * Creates configuration file with default values.
     * Beware it will truncate any existing file first!
     *
     * @param filePath the file-path for the configuration file.
     *
     * @throws IOException
     */
    public void createDefaultSettingsFile(String filePath) throws IOException {

        Files.writeString(Path.of(filePath), getDefaultSettingsAsText(),
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Loads the configuration file and overrides any existing values
     * with those from the configuration file.
     *
     * @param file the configuration file
     *
     * @throws InvalidConfigFileFormatException
     * @throws IOException
     */
    public void loadSettingsFile(File file) throws InvalidConfigFileFormatException, IOException {
        this.load(file);
    }

    /**
     * Defines default configuration file. Used also as an input for default values.
     *
     * @return default configuration file as a {@code String}
     */
    private String getDefaultSettingsAsText() {
        return String.join(System.lineSeparator(),
                "# This application will download all newer photos (than start_date) "
                        + "from GooglePhotos library",
                "# to the specified folder using defined credentials.",
                "# This file represents:",
                "# * the configuration of application.",
                "# * the \"database\" of the application.",
                "",
                "[configuration]",
                "# the location to download new photos to",
                "local_photos_folder = " + DefaultValues.SETTINGS_LOCAL_PHOTO_FOLDER,
                "",
                "# gmail account credentials",
                "# this account is used to send emails with errors encoutered",
                "source_gmail_username = ",
                "source_gmail_password = ",
                "",
                "# specify an email where to send error emails",
                "destination_email_address = ",
                "",
                "# clientId and clientSecret from the credentials.json file.",
                "client_id = ",
                "client_secret = ",
                "",
                "[database]",
                "# https://photos.google.com/ credentials use playground",
                "remote_googlephotos_refreshtoken = ",

                "# Determine last photo downloaded",
                "start_date = 2017-11-21",
                "",
                "# Determine if the last time application encountered an error.",
                "was_error = ");

    }

}
