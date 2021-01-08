package model.modifications.operations.directory.moddownloader;

public class ModDownloaderTimeoutException extends RuntimeException {

    public ModDownloaderTimeoutException() {

    }

    public ModDownloaderTimeoutException(String message, Throwable e) {
        super(message, e);
    }
}
