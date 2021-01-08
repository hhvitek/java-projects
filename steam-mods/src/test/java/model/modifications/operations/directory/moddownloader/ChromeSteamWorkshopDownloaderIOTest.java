package model.modifications.operations.directory.moddownloader;

import model.modifications.operations.directory.moddownloader.drivers.ChromeWebDriverCreator;
import model.modifications.operations.directory.moddownloader.drivers.WebDriverFactory;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

class ChromeSteamWorkshopDownloaderIOTest extends AbstractDownloaderTest{

    public ChromeSteamWorkshopDownloaderIOTest() {
        super();
        WebDriver webDriver = WebDriverFactory.getDriver(WebDriverFactory.SupportedWebDrivers.CHROME, DOWNLOAD_FOLDER);
        downloader = new SteamWorkshopDownloaderIOImpl(webDriver);
    }

}
