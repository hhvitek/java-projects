package model;

import model.exceptions.ModificationException;
import model.exceptions.UnknownChain;
import model.modifications.Modification;
import model.modificationschains.ModificationsChain;
import model.persistent.PersistentStorageManager;
import model.persistent.exceptions.ConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaModificationModelImpl implements ModificationModel {

    private Map<String, Modification> modifications;
    private Map<String, ModificationsChain> chains;

    private final PersistentStorageManager storageManager;

    public JavaModificationModelImpl(@NotNull PersistentStorageManager storageManager) {
        this.storageManager = storageManager;

        modifications = new LinkedHashMap<>();
        chains = new LinkedHashMap<>();
    }


    @Override
    public void load() throws IOException, ConfigurationException {
        storageManager.load();

        loadValuesFromStorage();
    }

    private void loadValuesFromStorage() {
        storageManager.getModifications().forEach(
                this::modifyModification
        );

        storageManager.getModificationsChains().forEach(
                this::modifyModificationsChain
        );
    }

    @Override
    public void save() throws IOException, ConfigurationException {
        storageManager.save(
                modifications.values().stream().collect(Collectors.toList()),
                new ArrayList<>(chains.values()),
                null
        );
    }

    @Override
    public @NotNull List<Modification> getAllModifications() {
        return modifications.values().stream().collect(Collectors.toList());
    }

    @Override
    public @NotNull List<ModificationsChain> getAllModificationsChains() {
        return new ArrayList<>(chains.values());
    }

    @Override
    public boolean existsModificationsChain(@Nullable String chainId) {
        if (chains.containsKey(chainId)) {
            return true;
        }
        return false;
    }

    @Override
    public void modifyModification(@NotNull Modification modification) {
        modifications.put(modification.getId(), modification);
    }

    @Override
    public void modifyModificationsChain(@NotNull ModificationsChain chain) {
        chains.put(chain.getId(), chain);
    }

    @Override
    public void executeChain(@NotNull String chainId, @Nullable Path file) throws UnknownChain, ModificationException, IOException {
        if (!existsModificationsChain(chainId)) {
            throw new UnknownChain(chainId);
        } else {
            ModificationsChain chain = chains.get(chainId);
            chain.execute(file);
        }
    }
}
