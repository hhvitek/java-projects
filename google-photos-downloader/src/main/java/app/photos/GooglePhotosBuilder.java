package app.photos;

import app.downloader.FactoryDownloader.FactoryDownloaderType;
import app.photos.downloaders.GooglePhotosCommons;
import com.google.auth.oauth2.UserCredentials;
import com.google.type.Date;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;

public final class GooglePhotosBuilder implements IGooglePhotosBuilder {

    private String startDate;
    private String endDate;
    private Path localPhotoFolder;

    private FactoryDownloaderType type;

    private String refreshToken;
    private String clientId;
    private String clientSecret;

    @Override
    public IGooglePhotosBuilder setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    @Override
    public IGooglePhotosBuilder setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public IGooglePhotosBuilder setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Override
    public IGooglePhotosBuilder setLocalPhotoFolder(Path localPhotoFolder) {
        this.localPhotoFolder = localPhotoFolder;
        return this;
    }

    @Override
    public IGooglePhotosBuilder setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public IGooglePhotosBuilder setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    @Override
    public IGooglePhotosBuilder setDownloaderType(FactoryDownloaderType type) {
        this.type = type;
        return this;
    }

    @Override
    public GooglePhotos build() throws IllegalArgumentException {

        if (!checkParameters()) {
            throw new IllegalArgumentException("Missing arguments. Ensure all arguments are passed.");
        }

        UserCredentials credentials = UserCredentials.newBuilder()
                .setRefreshToken(refreshToken)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        if (!Files.isDirectory(localPhotoFolder)) {
            throw new IllegalArgumentException(
                    "Passed localPhotoFolder argument is not an existing folder: "
                            + localPhotoFolder
            );
        }

        Date startDateDate;
        try {
            startDateDate = GooglePhotosCommons.getDateFromString(startDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Passed startDate argument is in the incorrect format. Should be yyyy-MM-HH: "
                            + startDate);
        }

        GooglePhotos gPhotos = new GooglePhotos(credentials, startDateDate, localPhotoFolder);

        if (this.type != null) {
            gPhotos.setDownloader(type);
        }

        if (this.endDate != null) {
            throw new IllegalArgumentException("endDate is not yet supported.");
        }

        return gPhotos;
    }

    private boolean checkParameters() {
        if (!GooglePhotosCommons.isAnyStringNullOrBlank(
                startDate,
                refreshToken,
                clientId,
                clientSecret
        )) {
            return localPhotoFolder != null;
        }
        return false;
    }


}
