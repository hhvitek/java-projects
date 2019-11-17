package app.view;

import app.photos.downloaders.IObserver;
import app.photos.downloaders.Status;

public interface IView extends IObserver {

    void setController(Controller controller);

    void setLocalFolder(String folder);

    void setConfigurationFilePath(String path);

    void setStatus(Status status, String message);

    void startView();

}
