package model.configuration;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public interface AppConfiguration {

    @NotNull Path getDefaultModFolder();
    void setDefaultModFolder(@NotNull Path defaultModFolder);

    @NotNull String getAdditionalProperty(@NotNull String propertyName) throws ConfigurationException;
    void setAdditionalProperty(@NotNull String propertyName, @NotNull String propertyValue);
}
