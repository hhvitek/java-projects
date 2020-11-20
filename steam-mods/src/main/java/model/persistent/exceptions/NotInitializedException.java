package model.persistent.exceptions;

/**
 * Thrown if a ConfigurationManager is used but it has not been initialized yet:
 *
 * For example:
 *      ConfigurationManager manager = new ConfigurationManagerImplementation(...);
 *      List<ModificationsConfiguration> modifications = manager.getModifications();
 */
public class NotInitializedException extends RuntimeException {

    public NotInitializedException(String errorMessage) {
        super(errorMessage);
    }
}
