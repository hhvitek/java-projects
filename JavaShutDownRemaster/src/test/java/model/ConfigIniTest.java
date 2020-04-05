package model;

import ini.InvalidConfigFileFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigIniTest {

    @Test
    void getClassNamesTest() throws IOException, InvalidConfigFileFormatException {

        String configPath = "configuration_test.ini";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(configPath).getFile());

        ConfigIni ini = new ConfigIni();
        ini.load(file);

        String expectedOutput = "NoAction<>ShutDownAction";
        List<String> actions = ini.getClassNames();
        String actualOutput = String.join("<>", actions);

        Assertions.assertEquals(expectedOutput, actualOutput);

    }
}
