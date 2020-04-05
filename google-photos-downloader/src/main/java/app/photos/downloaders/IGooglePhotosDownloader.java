package app.photos.downloaders;

import app.downloader.FactoryDownloader;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.UserCredentials;
import com.google.type.Date;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public interface IGooglePhotosDownloader extends IObservable {

    void downloadPhotos_throws(@NotNull Date startDate, @NotNull Date endDate,
                               @NotNull Path localFolder, @NotNull UserCredentials credentials)
            throws ApiException, IOException;

    void downloadPhotos(@NotNull Date startDate, @NotNull Date endDate, @NotNull Path localFolder,
                        @NotNull UserCredentials credentials);

    /**
     * Sets the method to download items. Beware this method overrides a behaviour for an already
     * existing setting for an overriding of existing files.
     *
     * @param type FactoryDownloaderType enum
     */
    void setItemDownloader(@NotNull FactoryDownloader.FactoryDownloaderType type);

    boolean hasError();

    String getErrorMessage();

    void stop();

}
