package app.photos.downloaders;

import app.ini.IIniConfig;
import app.ini.InvalidConfigFileFormatException;
import app.ini.myini.CustomIIniConfig;
import app.photos.GooglePhotosCommons;
import com.google.auth.oauth2.UserCredentials;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

class GooglePhotosDownloaderTest {

    static final String LOCAL_FOLDER_PATH = "src/test/resources/app/ini/input.ini";

    @Test
    void downloadPhotos()
            throws IOException, InvalidConfigFileFormatException {
        IIniConfig ini = new CustomIIniConfig();
        ini.load(new File(LOCAL_FOLDER_PATH));

        String startDate = ini.getValue("values", "startDate");
        String endDate = ini.getValue("values", "endDate");
        String localFolderPath = ini.getValue("values", "localFolder");
        String clientId = ini.getValue("values", "clientId");
        String clientSecret = ini.getValue("values", "clientSecret");
        String refreshToken = ini.getValue("values", "refreshToken");

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
