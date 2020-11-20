package model.modifications;

import model.exceptions.ModificationException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public interface Modification {
    @NotNull String getId();
    @NotNull String getName();
    void setName(@NotNull String name);
    @NotNull String getDescription();
    void setDescription(@NotNull String description);
    @NotNull Class<?> getClazz();

    void execute(@NotNull Path pathToFile) throws IOException, ModificationException;
}
