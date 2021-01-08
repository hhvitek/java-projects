package model.modifications.operations.directory.moddownloader;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import utilities.WorkingDirectoryManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractDownloaderTest {

    protected static Path DOWNLOAD_FOLDER =
            Path.of("src/test/resources/model/modifications/operations/directory/moddownloader/downloadFolder");
    protected SteamWorkshopDownloaderIO downloader;

    protected AbstractDownloaderTest() {
        // must set attributes in a child classes...
    }

    @Test
    public void simpleDownloadTest() throws IOException, ModDownloadException, InterruptedException {
        downloader.downloadModById(1399844243);

        Thread.sleep(1000); // wait for download to finish

        int downloadFolderContainsXFiles = WorkingDirectoryManager.countFilesInFolder(DOWNLOAD_FOLDER);

        Assertions.assertEquals(1, downloadFolderContainsXFiles);
    }

    @BeforeEach
    public void recreateDownloadFolderForThisOneTest() {
        try {
            WorkingDirectoryManager.deleteDirectoryIfExists(DOWNLOAD_FOLDER);
            Files.createDirectories(DOWNLOAD_FOLDER);
        } catch (IOException e) {
            fail("Cannot recreate test directory. " +
                            "Please check if the following folder exists and is not empty: " + DOWNLOAD_FOLDER.toAbsolutePath(),
                    e);
        }
    }

    @AfterEach
    public void quitDownloaderAfterEachTest() {
        downloader.quit();
    }
}
