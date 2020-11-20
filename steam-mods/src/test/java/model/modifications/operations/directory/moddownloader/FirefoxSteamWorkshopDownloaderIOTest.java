package model.modifications.operations.directory.moddownloader;

import model.modifications.operations.directory.moddownloader.drivers.FirefoxWebDriverCreator;
import model.modifications.operations.directory.moddownloader.drivers.WebDriverFactory;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

public class FirefoxSteamWorkshopDownloaderIOTest extends AbstractDownloaderTest {

    public FirefoxSteamWorkshopDownloaderIOTest() {
        super();
        WebDriver webDriver = WebDriverFactory.getDriver(WebDriverFactory.SupportedWebDrivers.FIREFOX, downloaderFolder);
        downloader = new SteamWorkshopDownloaderIOImpl(webDriver);
    }
}
