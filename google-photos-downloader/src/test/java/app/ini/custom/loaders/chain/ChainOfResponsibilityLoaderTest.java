package app.ini.custom.loaders.chain;

import app.ini.InvalidConfigFileFormatException;
import app.ini.custom.CustomIIniConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

class ChainOfResponsibilityLoaderTest {

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
        ini.setLoader(new ChainOfResponsibilityLoader());
        ini.load(new BufferedReader(reader));

        String actual = ini.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testLoadReaderErrorUnknownCommentSymbol() {
        String input = String.join(System.lineSeparator(),
                "; my section comment",
                "[section]",
                "key = value");

        StringReader reader = new StringReader(input);
        CustomIIniConfig ini = new CustomIIniConfig();
        ini.setLoader(new ChainOfResponsibilityLoader());

        Assertions.assertThrows(InvalidConfigFileFormatException.class,
                () -> ini.load(new BufferedReader(reader)));

    }

    @Test
    void testLoadReaderParsingErrorItemBeforeFirstSection() {
        String input = String.join(System.lineSeparator(),
                "# header comment",
                "",
                "# the item comment that should have been the section one",
                "key = value",
                "[section]",
                "key = value");

        StringReader reader = new StringReader(input);
        CustomIIniConfig ini = new CustomIIniConfig();
        ini.setLoader(new ChainOfResponsibilityLoader());

        Assertions.assertThrows(InvalidConfigFileFormatException.class,
                () -> ini.load(new BufferedReader(reader)));

    }

}
