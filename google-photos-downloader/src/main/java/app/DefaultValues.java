package app;

import app.downloader.FactoryDownloader.FactoryDownloaderType;

import java.io.File;

/**
 * For simplicity default global values.
 */
public final class DefaultValues {

    private DefaultValues() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String                SETTINGS_FILEPATH           = System.getProperty("user.dir") +
                                                                            File.separator +
                                                                            "settings.ini";
    public static final String                SETTINGS_LOCAL_PHOTO_FOLDER = "./FOTKY";
    public static final FactoryDownloaderType DOWNLOADER_TYPE             =
            FactoryDownloaderType.NIO;

}
