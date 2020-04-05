package app.view;

import app.photos.downloaders.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleView implements IView {

    private              Controller controller;
    private static final Logger     logger = LoggerFactory.getLogger(ConsoleView.class);

    private String configurationFile;
    private String localFolder;

    @Override
    public void startView() {
        controller.startDownloadingPhotos(configurationFile, localFolder);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void setLocalFolder(String folder) {
        localFolder = folder;
    }

    @Override
    public void setConfigurationFilePath(String path) {
        configurationFile = path;
    }

    @Override
    public void setStatus(Status status, String message) {
        if (status == Status.Error) {
            logger.error("Status: {}: {}", status, message);
            Exit();
        } else {
            logger.info("Status: {}: {}", status, message);
        }
    }

    @Override
    public void updateObserver(int count, int total, String fileName, String message,
                               Status status) {
        ;
    }

    private void Exit() {
        System.exit(0);
    }

}
