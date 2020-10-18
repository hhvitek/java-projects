package model.modifications;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public abstract class BasicAbstractModification implements Modification{

    protected String name;
    protected String description;

    protected BasicAbstractModification(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getDescription() {
        return description;
    }

    @Override
    public abstract void execute(@NotNull Path modFolder);
}
