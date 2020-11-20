package model.modifications.operations.directory.moddownloader.drivers;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ChromeWebDriverCreator implements WebDriverCreator {

    @Override
    public WebDriver getWebDriver(@NotNull Path downloadFolder) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/chromedriver_win32/chromedriver.exe");

        ChromeOptions options = new ChromeOptions()
                .setLogLevel(ChromeDriverLogLevel.WARNING)
                .setHeadless(false);

        setDownloadFolder(options, downloadFolder);

        return new org.openqa.selenium.chrome.ChromeDriver(options);
    }

    private void setDownloadFolder(ChromeOptions options, Path downloadFolder) {
        Map<String, Object> prefs = new HashMap<>();
        String downloadFolderInChromeFormat = getAbsolutePathWithForwardSlashesReplaced(downloadFolder);
        prefs.put("download.default_directory", downloadFolderInChromeFormat);
        options.setExperimentalOption("prefs", prefs);
    }

    private String getAbsolutePathWithForwardSlashesReplaced(Path input) {
        String absolutePathAsString = input.toAbsolutePath().toString();
        return absolutePathAsString.replaceAll("/", "\\\\");
    }
}
