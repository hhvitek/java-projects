package app.photos;

import java.nio.file.Path;
import java.time.format.DateTimeParseException;

import app.downloader.FactoryDownloader.FactoryDownloaderType;

/**
 * Just to try-out builder pattern. IGooglePhotosDownloader library must be
 * created with exact specified parameters, to change parameters, create a
 * different object IGooglePhotosBuilder
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
    public IGooglePhotosBuilder setStartDate(String startDate)
            throws IllegalArgumentException, DateTimeParseException;

    public IGooglePhotosBuilder setRefreshToken(String refreshToken);

    /**
     * The local folder to download photos to.
     *
     * @param localPhotosFolder
     * @return
     */
    public IGooglePhotosBuilder setLocalPhotoFolder(Path localPhotoFolder);

    public IGooglePhotosBuilder setClientId(String clientId);

    public IGooglePhotosBuilder setClientSecret(String clientSecret);

    public IGooglePhotosBuilder setDownloaderType(FactoryDownloaderType type);

    /**
     * Created {@link IGooglePhotosDownloader} object
     *
     * @return
     */
    public GooglePhotos build() throws IllegalStateException, NullPointerException;

}
