package app.view;

import app.MyCommons;
import app.Settings;
import app.ini.InvalidConfigFileFormatException;
import app.photos.GooglePhotos;
import app.photos.downloaders.Status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Controller {

    private IView view;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Future future;

    private GooglePhotos gPhotos;

    public void setView(IView view) {
        this.view = view;
    }

    public void startDownloadingPhotos(String configurationFile, String localFolder) {

        if (!MyCommons.areAllStringsNotNullAndNotBlank(configurationFile, localFolder)) {
            view.setStatus(Status.Error, String.format(
                    "Parameters have not been set. configurationFile: <%s>, localFolder: <%s>",
                    configurationFile, localFolder
            ));
        } else {
            if (future == null || future.isDone()) {
                future = executorService.submit(
                        () -> downloadPhotosThread(configurationFile, localFolder));
            } else {
                view.setStatus(Status.Ok, "The task has already been started.");
            }
        }
    }

    public void stopDownloadingPhotos() {
        if (future != null && !future.isDone()) {
            gPhotos.stop();
        }
    }

    private void downloadPhotosThread(String configurationFile, String localFolder) {
        Settings settings = new Settings();
        try {
            settings.loadSettingsFile(new File(configurationFile));
        } catch (InvalidConfigFileFormatException e) {
            view.setStatus(Status.Error, "The configuration file is invalid.");
            return;
        } catch (IOException e) {
            view.setStatus(Status.Error, "IOException during the config file parsing.");
            return;
        }

        try {
            gPhotos = GooglePhotos.newBuilder()
                                  .setRefreshToken(settings.getRefreshToken())
                                  .setLocalPhotoFolder(Path.of(localFolder))
                                  .setStartDate(settings.getStartDate())
                                  .setEndDate(settings.getEndDate())
                                  .setClientId(settings.getClientId())
                                  .setClientSecret(settings.getClientSecret())
                                  .build();

            gPhotos.attachObserver(view);
            gPhotos.downloadPhotos();
        } catch (IllegalStateException e) {
            view.setStatus(Status.Error, "The configuration file is invalid.");
        }
    }

}
