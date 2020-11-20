package model.modifications.operations.directory;

import model.exceptions.ModificationException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CopyModDescriptorFilesToModFolderRootDirOperation extends AbstractDirectoryOperation {

    @Override
    protected String getFileRegexToSearchFor() {
        return preparedGlobPattern + "/*/*.mod";
    }

    @Override
    protected void performRequestedOperationOnFile(@NotNull Path file) throws IOException, ModificationException {
        Path destCopiedFile = modFolder.resolve(file.getParent().getFileName().toString() + ".mod");

        Files.copy(file, destCopiedFile, StandardCopyOption.REPLACE_EXISTING);
    }


}
