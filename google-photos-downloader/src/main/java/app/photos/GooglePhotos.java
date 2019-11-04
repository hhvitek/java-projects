package app.photos;

import app.downloader.FactoryDownloader.FactoryDownloaderType;
import app.photos.downloaders.GooglePhotosCommons;
import app.photos.downloaders.GooglePhotosDownloader;
import app.photos.downloaders.IGooglePhotosDownloader;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.UserCredentials;
import com.google.type.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
 */

public class GooglePhotos {

    private static final Logger logger = LoggerFactory.getLogger(GooglePhotos.class);

    private String lastErrorText = "";
    private Date startDate;
    private Date endDate;
    private Path localPhotoFolder;
    private UserCredentials credentials;

    private IGooglePhotosDownloader gDownloader = new GooglePhotosDownloader();

    /**
     * Use at your own risk! Preferred method, recommended method is using Builder
     * See {@link GooglePhotos}
     */
    private GooglePhotos() {
    }

    /**
     * Use at your own risk! Preferred method, recommended method is using Builder
     * See {@link GooglePhotos}
     */
    public GooglePhotos(UserCredentials credentials, Date startDate, Path localPhotoFolder)
            throws IllegalArgumentException {

        this.startDate = startDate;
        this.endDate = GooglePhotosCommons.getDateNow();
        this.localPhotoFolder = localPhotoFolder;
        this.credentials = credentials;

        if (!checkParametersFormat(credentials, this.startDate, this.endDate, localPhotoFolder)) {
            logger.error(lastErrorText);
            throw new IllegalArgumentException(lastErrorText);
        }
    }

    // ##PUBLIC################################################################

    // ####SETTERS#############################################################
    public void setLocalPhotoFolder(Path path) {
        this.localPhotoFolder = path;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public void setDownloader(FactoryDownloaderType type) {
        this.gDownloader.setItemDownloader(type);
    }

    public boolean hasError() {
        return this.lastErrorText != null && !this.lastErrorText.isBlank();
    }

    public String getLastError() {
        return this.lastErrorText;
    }
    // ####STATIC METHODS

    public static IGooglePhotosBuilder newBuilder() {
        return new GooglePhotosBuilder();
    }


    public boolean downloadPhotos() {

        if (!checkParametersFormat(credentials, startDate, endDate, localPhotoFolder)) {
            logger.error(lastErrorText);
            return false;
        }

        try {
            gDownloader.downloadPhotos(startDate, endDate, localPhotoFolder, credentials);
        } catch (ApiException | IOException e) {
            setLastErrorText("GooglePhoto error", e);
            logger.error("GooglePhotos error", e);
            return false;
        }
        return true;
    }

    // ##PRIVATE###############################################################
    // ####SETTERS#############################################################

    private void setLastErrorText(String text) {
        this.lastErrorText = text;
    }

    private void setLastErrorText(String message, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        if (message == null) {
            message = "";
        }

        setLastErrorText(String.format(
                "%s:%n%s", message, sw.toString()));

        pw.close();
    }

    private boolean checkParametersFormat(UserCredentials credentials,
                                          Date startDate,
                                          Date endDate,
                                          Path localPhotoFolder) {
        if (GooglePhotosCommons.areAllArgsNonNull(credentials, startDate, endDate, localPhotoFolder)) {
            if (GooglePhotosCommons.isUserCredentialsNonBlank(credentials)) {
                if (!Files.isDirectory(localPhotoFolder)) {
                    lastErrorText = String.format("The folder <%s> is not a directory!",
                            localPhotoFolder);
                    return false;
                } else if (!startDate.isInitialized() || !endDate.isInitialized()) {
                    lastErrorText = String
                            .format("The argument startDate <%s> or endDate <%s> is in incorrect format."
                                    + " Expected \"yyyy-MM-DD\"", startDate, endDate);
                    return false;
                } else {
                    return true;
                }
            }
        }
        lastErrorText = String.format(
                "Invalid parameters credentials: <%s>, startDate: <%s>, "
                        + "endDate: <%s>, "
                        + "localPhotoFolder: <%s>.",
                GooglePhotosCommons.credentialsToString(credentials),
                startDate,
                endDate,
                localPhotoFolder
        );
        return false;
    }

}
