package model;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public interface ModModel {

    @NotNull String getModName();

    @NotNull Path getStandardModFolder();

    void performModificationsInStandardModFolder() throws ModModificationException, IOException;

    void performModifications(@NotNull Path modFolder) throws ModModificationException, IOException;
}
