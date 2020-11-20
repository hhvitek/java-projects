package model.modificationschains;

import model.exceptions.ModificationException;
import model.modifications.Modification;
import model.modifications.ModificationNotFound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public interface ModificationsChain {

    @NotNull String getId();
    @NotNull String getName();
    void setName(@NotNull String name);
    @NotNull String getDescription();
    void setDescription(@NotNull String desc);

    @NotNull List<Modification> getModifications();
    void removeAllModificationsById(@NotNull String modificationId) throws ModificationNotFound;
    void setModifications(@NotNull List<Modification> modifications);
    void addModification(@NotNull Modification modification);

    void execute(@Nullable Path file) throws ModificationException, IOException;
}
