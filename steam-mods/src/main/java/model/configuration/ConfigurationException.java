package model.configuration;

public class ConfigurationException extends Exception {

    public ConfigurationException(String errorMessage) {
        super(errorMessage);
    }

    public ConfigurationException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
