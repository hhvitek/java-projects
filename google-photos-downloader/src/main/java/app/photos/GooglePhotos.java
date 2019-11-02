package app.photos;

import app.DefaultValues;
import app.downloader.FactoryDownloader;
import app.downloader.FactoryDownloader.FactoryDownloaderType;
import app.downloader.IDownloader;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.UserCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.SearchMediaItemsPagedResponse;
import com.google.photos.library.v1.proto.DateFilter;
import com.google.photos.library.v1.proto.Filters;
import com.google.photos.types.proto.DateRange;
import com.google.photos.types.proto.MediaItem;
import com.google.photos.types.proto.MediaMetadata;
import com.google.protobuf.Timestamp;
import com.google.type.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
 *
 * @author vitek
 *
 */

public class GooglePhotos {

    private static final Logger logger = LoggerFactory.getLogger(GooglePhotos.class);

    private String lastErrorText = "";
    private Date startDate;
    private Date endDate;
    private Path localPhotoFolder;
    private UserCredentials credentials;

    private IDownloader downloader = FactoryDownloader.getDownloader(DefaultValues.DOWNLOADER_TYPE);

    /**
     * Use at your own risk! Preferred method, recommended method is using Builder
     * See {@link GooglePhotos}
     */
    public GooglePhotos() {
    }

    /**
     * Use at your own risk! Preferred method, recommended method is using Builder
     * See {@link GooglePhotos}
     */
    private GooglePhotos(UserCredentials credentials, String startDate, Path localPhotoFolder,
            FactoryDownloaderType type) throws IllegalArgumentException, DateTimeParseException {

        this.startDate = getDateFromString(startDate);
        this.localPhotoFolder = localPhotoFolder;
        this.credentials = credentials;

        if (!checkParametersFormat(credentials, this.startDate, localPhotoFolder)) {
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

    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public void setDownloader(FactoryDownloaderType type) {
        this.downloader = FactoryDownloader.getDownloader(type);
    }

    public String getLastError() {
        return this.lastErrorText;
    }
    // ####STATIC METHODS

    public static IGooglePhotosBuilder newBuilder() {
        return new GooglePhotosBuilder();
    }

    public static Date getDateFromString(String date)
            throws IllegalArgumentException, DateTimeParseException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, dtf);
        return Date.newBuilder().setDay(localDate.getDayOfMonth())
                .setMonth(localDate.getMonthValue()).setYear(localDate.getYear()).build();
    }

    public static Date getDateNow() {
        LocalDate localDate = LocalDate.now();
        return Date.newBuilder().setDay(localDate.getDayOfMonth())
                .setMonth(localDate.getMonthValue()).setYear(localDate.getYear()).build();
    }

    // ####IGooglePhotosDownload interface implementation######################

    public boolean downloadPhotos() {

        if (!checkParametersFormat(credentials, startDate, localPhotoFolder)) {
            logger.error(lastErrorText);
            return false;
        }

        try {
            credentials.refresh();

            PhotosLibrarySettings settings = PhotosLibrarySettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();

            PhotosLibraryClient photosLibraryClient = PhotosLibraryClient.initialize(settings);

            // Create new com.google.type.Date objects for two dates
            setEndDate(getDateNow());
            // Create a DateRange from these two dates
            DateRange dateRange = DateRange.newBuilder().setStartDate(startDate).setEndDate(endDate)
                    .build();
            // Create a new dateFilter with the date range. You can also set multiple date
            // ranges here
            DateFilter dateFilter = DateFilter.newBuilder().addRanges(dateRange).build();
            // Create a new Filters object
            Filters filters = Filters.newBuilder().setDateFilter(dateFilter).build();
            // Specify the Filters object in the searchMediaItems call
            SearchMediaItemsPagedResponse response = photosLibraryClient.searchMediaItems(filters);

            String localPhotoFolderAsString = this.localPhotoFolder.toString();
            for (MediaItem mediaItem : response.iterateAll()) {
                String name = mediaItem.getFilename();
                String baseUrl = mediaItem.getBaseUrl();
                if (mediaItem.hasMediaMetadata()) {
                    MediaMetadata mediaMetadata = mediaItem.getMediaMetadata();
                    if (mediaMetadata.hasPhoto()) {
                        logger.info("Its a photo! {}", name);
                        long height = mediaMetadata.getHeight();
                        long width = mediaMetadata.getWidth();
                        Timestamp creationTime = mediaMetadata.getCreationTime();
                        try {
                            downloader.downloadItem(baseUrl,
                                    Path.of(localPhotoFolderAsString, name));
                            logger.info("The photo {} has been downloaded successfully.", name);
                        } catch (FileAlreadyExistsException e) {
                            logger.warn("FileAlreadyExistsException during the file copying \"{}\"",
                                    name, e);
                        } catch (IOException e) {
                            logger.warn("IOException during the file copying \"{}\"", name, e);
                        }
                    } else if (mediaMetadata.hasVideo()) {
                        logger.info("Its a video! {}", name);
                    } else {
                        logger.warn("Unknown format. Ignoring. File: <{}>", name);
                    }
                } else {
                    logger.warn(
                            "Unknown format. The file doesnt have any metadata. Ignoring. File: {}",
                            name);
                }
            }
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

        try {
            sw.close();
            pw.close();
        } catch (IOException e1) {
            logger.error("Failed to close Writers.", e1);
        }

    }

    private void setEndDate(Date date) {
        endDate = date;
    }

    private static void storeCredentials(UserCredentials credentials, Path path)
            throws IOException {
        String accessToken = credentials.getAccessToken().getTokenValue();
        String refreshToken = credentials.getRefreshToken();
        String clientId = credentials.getClientId();
        String clientSecret = credentials.getClientSecret();

        Files.writeString(path,
                String.format(
                        "accessToken = %s%nrefreshToken = %s%nclientId = %s%nclientSecret = %s%n",
                        accessToken, refreshToken, clientId, clientSecret));
    }

    private boolean checkParametersFormat(UserCredentials credentials, Date startDate,
            Path localPhotoFolder) {
        if (credentials != null && startDate != null && localPhotoFolder != null) {
            if (!(credentials.getRefreshToken().isEmpty() || credentials.getClientId().isEmpty()
                    || credentials.getClientSecret().isEmpty())) {

                if (!Files.isDirectory(localPhotoFolder)) {
                    lastErrorText = String.format("The folder <%s> is not a directory!",
                            localPhotoFolder);
                    return false;
                } else if (!startDate.isInitialized()) {
                    lastErrorText = String
                            .format("The argument lastDate <%s> is in incorect format."
                                    + " Expected \"yyyy-MM-DD\"", startDate);
                    return false;
                } else {
                    return true;
                }
            }
        }
        lastErrorText = String.format(
                "Invalid parameters credentials: <%s>, lastDate: <%s>, "
                        + "localPhotoFolder: <%s>.",
                credentialsToString(credentials), startDate, localPhotoFolder);
        return false;
    }

    private static String credentialsToString(UserCredentials credentials) {
        if (credentials != null) {
            return String.format("{credentials: [clientId=%s, clientSecret=%s, refreshToken=%s]}",
                    credentials.getClientId(), credentials.getClientSecret(),
                    credentials.getRefreshToken());
        }
        return "{credentials: null}";
    }

    /**
     * Expects date-string in the format YYYY-MM-DD
     *
     * @param date
     * @return
     */
    private boolean checkDateFormat(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDateTime.parse(date, dtf);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return false;
        }
        return true;
    }

}
