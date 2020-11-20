package model.modifications.operations.directory.moddownloader.drivers;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

/**
 * Should create, configure and initialize a WebDriver for Selenium-based usage...
 * This WebDriver should be:
 *   1] headless
 *   2] silent - as logless as possible
 *   3] support userless automatic file-downloads to specific configurable downloadFolder
 */
interface WebDriverCreator {
    WebDriver getWebDriver(@NotNull Path downloadFolder);
}
