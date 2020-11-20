package model.modifications.operations.directory.moddownloader;

import model.exceptions.ModificationException;
import model.modifications.operations.Operation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModDownloaderOperationTest {

    private Path downloadFolder = Path.of("src/test/resources/model/modifications/operations/directory/moddownloader/downloadFolder");


    @Test
    public void oneModDownloadTest() throws IOException, ModificationException {
        List<Integer> mods = List.of(1399844243);
        Path tempDownloadFolder = Files.createTempDirectory(downloadFolder, "one_");

        Operation downloadOperation = new ModDownloaderOperation(mods);
        downloadOperation.execute(tempDownloadFolder);

    }

    @Test void twoModsDownloadTest() throws IOException, ModificationException {
        List<Integer> mods = List.of(1610578060, 1712760331);

        Path tempDownloadFolder = Files.createTempDirectory(downloadFolder, "two_");
        Operation downloadOperation = new ModDownloaderOperation(mods);
        downloadOperation.execute(tempDownloadFolder);
    }

}
