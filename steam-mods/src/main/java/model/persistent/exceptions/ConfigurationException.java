package model.persistent.exceptions;

/**
 * Thrown on general error when working with configuration storage data.
 *
 * For example:
 *  * requesting data for non-existing entity.
 *  * errors during loading/saving configuration data.
 */
public class ConfigurationException extends Exception {

    public ConfigurationException(String errorMessage) {
        super(errorMessage);
    }

    public ConfigurationException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
