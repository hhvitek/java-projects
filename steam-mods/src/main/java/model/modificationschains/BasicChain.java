package model.modificationschains;

import model.exceptions.ModificationException;
import model.modifications.Modification;
import model.modifications.ModificationNotFound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BasicChain implements ModificationsChain {

    private String id;
    private String name;
    private String desc;

    private List<Modification> modifications;

    public BasicChain(@NotNull String id, @NotNull String name) {
        this.id = id;
        this.name = name;

        modifications = new ArrayList<>();
    }

    public BasicChain(@NotNull String id, @NotNull String name, @NotNull String desc) {
        this(id, name);
        this.desc = desc;
    }


    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getDescription() {
        return desc;
    }

    @Override
    public void setDescription(@NotNull String desc) {
        this.desc = desc;
    }

    @Override
    public @NotNull List<Modification> getModifications() {
        return new ArrayList<>(modifications);
    }

    @Override
    public void removeAllModificationsById(@NotNull String modificationId) throws ModificationNotFound {
        modifications.removeIf(
                x -> x.getId().equals(modificationId)
        );
    }

    @Override
    public void setModifications(@NotNull List<Modification> modifications) {
        this.modifications = new ArrayList<>(modifications);
    }

    @Override
    public void addModification(@NotNull Modification modification) {
        modifications.add(modification);
    }

    @Override
    public void execute(@Nullable Path file) throws ModificationException, IOException {
        for (Modification modification: modifications) {
            modification.execute(file);
        }
    }
}
