package app;

import java.io.File;
// import java.io.BufferedWriter;
// import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.settings.ini.IIniConfig;
import app.settings.ini.Ini4jIIniConfig;

/**
 * Abstraction for program configuration values.
 * The Settings class defines default values and can import values from the settings file.
 * If the settings file is used default values are overridden.
 *
 * @author vitek
 *
 */
public class Settings extends Ini4jIIniConfig implements IIniConfig {
    private static final Logger logger = LoggerFactory.getLogger(Settings.class);

    public Settings() {
        super();
    }

    /**
     * Creates configuration file with default values.
     * Beware it will truncate any existing file first!
     *
     * @param filePath the file-path for the configuration file.
     * @throws IOException
     */
    public void createDefaultSettingsFile(String filePath) throws IOException {

        Files.writeString(Path.of(filePath), getDefaultSettingsAsText(),
                StandardOpenOption.TRUNCATE_EXISTING);
        // BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        // try {
        // writer.write(getDefaultSettingsAsText());
        // } catch(IOException e) {
        // throw e;
        // } finally {
        // writer.close();
        // }
    }

    /**
     * Loads the configuration file and overrides any existing values
     * with those from the configuration file.
     *
     * @param file the configuration file
     * @throws InvalidFileFormatException
     * @throws IOException
     */
    public void loadSettingsFile(File file) throws InvalidFileFormatException, IOException {
        this.load(file);
    }

    /**
     * This method checks if each and every value id non-null.
     *
     * @return true if every value is non-null. False if any value is null.
     */
    public boolean checkExistenceOfIniConfigValues() {
        for (String section : ini.keySet()) { // for section
            Ini.Section iniSection = ini.get(section);
            for (String key : iniSection.keySet()) { // for section.key
                String value = iniSection.get(key);
                if (value == null) {
                    logger.warn("The configuration parameter <{}.{}> hasnt been properly set!",
                            section, key);
                    return false;
                }
            }
        }
        return true;
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
                " = " + DefaultValues.SETTINGS_LOCAL_PHOTO_FOLDER,
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
