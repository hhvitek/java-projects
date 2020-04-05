package app.photos.downloaders;

import app.photos.GooglePhotosCommons;
import com.google.auth.oauth2.UserCredentials;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class GooglePhotosDownloaderTest {

    @Test
    void downloadPhotos() {

        String startDate = "2018-01-01";
        String endDate = "2018-01-02";
        String localFolderPath = "./FOTKY";
        String clientId = "clientId";
        String clientSecret = "clientSecret";
        String refreshToken = "refreshToken";

        IGooglePhotosDownloader gPhotosDownloader = new GooglePhotosDownloader();

        UserCredentials credentials = UserCredentials.newBuilder()
                                                     .setClientId(clientId)
                                                     .setClientSecret(clientSecret)
                                                     .setRefreshToken(refreshToken)
                                                     .build();

        gPhotosDownloader.downloadPhotos(GooglePhotosCommons.getDateFromString(startDate),
                                         GooglePhotosCommons.getDateFromString(endDate),
                                         Path.of(localFolderPath), credentials
        );
    }

}
