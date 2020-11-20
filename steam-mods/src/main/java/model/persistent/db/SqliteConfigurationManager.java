package model.persistent.db;

import model.modifications.Modification;
import model.modificationschains.ModificationsChain;
import model.persistent.PersistentStorageManager;
import model.persistent.exceptions.ConfigurationException;
import model.persistent.exceptions.NotInitializedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class SqliteConfigurationManager implements PersistentStorageManager {

    private EntityManager entityManager;

    public SqliteConfigurationManager(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void load() throws ConfigurationException, IOException {

    }

    @Override
    public void save(@Nullable List<Modification> modifications, @Nullable List<ModificationsChain> chains, @Nullable Map<String, String> appProperties) throws ConfigurationException, IOException {

    }

    @Override
    public @NotNull List<Modification> getModifications() throws NotInitializedException {
        return null;
    }

    @Override
    public @NotNull List<ModificationsChain> getModificationsChains() throws NotInitializedException {
        return null;
    }

    @Override
    public @NotNull Map<String, String> getAppProperties() throws NotInitializedException {
        return null;
    }

    @Override
    public @NotNull Path getDefaultModFolder() throws NotInitializedException {
        return null;
    }
}
