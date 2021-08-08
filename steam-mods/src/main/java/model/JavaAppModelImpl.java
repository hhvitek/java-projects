package model;

import model.persistent.PersistentStorageManager;
import model.persistent.exceptions.ConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class JavaAppModelImpl extends AbstractObservableModel implements AppModel {

    private final PersistentStorageManager storageManager;

    private Path defaultModFolder;
    private Path chosenModFolder;
    private String selectedModificationsChainId;

    public JavaAppModelImpl(@NotNull PersistentStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Override
    public void load() throws IOException, ConfigurationException {
        storageManager.load();

        loadValuesFromStorage();
        ifNoModFolderIsChosenChoseDefaultModFolder();
    }

    private void loadValuesFromStorage() {
        defaultModFolder = storageManager.getDefaultModFolder();

        Map<String, String> appValues = storageManager.getAppProperties();
        chosenModFolder = storageManager.getSelectedModFolder();
        selectedModificationsChainId = storageManager.getSelectedModificationChain();
    }

    private void ifNoModFolderIsChosenChoseDefaultModFolder() {
        if (selectedModificationsChainId == null || selectedModificationsChainId.isBlank()) {
            setChosenModFolder(defaultModFolder);
        }
    }

    @Override
    public void save() throws IOException, ConfigurationException {
        storageManager.save(null, null, getMapFromValues());
    }

    private Map<String, String> getMapFromValues() {
        Map<String, String> appValues = new LinkedHashMap<>();

        appValues.put("default_mod_folder", defaultModFolder.toString());
        appValues.put("chosen_mod_folder", chosenModFolder.toString());
        appValues.put("selected_modifications_chain", selectedModificationsChainId);

        return appValues;
    }

    @Override
    public @NotNull Path getDefaultModFolder() {
        return defaultModFolder;
    }

    @Override
    public void setDefaultModFolder(@NotNull Path modFolder) {
        defaultModFolder = modFolder;
    }

    @Override
    public @NotNull Path getChosenModFolder() {
        return chosenModFolder;
    }

    @Override
    public void setChosenModFolder(@NotNull Path modFolder) {
        chosenModFolder = modFolder;
    }

    @Override
    public @Nullable String getSelectedModificationsChainId() {
        return selectedModificationsChainId;
    }

    @Override
    public void setSelectedModificationsChain(@Nullable String chainId) {
        selectedModificationsChainId = chainId;
    }
}
