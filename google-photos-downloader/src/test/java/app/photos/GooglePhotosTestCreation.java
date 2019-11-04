package app.photos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class GooglePhotosTestCreation {

    @Test
    void creationUsingBuilderAllParameters401Unauthorized() {
        String clientId = "client1";
        String clientSecret = "clientSecret1";
        String refreshToken = "0000-1111-2222-3333";
        Path localFolder = Path.of(".");
        String startDate = "2019-05-05";

        GooglePhotos gPhotos = GooglePhotos.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .setLocalPhotoFolder(localFolder)
                .setStartDate(startDate)
                .build();

        gPhotos.downloadPhotos();

        String expected = "401 Unauthorized";
        Assertions.assertTrue(gPhotos.getLastError().contains(expected));
    }


}
