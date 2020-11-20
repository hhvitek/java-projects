package model.persistent.json;

import model.modifications.Modification;
import model.modificationschains.ModificationsChain;
import model.persistent.*;
import model.persistent.exceptions.ConfigurationException;
import model.persistent.exceptions.NotInitializedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JsonConfigurationManagerTest {

    private static Path standardConfigurationFilePath;
    private static Path invalidConfigurationFilePath;
    private static Path unknownConfigurationFilePath;
    private static Path missingConfigurationFilePath;
    private static Path outputFolder;

    private FileStorageManager manager;

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
        Path jsonResourcesFolder = testResources.resolve("model/persistent/json");
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
        Path actualPath = manager.getDefaultModFolder();
        Path expectedPath = Path.of("%USERPROFILE%", "Documents");
        Assertions.assertEquals(expectedPath, actualPath);
    }

   private void standardFileCheckModifications() {
        List<Modification> modifications = manager.getModifications();
        Assertions.assertEquals(3, modifications.size());
        Modification theFirstModification = modifications.get(0);
        Assertions.assertEquals("EXTRACT_ALL", theFirstModification.getId());
    }

    private void standardFileCheckChains() {
        List<ModificationsChain> modChains = manager.getModificationsChains();
        Assertions.assertEquals(1, modChains.size());
        ModificationsChain theFirstModChain = modChains.get(0);
        Assertions.assertEquals("BASIC", theFirstModChain.getId());

        List<Modification> modifications = theFirstModChain.getModifications();
        List<String> expectedModificationsIds = List.of(
                "EXTRACT_ALL",
                "MODIFY_MOD_DESCRIPTOR_FILES",
                "COPY_MOD_DESCRIPTOR_FILES"
        );

        Assertions.assertEquals(expectedModificationsIds, modificationsToListIds(modifications));
    }

    private List<String> modificationsToListIds(List<Modification> modifications) {
        return modifications.stream().map(Modification::getId).collect(Collectors.toList());
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
