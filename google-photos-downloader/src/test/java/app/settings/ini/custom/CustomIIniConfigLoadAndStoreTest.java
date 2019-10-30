package app.settings.ini.custom;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import app.settings.ini.InvalidConfigFileFormatException;

class CustomIIniConfigLoadAndStoreTest {

    @Test
    void testLoadFile() throws InvalidConfigFileFormatException, IOException {
        File file = new File("src/test/resources/app/settings/ini/custom/default_config_ini.ini");
        CustomIIniConfig ini = new CustomIIniConfig();
        ini.load(file);

        String actual = ini.toString();
        String expected = Files.readString(file.toPath());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testLoadReader() throws InvalidConfigFileFormatException, IOException {
        String expected = String.join(System.lineSeparator(),
                "# my header comment 1st line.",
                "# my header comment 2nd line.",
                "",
                "# my first section comment.",
                "[first_section]",
                "first_key = first_value",
                "second_key = second_value",
                "",
                "[second_section]",
                "# the second section, first item comment.",
                "first_ket = first_value_second_section");
        StringReader reader = new StringReader(expected);

        CustomIIniConfig ini = new CustomIIniConfig();
        ini.load(new BufferedReader(reader));

        String actual = ini.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testLoadReaderErrorUnknownCommentSymbol()
            throws InvalidConfigFileFormatException, IOException {
        String input = String.join(System.lineSeparator(),
                "; my section comment",
                "[section]",
                "key = value");

        StringReader reader = new StringReader(input);
        CustomIIniConfig ini = new CustomIIniConfig();

        Assertions.assertThrows(InvalidConfigFileFormatException.class,
                () -> ini.load(new BufferedReader(reader)));

    }

    @Test
    void testLoadReaderParsingErrorItemBeforeFirstSection()
            throws InvalidConfigFileFormatException, IOException {
        String input = String.join(System.lineSeparator(),
                "# header comment",
                "",
                "# the item comment that should have been the section one",
                "key = value",
                "[section]",
                "key = value");

        StringReader reader = new StringReader(input);
        CustomIIniConfig ini = new CustomIIniConfig();

        Assertions.assertThrows(InvalidConfigFileFormatException.class,
                () -> ini.load(new BufferedReader(reader)));

    }

    @Test
    void testStore() throws IOException {
        File actualFile = new File(
                "src/test/resources/app/settings/ini/custom/created_config_ini.ini");
        File expectedFile = new File(
                "src/test/resources/app/settings/ini/custom/default_config_ini.ini");

        CustomIIniConfig ini = new CustomIIniConfig();
        ini.load(expectedFile);

        ini.store(actualFile);

        byte[] actual = Files.readAllBytes(actualFile.toPath());
        byte[] expected = Files.readAllBytes(expectedFile.toPath());

        Files.delete(actualFile.toPath());

        Assertions.assertEquals(expected, actual, "Files do not match.");
    }

}
