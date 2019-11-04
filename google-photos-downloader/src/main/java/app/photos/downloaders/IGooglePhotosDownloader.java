package app.photos.downloaders;

import app.downloader.FactoryDownloader;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.UserCredentials;
import com.google.type.Date;

import java.io.IOException;
import java.nio.file.Path;

public interface IGooglePhotosDownloader {
    void downloadPhotos(Date startDate,
                        Date endDate,
                        Path localFolder,
                        UserCredentials credential
    ) throws ApiException, IOException;

    void setItemDownloader(FactoryDownloader.FactoryDownloaderType type);

    void setOverrideExistingFiles(boolean override);
}
