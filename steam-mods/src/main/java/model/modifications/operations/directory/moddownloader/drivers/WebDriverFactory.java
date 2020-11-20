package model.modifications.operations.directory.moddownloader.drivers;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

public class WebDriverFactory {

    public enum SupportedWebDrivers {
        FIREFOX,
        CHROME
    }

    public static WebDriver getDriver(@NotNull WebDriverFactory.SupportedWebDrivers chosenDriver, @NotNull Path downloadFolder) throws
            RuntimeException
    {
        switch (chosenDriver) {
            case FIREFOX:
                return new FirefoxWebDriverCreator().getWebDriver(downloadFolder);
            case CHROME:
                return new ChromeWebDriverCreator().getWebDriver(downloadFolder);
            default:
                throw new RuntimeException("Unsupported driver: " + chosenDriver);
        }
    }
}
