package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public interface Model {

    @NotNull List<String> getSupportedModModelsNames();

    void setSelectedModModel(@NotNull String modModelName) throws ModModificationException;
    @Nullable String getSelectedModModel();

    void performModModelModificationOnSelectedModModel() throws ModModificationException;
    void performModModelModification(@NotNull String modModelName) throws ModModificationException;

    void copyModDescriptorFileToCorrectModLocation() throws IOException;

    void performModificationAndCopyDescriptorFile() throws IOException, ModModificationException;
}
