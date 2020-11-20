package model.modificationschains;

import model.exceptions.ModificationException;
import model.modifications.operations.directory.AbstractOperationTest;
import model.persistent.PersistentStorageManager;
import model.persistent.exceptions.ConfigurationException;
import model.persistent.json.JsonConfigurationManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.ResourceManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class BasicChainTest extends AbstractOperationTest {

    private static List<ModificationsChain> chains;

    public BasicChainTest() {
        super("src/test/resources/model/modificationschains");
    }

    @Override
    protected String getOperationDirectoryName() {
        return "steam_workshop_downloader";
    }

    @Override
    public void init() {

    }

    @BeforeAll
    private static void initStatic() throws IOException, ConfigurationException {
        Path configurationFile = ResourceManager.getFilePathFromResourceName("model/modificationschains/configuration.json");

        Assertions.assertNotNull(configurationFile);

        PersistentStorageManager storageManager = new JsonConfigurationManager(configurationFile);
        storageManager.load();

        chains = storageManager.getModificationsChains();
    }

    @Test
    public void steamWorkshopDownloaderTest() throws IOException, ConfigurationException, ModificationException {
        ModificationsChain chain = chains.get(1);
        Assertions.assertEquals("steam_workshop_downloader", chain.getId());

        setTemplateAndWorkingDirectoriesBasedOnDirectoryName("steam_workshop_downloader");
        super.init();

        chain.execute(workingDirectory);
    }

    @Test
    public void stellarisSmodsRuTest() throws IOException, ConfigurationException, ModificationException {
        ModificationsChain chain = chains.get(2);
        Assertions.assertEquals("smods_ru", chain.getId());

        setTemplateAndWorkingDirectoriesBasedOnDirectoryName("smods_ru");
        super.init();

        chain.execute(workingDirectory);
    }

}
