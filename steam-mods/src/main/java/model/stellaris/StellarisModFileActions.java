package model.stellaris;

import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class StellarisModFileActions {

    private Path descriptorModFile;

    public StellarisModFileActions(@NotNull Path initialDescriptorModFile) {
        setDescriptorModFile(initialDescriptorModFile);
    }

    public Path getDescriptorModFile() {
        return descriptorModFile;
    }

    public void setDescriptorModFile(@NotNull Path descriptorModFile) {
        this.descriptorModFile = descriptorModFile;
    }

    public @NotNull Path getParentFolderOfDescriptorModFile() {
        return descriptorModFile.getParent();
    }

    public @NotNull String getContent() throws IOException {
        return Files.readString(descriptorModFile);
    }

    public void removeArchiveItem() throws IOException {
        removeItem("archive");
    }

    private void removeItem(@NotNull String item) throws IOException {
        String content = getContent();

        content.replaceAll("^" + item + "=.*$", "");
    }

    public void removePathItem() throws IOException {
        removeItem("path");
    }

    public void removeBlankLines() {

    }


    public void addOrUpdatePathItem() {

    }

    public void addItem(@NotNull String item) {

    }
}
