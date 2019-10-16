package app.photos;

import java.nio.file.Path;
import java.time.format.DateTimeParseException;

import com.google.auth.oauth2.UserCredentials;

import app.downloader.FactoryDownloader.FactoryDownloaderType;

public final class GooglePhotosBuilder implements IGooglePhotosBuilder {

    private final GooglePhotos gPhotos = new GooglePhotos();

    private String refreshToken;
    private String clientId;
    private String clientSecret;

    @Override
    public IGooglePhotosBuilder setStartDate(String startDate)
            throws IllegalArgumentException, DateTimeParseException {
        gPhotos.setStartDate(GooglePhotos.getDateFromString(startDate));
        return this;
    }

    @Override
    public IGooglePhotosBuilder setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Override
    public IGooglePhotosBuilder setLocalPhotoFolder(Path localPhotoFolder) {
        gPhotos.setLocalPhotoFolder(localPhotoFolder);
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
    public GooglePhotos build() throws IllegalStateException, NullPointerException {
        UserCredentials credentials = UserCredentials.newBuilder().setRefreshToken(refreshToken)
                .setClientId(clientId).setClientSecret(clientSecret).build();
        gPhotos.setCredentials(credentials);
        return gPhotos;
    }

    @Override
    public IGooglePhotosBuilder setDownloaderType(FactoryDownloaderType type) {
        gPhotos.setDownloader(type);
        return this;
    }

}
