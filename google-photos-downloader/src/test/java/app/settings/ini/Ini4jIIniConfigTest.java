package app.settings.ini;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Ini4jIIniConfigTest {

    private Ini4jIIniConfig iniConfig;

    @BeforeEach
    void init() {
        iniConfig = new Ini4jIIniConfig();
        Reader reader = new StringReader(this.getDefaultConfigText());
        try {
            iniConfig.load(reader);
        } catch (IOException e) {
            fail(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                fail(e);
            }
        }
    }

    @Test
    void testGetSection() {

        Assertions.assertAll("GetSection",
                () -> Assertions.assertNull(iniConfig.getSection("database"),
                        "If section doesnt exist, returns null"),
                () -> Assertions.assertNotNull(iniConfig.getSection("database"),
                        "If section does exist, returns names section"));

    }

    @Test
    void testGetValue() {

        Assertions.assertEquals("2017-11-21", iniConfig.getValue("database", "start_date"));
        Assertions.assertNull(iniConfig.getValue("non_exist_section", "start_date"));
        Assertions.assertNull(iniConfig.getValue("database", "non_exist_key"));
        Assertions.assertNull(iniConfig.getValue("database", "local_photos_folder"));

    }

    @Test
    void testContainsKey() {
        fail("Not yet implemented");
    }

    @Test
    void testContainsSection() {
        fail("Not yet implemented");
    }

    @Test
    void testLoadFile() {
        fail("Not yet implemented");
    }

    @Test
    void testLoadReader() {
        fail("Not yet implemented");
    }

    @Test
    void testPutCommentStringString() {
        fail("Not yet implemented");
    }

    @Test
    void testPutCommentStringStringString() {
        fail("Not yet implemented");
    }

    @Test
    void testPutValue() {
        fail("Not yet implemented");
    }

    @Test
    void testStore() {
        fail("Not yet implemented");
    }

    @Test
    void testToString() {
        fail("Not yet implemented");
    }

    private String getDefaultConfigText() {
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
                "local_photos_folder = ",
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
