package app.photos;

import app.downloader.FactoryDownloader.FactoryDownloaderType;

import java.nio.file.Path;

/**
 * Just to try-out builder pattern.
 */
public interface IGooglePhotosBuilder {

    /**
     * set Date, specifically com.google.type.Date. Expected String format
     * "yyyy-MM-DD" example: "2019-10-21". Only photos newer than this date will be
     * relevant.
     *
     * @param startDate Only accepts the following format "yyyy-MM-DD".
     *
     * @return
     */
    IGooglePhotosBuilder setStartDate(String startDate);

    IGooglePhotosBuilder setEndDate(String endDate);

    IGooglePhotosBuilder setRefreshToken(String refreshToken);

    /**
     * The local folder to download photos to.
     *
     * @param localPhotoFolder
     *
     * @return
     */
    IGooglePhotosBuilder setLocalPhotoFolder(Path localPhotoFolder);

    IGooglePhotosBuilder setClientId(String clientId);

    IGooglePhotosBuilder setClientSecret(String clientSecret);

    IGooglePhotosBuilder setDownloaderType(FactoryDownloaderType type);

    /**
     * Creates {@link GooglePhotos} object
     *
     * @return
     */
    GooglePhotos build() throws IllegalStateException, NullPointerException;

}
