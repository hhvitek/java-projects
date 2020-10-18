package model.modifications;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class ExtractAllModifications extends BasicAbstractModification {

    public ExtractAllModifications(@NotNull String name, @NotNull String description) {
        super(name, description);
    }

    @Override
    public void execute(@NotNull Path modFolder) {

    }
}
