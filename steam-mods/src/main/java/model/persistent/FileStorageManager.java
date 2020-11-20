package model.persistent;

import model.persistent.exceptions.ConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The more specific interface for loading/storing app's data using file-based storage such as ini, json based formats.
 */
public interface FileStorageManager extends PersistentStorageManager {
    void load(@NotNull Path configurationFile) throws IOException, ConfigurationException;
    void save(@NotNull Path configurationFile) throws IOException;
}
