package model.modifications.operations.directory.moddownloader;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractDownloaderTest {

    protected Path downloaderFolder;
    protected SteamWorkshopDownloaderIO downloader;

    protected AbstractDownloaderTest() {
        // must set attributes in a child classes...

        downloaderFolder = Path.of("src/test/resources/model/modifications/operations/directory/moddownloader/downloadFolder");
    }

    @Test
    public void simpleDownloadTest() throws IOException, ModDownloadException {
        downloader.downloadModById(1399844243);
    }
}
