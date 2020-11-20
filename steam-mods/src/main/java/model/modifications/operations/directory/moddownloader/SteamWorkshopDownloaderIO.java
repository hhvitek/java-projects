package model.modifications.operations.directory.moddownloader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Expected to be used to download multiple files one-by-one.
 * Should be manually terminated using quit() method after the usage.
 */
public interface SteamWorkshopDownloaderIO {
    void downloadModById(@NotNull Integer id) throws IOException, ModDownloadException;
    void quit();
}
