package model;

import model.persistent.exceptions.ConfigurationException;
import model.exceptions.ModificationException;
import model.exceptions.UnknownChain;
import model.modifications.Modification;
import model.modificationschains.ModificationsChain;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ModificationModel {

    void load() throws IOException, ConfigurationException;
    void save() throws IOException, ConfigurationException;

    @NotNull List<Modification> getAllModifications();
    @NotNull List<ModificationsChain> getAllModificationsChains();

    boolean existsModificationsChain(@Nullable String chainId);

    void modifyModification(@NotNull Modification modification);
    void modifyModificationsChain(@NotNull ModificationsChain chain);

    void executeChain(@NotNull String chainId, @Nullable Path file) throws UnknownChain, ModificationException, IOException;
}
