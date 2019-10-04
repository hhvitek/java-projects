package app;

import java.io.File;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.settings.ini.IIniConfig;
import app.settings.ini.Ini4jIIniConfig;


public class Settings extends Ini4jIIniConfig implements IIniConfig {
    private static final Logger logger = LoggerFactory.getLogger(Settings.class);

    public Settings() {
        super();
    }

    public void createDefaultSettingsFile(String filePath) throws IOException {

        Files.writeString(
                Path.of(filePath),
                getDefaultSettingsAsText(),
                StandardOpenOption.TRUNCATE_EXISTING
        );
//        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
//        try {
//            writer.write(getDefaultSettingsAsText());
//        } catch(IOException e) {
//            throw e;
//        } finally {
//            writer.close();
//        }
    }

    public void loadSettingsFile(File file) throws InvalidFileFormatException, IOException {
        this.load(file);
    }

    private Ini getDefaultSettings() {
        try {
            ini.load(new StringReader(getDefaultSettingsAsText()));
        } catch (IOException e) {
            logger.error("Should never happen!!!, Failed to load default settings file", e);
            e.printStackTrace();
            return null;
        }
        return ini;
    }

    private String getDefaultSettingsAsText() {
        return String.join(System.lineSeparator(),
        "# This application will download all newer photos from GooglePhotos library",
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
        "[database]",
        "# https://photos.google.com/ credentials",
        "remote_googlephotos_accesstoken = ",

        "# Determine last photo downloaded",
        "last_photo_id = ",
        "last_photo_name = ",
        "last_photo_timestamp = ",
        "",
        "# Determine if the last time application encountered an error.",
        "was_error = "
        );

    }

}
