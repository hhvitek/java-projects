package model.exceptions;


public class ModificationException extends Exception {

    public ModificationException(String message) {
        super(message);
    }

    public ModificationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
