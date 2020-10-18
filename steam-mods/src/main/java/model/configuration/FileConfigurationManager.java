package model.configuration;

import model.configuration.ConfigurationException;
import model.configuration.ConfigurationManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public interface FileConfigurationManager extends ConfigurationManager {
    void load(@NotNull Path configurationFile) throws IOException, ConfigurationException;
    void save(@NotNull Path configurationFile) throws IOException;
}
