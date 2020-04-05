package app.photos.downloaders;

import app.MyCommons;
import app.downloader.FactoryDownloader;
import app.downloader.IDownloader;
import app.photos.GooglePhotosCommons;
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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GooglePhotosDownloader implements IGooglePhotosDownloader {

    private static final Logger logger = LoggerFactory.getLogger(GooglePhotosDownloader.class);

    private final List<IObserver> observers = new ArrayList<>();

    private IDownloader itemDownloader = FactoryDownloader.getDefaultDownloader();

    private int    itemsProcessed;
    private int    itemsTotal;
    private String lastProcessedItemName;
    private String lastProcessedItemDate;
    private String message;
    private Status status = Status.Ok;

    private volatile boolean cancelled;

    @Override
    public void downloadPhotos_throws(@NotNull Date startDate, @NotNull Date endDate,
                                      @NotNull Path localFolder,
                                      @NotNull UserCredentials credentials)
            throws ApiException, IOException {

        init();

        if (checkParameters(startDate, endDate, localFolder, credentials)) {

            SearchMediaItemsPagedResponse response =
                    connectToGooglePhotosLibrary(startDate, endDate, credentials);

            itemsTotal = countFetchedMediaItems(response);
            downloadAllItems(response, localFolder);
        } else {
            status = Status.Error;
            message = "Invalid arguments.";

        }
        notifyObservers();
    }

    @Override
    public void downloadPhotos(@NotNull Date startDate, @NotNull Date endDate,
                               @NotNull Path localFolder, @NotNull UserCredentials credentials) {

        init();

        if (checkParameters(startDate, endDate, localFolder, credentials)) {
            try {
                SearchMediaItemsPagedResponse response =
                        connectToGooglePhotosLibrary(startDate, endDate, credentials);

                itemsTotal = countFetchedMediaItems(response);
                downloadAllItems(response, localFolder);

            } catch (ApiException | IOException e) {
                status = Status.Error;
                message = "Error working with GooglePhotos library: " + e.getLocalizedMessage();
                notifyObservers();
            }

        } else {
            status = Status.Error;
            message = "Invalid arguments.";

        }
        notifyObservers();
    }

    @Override
    public void setItemDownloader(@NotNull FactoryDownloader.FactoryDownloaderType type) {
        itemDownloader = FactoryDownloader.getDownloader(type);
    }

    @Override
    public boolean hasError() {
        return status == Status.Error;
    }

    @Override
    public String getErrorMessage() {
        if (hasError()) {
            return message;
        }
        return "";
    }

    @Override
    public void attachObserver(@NotNull IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(@NotNull IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void stop() {
        cancelled = true;
    }

    private void notifyObservers() {
        observers.forEach(
                x -> x.updateObserver(itemsProcessed, itemsTotal, lastProcessedItemName, message,
                                      status
                ));
    }

    //##################LOGIC###################

    /**
     * Perform basic checks to ensure all parameters are valid as expected. Returns true if they
     * are.
     */
    private boolean checkParameters(Date startDate, Date endDate, Path localFolder,
                                    UserCredentials credentials) {
        if (MyCommons.areAllArgsNonNull(startDate, endDate, localFolder, credentials)) {
            if (startDate.isInitialized() && endDate.isInitialized()) {
                if (Files.isDirectory(localFolder)) {
                    return GooglePhotosCommons.isUserCredentialsNonBlank(credentials);
                }
            }
        }
        return false;
    }

    /**
     * Tries to connect to GooglePhotos API using
     * specified credentials and range filter startDate to endDate
     * Returns API "{@code SearchMediaItemsPagedResponse response}" object that can be iterated
     * upon:
     * <pre>
     * {@code
     * for (MediaItem mediaItem : response.iterateAll()) {
     *      //perform actions
     * }
     * }
     * </pre>
     */
    private SearchMediaItemsPagedResponse connectToGooglePhotosLibrary(@NotNull Date startDate,
                                                                       @NotNull Date endDate,
                                                                       @NotNull UserCredentials credentials)
            throws ApiException, IOException {
        // refresh/create access token from the configured refresh token.
        credentials.refresh();

        PhotosLibrarySettings settings = PhotosLibrarySettings.newBuilder()
                                                              .setCredentialsProvider(
                                                                      FixedCredentialsProvider.create(
                                                                              credentials))
                                                              .build();

        SearchMediaItemsPagedResponse response;
        try (PhotosLibraryClient photosLibraryClient = PhotosLibraryClient.initialize(settings)) {

            // Create a DateRange from two dates: startDate->endDate
            DateRange dateRange = DateRange.newBuilder()
                                           .setStartDate(startDate)
                                           .setEndDate(endDate)
                                           .build();
            // Create a new dateFilter with the DataRange. You can also set multiple date
            // ranges here
            DateFilter dateFilter = DateFilter.newBuilder()
                                              .addRanges(dateRange)
                                              .build();
            // Create a new Filters object
            Filters filters = Filters.newBuilder()
                                     .setDateFilter(dateFilter)
                                     .build();
            // Specify the Filters object in the searchMediaItems call
            response = photosLibraryClient.searchMediaItems(filters);
        }
        return response;
    }

    /**
     * Downloads all items previously meta-fetched/filtered using GooglePhotos API. Those items are
     * meta-cached in the API object {@code SearchMediaItemsPagedResponse response}. Items are
     * stored into the {@code localFolder} folder.
     */
    private void downloadAllItems(@NotNull SearchMediaItemsPagedResponse response,
                                  @NotNull Path localFolder)
            throws ApiException, IOException {

        String localPhotoFolderAsString = localFolder.toString();

        for (MediaItem mediaItem : response.iterateAll()) {
            if (isCancelled()) {
                status = Status.Cancelled;
                message = "The downloading has been cancelled by the caller.";
                return;
            }

            String name = mediaItem.getFilename();
            String baseUrl = mediaItem.getBaseUrl();

            itemsProcessed++;
            setLastProcessedItemName(name);
            notifyObservers(); // ###OBSERVERS state changed

            if (mediaItem.hasMediaMetadata()) {
                MediaMetadata mediaMetadata = mediaItem.getMediaMetadata();
                setLastProcessedItemDate(mediaMetadata);

                if (mediaMetadata.hasPhoto()) {
                    processPhoto(mediaItem, localPhotoFolderAsString);
                } else if (mediaMetadata.hasVideo()) {
                    logger.info("Its a video! {}", name);
                } else {
                    message = name + " is an unknown format. Ignoring.";
                    logger.warn(message);
                }
            } else {
                message = name + " file doesnt have any metadata. Ignoring.";
                logger.warn(message);
            }
            notifyObservers();
        }
    }

    private int countFetchedMediaItems(@NotNull SearchMediaItemsPagedResponse response) {

        Stream<MediaItem> stream = StreamSupport.stream(response.iterateAll()
                                                                .spliterator(), false);
        return (int) stream.count();
    }

    private void setLastProcessedItemDate(@NotNull MediaMetadata mediaMetadata) {
        if (mediaMetadata.getCreationTime()
                         .isInitialized()) {
            lastProcessedItemDate = GooglePhotosCommons.getStringDateFromGoogleTimestamp(
                    mediaMetadata.getCreationTime());
        }
    }

    private void setLastProcessedItemName(@NotNull String name) {
        lastProcessedItemName = name;
    }

    private boolean isCancelled() {
        return cancelled;
    }

    private void processPhoto(MediaItem mediaItem, String targetFolder)
            throws IOException {

        logger.info("Its a photo! {}", mediaItem.getFilename());

        long height = mediaItem.getMediaMetadata()
                               .getHeight();
        long width = mediaItem.getMediaMetadata()
                              .getWidth();
        Timestamp creationTime = mediaItem.getMediaMetadata()
                                          .getCreationTime();
        try {
            itemDownloader.downloadItem(mediaItem.getBaseUrl(),
                                        Path.of(targetFolder, mediaItem.getFilename())
            );
            message = mediaItem.getFilename() + " downloaded successfully";
            logger.info(message);
        } catch (FileAlreadyExistsException e) {
            message = mediaItem.getFilename() + " file already exists. Ignoring.";
            logger.warn(message);
        }
    }

    private void init() {
        itemsProcessed = 0;
        itemsTotal = 0;
        message = "";
        lastProcessedItemName = "";
        status = Status.Ok;
        cancelled = false;
        lastProcessedItemDate = "";
    }

}
