package model.modifications.operations.directory.moddownloader;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Beware to call quit() method before any files are successfully downloaded.
 * If a file is being downloaded and the quit() method is called, the file may not be downloaded successfully.
 */
public class SteamWorkshopDownloaderIOImpl implements SteamWorkshopDownloaderIO {

    private static final Logger logger = LoggerFactory.getLogger(SteamWorkshopDownloaderIOImpl.class);

    private WebDriver webDriver;

    public SteamWorkshopDownloaderIOImpl(@NotNull WebDriver webDriver) {
        this.webDriver = webDriver;

        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Override
    public void downloadModById(@NotNull Integer id) throws IOException, ModDownloadException {
        try {
            traverseToModDownloadWebPage();
            enterModIdIntoSearchInputBox(id);
            clickOnDownloadButton();
        } catch (TimeoutException | NoSuchElementException e) {
            String errorMessage = "Encountered error. Trying to download a mod with the following id: <" + id + ">.";
            logger.error(errorMessage, e);
            throw new ModDownloadException(errorMessage, e);
        }
    }

    private void traverseToModDownloadWebPage() throws TimeoutException {
        webDriver.get("https://steamworkshopdownloader.io/");
    }

    /**
     * Simple sendKeys with mod's id string does not work correctly
     * Sometimes (1/4 times) a different mod is downloaded
     * "1234" is MOD-A; "12345678" is MOD-B
     * We want to download MOD-B but MOD-A is downloaded....
     * Therefore a workaround using system's clipboard was implemented...
     */
    private void enterModIdIntoSearchInputBox(@NotNull Integer modId) throws TimeoutException, NoSuchElementException {
        String modIdAsString = modId.toString();

        WebElement inputSearchBox = findInputSearchBox();

        setSystemClipboardContents(modIdAsString);

        inputSearchBox.click(); // simulate human behaviour

        inputSearchBox.sendKeys(Keys.LEFT_CONTROL + "v");
    }

    private void setSystemClipboardContents(String value) {
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
    }

    private WebElement findInputSearchBox() throws TimeoutException, NoSuchElementException {
        return webDriver.findElement(By.id("downloadUrlLabel"));
    }

    private void clickOnDownloadButton() throws TimeoutException, NoSuchElementException {
        WebElement downloadButton = findDownloadButton();

        downloadButton.click();
    }

    private WebElement findDownloadButton() throws TimeoutException, NoSuchElementException {
        return webDriver.findElement(By.cssSelector("button.text-nowrap.btn.btn-sm.btn-primary"));
    }

    @Override
    public void quit() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
