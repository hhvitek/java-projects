package model.modifications;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public interface Modification {
    @NotNull String getName();
    @NotNull String getDescription();

    void execute(@NotNull Path modFolder);
}
