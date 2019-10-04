package app.photos;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.SearchMediaItemsPagedResponse;
import com.google.photos.library.v1.proto.DateFilter;
import com.google.photos.library.v1.proto.Filters;
import com.google.photos.types.proto.DateRange;
import com.google.photos.types.proto.MediaItem;
import com.google.type.Date;

public class GooglePhotos {

    private final Logger logger = LoggerFactory.getLogger(GooglePhotos.class);

    private String errorText;
    private String lastDate;
    private String accessTokenString;
    private String localPhotoFolder;

    public GooglePhotos() {

    }

    public void downloadPhotos(String accessTokenString, String lastDate, String localPhotoFolder) {
        // Set up the Photos Library Client that interacts with the API
        this.accessTokenString = accessTokenString;
        this.lastDate = lastDate;
        this.localPhotoFolder = localPhotoFolder;

        if (!checkParametersFormat()) {
            logger.debug(errorText);
            return;
        }

        GoogleCredentials credentials;
        try {
            AccessToken accessToken = new AccessToken(accessTokenString, null);
            credentials = GoogleCredentials.create(accessToken);
            credentials.refreshIfExpired();

            this.accessTokenString = credentials.getAccessToken().getTokenValue();

            PhotosLibrarySettings settings = PhotosLibrarySettings.newBuilder()
                    .setCredentialsProvider(
                            FixedCredentialsProvider.create(credentials)
                    )
                    .build();

            PhotosLibraryClient photosLibraryClient = PhotosLibraryClient.initialize(settings);

            // Create new com.google.type.Date objects for two dates
            Date startDate = getDateFromString(lastDate);
            Date endDate = getDateNow();
            // Create a DateRange from these two dates
            DateRange dateRange = DateRange.newBuilder()
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .build();
            // Create a new dateFilter with the date range. You can also set multiple date
            // ranges here
            DateFilter dateFilter = DateFilter.newBuilder()
                    .addRanges(dateRange)
                    .build();
            // Create a new Filters object
            Filters filters = Filters.newBuilder()
                    .setDateFilter(dateFilter)
                    .build();
            // Specify the Filters object in the searchMediaItems call
            SearchMediaItemsPagedResponse response = photosLibraryClient.searchMediaItems(filters);

            for (MediaItem mediaItem : response.iterateAll()) {
                String name = mediaItem.getFilename();
                System.out.println(name);
            }

        } catch (ApiException | IOException e) {
            logger.error("GooglePhotos error", e);
            e.printStackTrace();
        }
    }

    private boolean checkParametersFormat() {

        if (accessTokenString != null && lastDate != null && localPhotoFolder != null
                && !accessTokenString.isEmpty()) {

            if (!Files.isDirectory(Path.of(localPhotoFolder))) {
                errorText = String.format("The folder <%s> is not a directory!", localPhotoFolder);
            } else if (!checkDateFormat(lastDate)) {
                errorText = String.format("The argument lastDate <%s> is in incorect format."
                        + " Expected \"yyyy-MM-DD\"", lastDate);
            } else {
                return true;
            }
        }

        errorText = String.format("Invalid parameters accessTokenString: <%s>, lastDate: <%s>, "
                + "localPhotoFolder: <%s>.", accessTokenString, lastDate, localPhotoFolder);
        return false;
    }

    private Date getDateFromString(String date) throws DateTimeParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDate = LocalDateTime.parse(date, dtf);
        return Date.newBuilder()
                .setDay(localDate.getDayOfMonth())
                .setMonth(localDate.getMonthValue())
                .setYear(localDate.getYear())
                .build();
    }

    private Date getDateNow() {
        LocalDateTime localDate = LocalDateTime.now();
        return Date.newBuilder()
                .setDay(localDate.getDayOfMonth())
                .setMonth(localDate.getMonthValue())
                .setYear(localDate.getYear())
                .build();
    }



    /**
     * Expects date-string in the format YYYY-MM-DD
     * @param date
     * @return
     */
    private boolean checkDateFormat(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDateTime.parse(date, dtf);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }



}
