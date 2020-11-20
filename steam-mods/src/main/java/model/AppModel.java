package model;

import model.persistent.exceptions.ConfigurationException;
import model.persistent.exceptions.NotInitializedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;

public interface AppModel {

    void load() throws IOException, ConfigurationException;
    void save() throws IOException, ConfigurationException;

    @NotNull Path getDefaultModFolder();
    void setDefaultModFolder(@NotNull Path modFolder);

    /**
     * @return returns default mod folder if no folder was chosen yet
     */
    @NotNull Path getChosenModFolder();
    void setChosenModFolder(@NotNull Path modFolder);

    @Nullable String getSelectedModificationsChainId();
    void setSelectedModificationsChain(@Nullable String chainId);
}
