package model.modifications.operations.directory.moddownloader;

import model.exceptions.ModificationException;
import model.modifications.operations.Operation;
import model.modifications.operations.directory.moddownloader.drivers.WebDriverFactory;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import utilities.WorkingDirectoryManager;
import utilities.file_locators.FileLocatorImpl;
import utilities.file_locators.IFileLocator;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Driver;
import java.util.List;

public class ModDownloaderOperation implements Operation {

    private static int MAX_WAIT_TIME_IN_SEC = 30;

    private List<Integer> modIds;
    private SteamWorkshopDownloaderIO downloader;

    private static WebDriverFactory.SupportedWebDrivers DEFAULT_DRIVER = WebDriverFactory.SupportedWebDrivers.FIREFOX;

    public ModDownloaderOperation(@NotNull List<Integer> modIds) {
        this.modIds = modIds;
    }

    public void setModIds(@NotNull List<Integer> modIds) {
        this.modIds = modIds;
    }

    @Override
    public void execute(@NotNull Path destinationFolder) throws IOException, ModificationException {
        downloader = createDownloader(destinationFolder);

        int currentNumberOfZipFilesInDownloadFolder = countZipFilesInFolder(destinationFolder);

        for(Integer modId: modIds) {
            downloadMod(modId);
        }

        int expectedNumberOfZipFilesAfterDownload = currentNumberOfZipFilesInDownloadFolder + modIds.size();

        try {
            waitUntilAllModsAreDownloadedOrThrowTimeoutException(expectedNumberOfZipFilesAfterDownload, destinationFolder, MAX_WAIT_TIME_IN_SEC);
        } catch (ModDownloaderTimeoutException ex) {
            throw new ModificationException("Failed to download all mod zip files. Timeout error." + ex.getMessage());
        }
    }

    private SteamWorkshopDownloaderIO createDownloader(Path downloadFolder) {
        WebDriver defaultDriver = WebDriverFactory.getDriver(DEFAULT_DRIVER, downloadFolder);

        return new SteamWorkshopDownloaderIOImpl(defaultDriver);
    }

    private int countZipFilesInFolder(Path folder) {
        IFileLocator fileLocator = new FileLocatorImpl();
        return fileLocator.findUsingRegex(folder, "glob:*.zip").size();
    }

    private void downloadMod(Integer modId) throws ModificationException {
        try {
            downloader.downloadModById(modId);
        } catch (ModDownloadException | IOException e) {
            throw new ModificationException(e.toString(), e);
        }
    }

    private void waitUntilAllModsAreDownloadedOrThrowTimeoutException(
            int expectedNumberOfZipFiles,
            Path downloadFolder,
            int timeoutInSec) throws ModDownloaderTimeoutException {

        long startTime = System.currentTimeMillis();

        while (true) {
            int actualNumberOfZipFiles = countZipFilesInFolder(downloadFolder);
            if (actualNumberOfZipFiles >= expectedNumberOfZipFiles) {
                return;
            }

            sleepOneSecondOnErrorThrowTimeoutException();

            if (hasTimeoutExpired(startTime, timeoutInSec)) {
                throw new ModDownloaderTimeoutException();
            }
        }
    }

    private void sleepOneSecondOnErrorThrowTimeoutException() throws ModDownloaderTimeoutException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw  new ModDownloaderTimeoutException("Failed to sleep for a one second.", e);
        }
    }

    private boolean hasTimeoutExpired(long startTime, int timeout) {
        long endTime = System.currentTimeMillis() - startTime;
        if (endTime > timeout * 1000) {
            return true;
        } else {
            return false;
        }
    }


}
