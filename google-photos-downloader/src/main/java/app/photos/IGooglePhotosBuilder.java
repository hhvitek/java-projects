package app.photos;

import java.nio.file.Path;
import java.time.format.DateTimeParseException;

import app.downloader.FactoryDownloader.FactoryDownloaderType;

/**
 * Just to try-out builder pattern.
 *
 * @author vitek
 *
 */
public interface IGooglePhotosBuilder {

    /**
     * set Date, specifically com.google.type.Date. Expected String format
     * "yyyy-MM-DD" example: "2019-10-21". Only photos newer than this date will be
     * relevant.
     *
     * @param startDate. Only accepts the following format "yyyy-MM-DD".
     * @return
     */
    IGooglePhotosBuilder setStartDate(String startDate)
            throws IllegalArgumentException, DateTimeParseException;

    IGooglePhotosBuilder setRefreshToken(String refreshToken);

    /**
     * The local folder to download photos to.
     *
     * @param localPhotosFolder
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
