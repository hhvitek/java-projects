package model.modifications.operations.directory.moddownloader;

import model.exceptions.ModificationException;
import model.modifications.operations.Operation;
import model.modifications.operations.directory.moddownloader.drivers.WebDriverFactory;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Driver;
import java.util.List;

public class ModDownloaderOperation implements Operation {

    private List<Integer> modIds;
    private SteamWorkshopDownloaderIO downloader;

    private static WebDriverFactory.SupportedWebDrivers DEFAULT_DRIVER = WebDriverFactory.SupportedWebDrivers.FIREFOX;

    public ModDownloaderOperation(@NotNull List<Integer> modIds) {
        this.modIds = modIds;
    }

    @Override
    public void execute(Path destinationFolder) throws IOException, ModificationException {
        downloader = createDownloader(destinationFolder);

        for(Integer modId: modIds) {
            downloadMod(modId);
        }
    }

    private SteamWorkshopDownloaderIO createDownloader(Path downloadFolder) {
        WebDriver defaultDriver = WebDriverFactory.getDriver(DEFAULT_DRIVER, downloadFolder);

        return new SteamWorkshopDownloaderIOImpl(defaultDriver);
    }

    private void downloadMod(Integer modId) throws ModificationException {
        try {
            downloader.downloadModById(modId);
        } catch (ModDownloadException | IOException e) {
            throw new ModificationException(e.toString(), e);
        }
    }

    public void setModIds(@NotNull List<Integer> modIds) {
        this.modIds = modIds;
    }
}
