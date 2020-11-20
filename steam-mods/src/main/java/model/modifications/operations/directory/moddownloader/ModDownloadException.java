package model.modifications.operations.directory.moddownloader;

import model.exceptions.ModificationException;

public class ModDownloadException extends Exception {

    public ModDownloadException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
