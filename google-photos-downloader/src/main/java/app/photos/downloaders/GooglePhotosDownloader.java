package app.photos.downloaders;

import app.downloader.FactoryDownloader;
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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GooglePhotosDownloader implements IGooglePhotosDownloader, IObservable {

    private static final Logger logger = LoggerFactory.getLogger(GooglePhotosDownloader.class);

    private List<IObserver> observers = new ArrayList<>();

    private IDownloader itemDownloader = FactoryDownloader.getDefaultDownloader();

    private SearchMediaItemsPagedResponse response;

    private int itemsProcessed = 0;
    private int itemsCount = 0;
    private String lastProcessedItemDate;


    @Override
    public void downloadPhotos(@NotNull Date startDate,
                               @NotNull Date endDate,
                               @NotNull Path localFolder,
                               @NotNull UserCredentials credentials)
            throws ApiException, IOException {

        if (checkParameters(startDate, endDate, localFolder, credentials)) {
            response = connectToGooglePhotosLibrary(startDate, endDate, credentials);
            itemsProcessed = 0;
            downloadAllItems(response, localFolder);
        }

    }

    @Override
    public void setItemDownloader(FactoryDownloader.FactoryDownloaderType type) {
        itemDownloader = FactoryDownloader.getDownloader(type);
    }

    @Override
    public void setOverrideExistingFiles(boolean override) {
        itemDownloader.setReplaceExisting(override);
    }

    @Override
    public void attachObserver(IObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void detachObserver(IObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(IObserver::updateObserver);
    }

    //##################LOGIC###################

    private boolean checkParameters(Date startDate, Date endDate, Path localFolder, UserCredentials credentials) {
        if (GooglePhotosCommons.areAllArgsNonNull(startDate, endDate, localFolder, credentials)) {
            if (startDate.isInitialized() && endDate.isInitialized()) {
                if (Files.isDirectory(localFolder)) {
                    return GooglePhotosCommons.isUserCredentialsNonBlank(credentials);
                }
            }
        }
        return false;
    }

    private SearchMediaItemsPagedResponse connectToGooglePhotosLibrary(@NotNull Date startDate,
                                                                       @NotNull Date endDate,
                                                                       @NotNull UserCredentials credentials)
            throws ApiException, IOException {
        // refresh/create access token from the configured refresh token.
        credentials.refresh();

        PhotosLibrarySettings settings = PhotosLibrarySettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();

        PhotosLibraryClient photosLibraryClient = PhotosLibraryClient.initialize(settings);

        // Create a DateRange from two dates: startDate->endDate
        DateRange dateRange = DateRange.newBuilder().setStartDate(startDate).setEndDate(endDate)
                .build();
        // Create a new dateFilter with the DataRange. You can also set multiple date
        // ranges here
        DateFilter dateFilter = DateFilter.newBuilder().addRanges(dateRange).build();
        // Create a new Filters object
        Filters filters = Filters.newBuilder().setDateFilter(dateFilter).build();
        // Specify the Filters object in the searchMediaItems call
        SearchMediaItemsPagedResponse response = photosLibraryClient.searchMediaItems(filters);
        return response;
    }

    private void downloadAllItems(@NotNull SearchMediaItemsPagedResponse items,
                                  @NotNull Path localFolder)
            throws ApiException, IOException {


        String localPhotoFolderAsString = localFolder.toString();

        for (MediaItem mediaItem : items.iterateAll()) {
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
                        itemDownloader.downloadItem(
                                baseUrl,
                                Path.of(localPhotoFolderAsString,
                                        name
                                )
                        );
                        logger.info("The photo {} has been downloaded successfully.", name);
                        itemsProcessed++;
                        setLastProcessedItemDate(mediaMetadata);
                        notifyObservers();

                    } catch (FileAlreadyExistsException e) {
                        logger.warn("FileAlreadyExistsException during the file copying \"{}\"",
                                name, e);
                    }
                } else if (mediaMetadata.hasVideo()) {
                    logger.info("Its a video! {}", name);
                } else {
                    logger.warn("Unknown format. Ignoring. File: <{}>", name);
                }
            } else {
                logger.warn(
                        "Unknown format. The file doesn't have any metadata. Ignoring. File: {}",
                        name);
            }
        }
    }

    private void setLastProcessedItemDate(MediaMetadata mediaMetadata) {
        if (mediaMetadata.getCreationTime().isInitialized()) {
            lastProcessedItemDate = GooglePhotosCommons.getStringDateFromGoogleTimestamp(
                    mediaMetadata.getCreationTime()
            );
        }
    }
}
