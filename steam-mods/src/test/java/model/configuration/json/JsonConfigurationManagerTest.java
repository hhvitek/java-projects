package model.configuration.json;

import model.configuration.*;
import model.configuration.json.pojo.JsonAppPojo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class JsonConfigurationManagerTest {

    private static Path standardConfigurationFilePath;
    private static Path invalidConfigurationFilePath;
    private static Path unknownConfigurationFilePath;
    private static Path missingConfigurationFilePath;
    private static Path outputFolder;

    private FileConfigurationManager manager;

    public JsonConfigurationManagerTest() {

    }

    @BeforeAll
    private static void staticInit() {
        standardConfigurationFilePath = getConfigurationFilePathFromResource("configuration_standard_test.json");
        invalidConfigurationFilePath = getConfigurationFilePathFromResource("configuration_invalid_test.json");
        unknownConfigurationFilePath = getConfigurationFilePathFromResource("configuration_unknown_test.json");
        missingConfigurationFilePath = getConfigurationFilePathFromResource("configuration_missing_test.json");
        outputFolder = getConfigurationFilePathFromResource("output");
    }

    private static Path getConfigurationFilePathFromResource(String resourceName) {
        Path testResources = Paths.get("src", "test", "resources");
        Path jsonResourcesFolder = testResources.resolve("model/configuration/json");
        Path configurationFilePath = jsonResourcesFolder.resolve(resourceName);

        Assertions.assertNotNull(configurationFilePath);
        Assertions.assertTrue(Files.isReadable(configurationFilePath), "Resource file cannot be found: " + resourceName);

        return configurationFilePath;
    }

    @Test
    public void standardFileConfigurationLoadTest() throws IOException, ConfigurationException {
        manager = new JsonConfigurationManager(standardConfigurationFilePath);
        manager.load();

        standardFileCheckApp();
        standardFileCheckModifications();
        standardFileCheckChains();
    }

    private void standardFileCheckApp() {
        AppConfiguration appConfiguration = manager.getAppConfiguration();
        Path expectedPath = Path.of("%USERPROFILE%", "Documents");
        Assertions.assertEquals(expectedPath, appConfiguration.getDefaultModFolder());
    }

    private void standardFileCheckModifications() {
        List<ModificationsConfiguration> modifications = manager.getModifications();
        Assertions.assertEquals(3, modifications.size());
        ModificationsConfiguration theFirstModification = modifications.get(0);
        Assertions.assertEquals("EXTRACT_ALL", theFirstModification.getId());
    }

    private void standardFileCheckChains() {
        List<ModificationsChainConfiguration> modChains = manager.getModificationsChain();
        Assertions.assertEquals(1, modChains.size());
        ModificationsChainConfiguration theFirstModChain = modChains.get(0);
        Assertions.assertEquals("BASIC", theFirstModChain.getId());

        List<String> modificationsIds = theFirstModChain.getModificationsIds();
        List<String> expectedModificationsIds = List.of(
                "EXTRACT_ALL",
                "MODIFY_MOD_DESCRIPTOR_FILES",
                "COPY_MOD_DESCRIPTOR_FILES"
        );

        Assertions.assertEquals(expectedModificationsIds, modificationsIds
        );
    }

    @Test
    public void invalidFileConfigurationLoadTest() {
        manager = new JsonConfigurationManager(invalidConfigurationFilePath);

        Assertions.assertThrows(ConfigurationException.class,
                () -> manager.load()
        );
    }

    @Test
    public void missingFileConfigurationLoadTest() {
        manager = new JsonConfigurationManager(missingConfigurationFilePath);

        Assertions.assertThrows(ConfigurationException.class,
                () -> manager.load()
        );
    }

    @Test
    public void uninitializedManagerUsageTest() {
        manager = new JsonConfigurationManager(standardConfigurationFilePath);

        Assertions.assertThrows(NotInitializedException.class,
                () -> manager.getModifications()
        );
    }

    @Test
    public void unknownFileConfigurationLoadTest() {
        manager = new JsonConfigurationManager(unknownConfigurationFilePath);

        Assertions.assertThrows(ConfigurationException.class,
                () -> manager.load()
        );
    }

    //####################SAVE##########################################
    @Test
    public void saveStandardWithoutChangeTest() throws IOException, ConfigurationException {
        manager = new JsonConfigurationManager(standardConfigurationFilePath);
        manager.load();

        Path outputFile = outputFolder.resolve("output_" + standardConfigurationFilePath.getFileName());
        manager.save(outputFile);

        String actualInputFileContent = Files.readString(standardConfigurationFilePath);
        String actualOutputFileContent = Files.readString(outputFile);

        Assertions.assertEquals(
                stripAllBlankCharacters(actualInputFileContent),
                stripAllBlankCharacters(actualOutputFileContent),
                "The saved json output is different than expected input file."
        );
    }

    private String stripAllBlankCharacters(String input) {
        return input.replaceAll("\\s+", "");
    }

    @Test
    public void saveStandardWithSimpleChangeTest() {

    }

}
