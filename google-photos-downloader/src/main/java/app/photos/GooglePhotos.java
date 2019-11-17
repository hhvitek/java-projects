package app.photos;

import app.MyCommons;
import app.downloader.FactoryDownloader;
import app.photos.downloaders.*;
import com.google.auth.oauth2.UserCredentials;
import com.google.type.Date;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Use the following code to construct an object of this class.
 * <pre>
 * {@code
 * GooglePhotos gPhotos = GooglePhotos.newBuilder()
 *     .setClientId("clientId...")
 *     .setClientSecret("clientSecret...")
 *     .setRefreshToken("refreshToken...")
 *     .setLocalPhotoFolder(Path.of("./LOCAL_FOLDER"))
 *     .setStartDate("2019-02-21")
 *     .build()
 * }
 * </pre>
 * <p>
 * Servers as a kind of wrapper over actual downloader class IGooglePhotosDownloader.
 * It's observable for outside world - just attached the observer to the actual downloader.
 * It's observer for the downloader - looking for any errors
 * - so GooglePhotos object knows any errors as fast as possible,
 * - however it will also set the final error when the downloader finishes.
 */

public class GooglePhotos implements IObservable, IObserver {

    private static final Logger logger = LoggerFactory.getLogger(GooglePhotos.class);

    private String          lastErrorText = "";
    private Date            startDate;
    private Date            endDate;
    private Path            localPhotoFolder;
    private UserCredentials credentials;

    private IGooglePhotosDownloader gDownloader;

    /**
     * Use Builder
     * See {@link GooglePhotos}
     */
    private GooglePhotos() {
        throw new UnsupportedOperationException("Using this constructor is forbidden.");
    }

    /**
     * Use at your own risk! Use Builder
     * See {@link GooglePhotos}
     *
     * @param credentials      UserCredentials
     * @param startDate        Google's Date this date defines the beginning of photos timestamp
     * @param endDate          Google's Date this date defines the end of photos timestamp. If it's
     *                         {@code null} the today's Date is used
     * @param localPhotoFolder The local file system folder to download photos to
     * @param type             The method of downloading individual photos.
     */
    GooglePhotos(UserCredentials credentials, Date startDate, Date endDate, Path localPhotoFolder,
                 FactoryDownloader.FactoryDownloaderType type)
            throws IllegalArgumentException {

        this.startDate = startDate;
        if (endDate == null) {
            this.endDate = GooglePhotosCommons.getDateNow();
        } else {
            this.endDate = endDate;
        }

        this.localPhotoFolder = localPhotoFolder;
        this.credentials = credentials;

        if (!checkParametersFormat(credentials, startDate, this.endDate, localPhotoFolder)) {
            logger.error(lastErrorText);
            throw new IllegalArgumentException(lastErrorText);
        }

        gDownloader = new GooglePhotosDownloader();
        if (type != null) {
            gDownloader.setItemDownloader(type);
        }
    }

    // ##PUBLIC################################################################

    @Override
    public void attachObserver(@NotNull IObserver observer) {
        gDownloader.attachObserver(observer);
    }

    @Override
    public void detachObserver(@NotNull IObserver observer) {
        gDownloader.detachObserver(observer);
    }

    public void stop() {
        gDownloader.stop();
    }

    @Override
    public void updateObserver(int count, int total, String fileName, String message,
                               Status status) {
        if (status == Status.Error) {
            setLastErrorText(message);
        }
    }

    public boolean hasError() {
        return lastErrorText != null && !lastErrorText.isBlank();
    }

    public String getLastError() {
        return lastErrorText;
    }

    public static IGooglePhotosBuilder newBuilder() {
        return new GooglePhotosBuilder();
    }

    public void downloadPhotos() {

        if (!checkParametersFormat(credentials, startDate, endDate, localPhotoFolder)) {
            logger.error(lastErrorText);
        }

        gDownloader.downloadPhotos(startDate, endDate, localPhotoFolder, credentials);
        if (gDownloader.hasError()) {
            setLastErrorText(gDownloader.getErrorMessage());
        }
    }

    private void setLastErrorText(String text) {
        lastErrorText = text;
    }

    private boolean checkParametersFormat(UserCredentials credentials, Date startDate, Date endDate,
                                          Path localPhotoFolder) {
        if (MyCommons.areAllArgsNonNull(credentials, startDate, endDate, localPhotoFolder)) {
            if (GooglePhotosCommons.isUserCredentialsNonBlank(credentials)) {
                if (!Files.isDirectory(localPhotoFolder)) {
                    lastErrorText =
                            String.format("The folder <%s> is not a directory!", localPhotoFolder);
                    return false;
                } else if (!startDate.isInitialized() || !endDate.isInitialized()) {
                    lastErrorText = String.format(
                            "The argument startDate <%s> or endDate <%s> is in incorrect format." +
                            " Expected \"yyyy-MM-DD\"", startDate, endDate);
                    return false;
                } else {
                    return true;
                }
            }
        }
        lastErrorText = String.format(
                "Invalid parameters credentials: <%s>, startDate: <%s>, " + "endDate: <%s>, " +
                "localPhotoFolder: <%s>.", GooglePhotosCommons.credentialsToString(credentials),
                startDate, endDate, localPhotoFolder
        );
        return false;
    }

}
