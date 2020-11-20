package model.modifications.operations.directory.moddownloader.drivers;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Path;

public class FirefoxWebDriverCreator implements WebDriverCreator {

    @Override
    public WebDriver getWebDriver(@NotNull Path downloadFolder) {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/webdrivers/geckodriver-v0.28.0-win64/geckodriver.exe");

        System.setProperty(org.openqa.selenium.firefox.FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
        System.setProperty(org.openqa.selenium.firefox.FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");

        FirefoxOptions options = new FirefoxOptions()
                .setLogLevel(FirefoxDriverLogLevel.WARN)
                .setHeadless(false)
                .addPreference("browser.download.dir", downloadFolder.toAbsolutePath().toString())
                .addPreference("browser.download.folderList", 2)
                .addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");

        return new org.openqa.selenium.firefox.FirefoxDriver(options);
    }
}
