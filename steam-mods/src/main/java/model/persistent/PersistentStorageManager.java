package model.persistent;

import model.modifications.Modification;
import model.modificationschains.ModificationsChain;
import model.persistent.exceptions.ConfigurationException;
import model.persistent.exceptions.NotInitializedException;
import model.persistent.json.pojo.JsonAppPojo;
import model.persistent.json.pojo.JsonModificationPojo;
import model.persistent.json.pojo.JsonModificationsChainPojo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Interface representing how to load app's configuration.
 *
 * The app's persistent storage is divided into three parts:
 *  1] AppConfigurationSection
 *  2] List of ModificationsConfigurationSection
 *  3] List of ModificationsChainConfigurationSection
 *
 *
 * ***
 * What is Modification?
 *
 * Modification is one specific operation, that can be performed by this application.
 * Such as: copying file, modifying file content, un/compressing file etc.
 *
 * ***
 *
 * What is ModificationConfigurationSection?
 *
 * Modification is defined by it's attributes and functions/operations.
 * ModificationConfigurationSection is specification of attributes Modification in app's storage.
 *
 * Json storage looks as follows:
 *     ...
 *     "modifications": [
 *         {
 *             "id": "EXTRACT_ALL",
 *             "name": "Extract All",
 *             "description": "Extract all .zip files in the given folder.",
 *             "class": "model.modifications.ExtractAllModifications"
 *         },
 *         {
 *             ...
 *         }
 *      ...
 *
 * ***
 *
 * What is ModificationChainConfigurationSection?
 *
 * Modifications can be chained together to create one "big" operation consisting of ordered modifications.
 * This chain can be executed and "small" modifications are performed in orderly fashion one by one...
 *
 */
public interface PersistentStorageManager {
    void load() throws ConfigurationException, IOException;

    void save(@Nullable List<Modification> modifications,
              @Nullable List<ModificationsChain> chains,
              @Nullable Map<String, String> appProperties) throws ConfigurationException, IOException;

    @NotNull List<Modification> getModifications() throws NotInitializedException;

    @NotNull List<ModificationsChain> getModificationsChains() throws NotInitializedException;

    @NotNull Map<String, String> getAppProperties() throws NotInitializedException;
    @NotNull Path getDefaultModFolder() throws NotInitializedException;
}
