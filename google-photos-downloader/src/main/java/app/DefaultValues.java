package app;

import app.downloader.FactoryDownloader.FactoryDownloaderType;

/**
 * For simplicity default global values.
 */
public class DefaultValues {

    public static final String SETTINGS_FILEPATH = "./settings.ini";
    public static final String SETTINGS_LOCAL_PHOTO_FOLDER = "./FOTKY";
    public static final FactoryDownloaderType DOWNLOADER_TYPE = FactoryDownloaderType.NIO;
}
