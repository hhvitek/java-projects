package model.modifications.operations.directory;

import model.exceptions.ModificationException;
import model.modifications.operations.Operation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utilities.file_locators.FileLocatorImpl;
import utilities.file_locators.IFileLocator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class AbstractDirectoryOperation implements Operation {

    protected Path modFolder;
    protected String preparedGlobPattern;

    @Override
    public void execute(@NotNull Path pathToFolder) throws IOException, ModificationException {
        this.modFolder = pathToFolder;
        preparedGlobPattern = prepareGlobPattern(pathToFolder);

        if (!Files.isDirectory(modFolder)) {
                throw new ModificationException("Cannot found requested directory: " + pathToFolder);
        }

        performRequestedOperationInDirectory();
    }

    private String prepareGlobPattern(@NotNull Path pathToFolder) {
        String absolutePath = pathToFolder.toAbsolutePath().toString();
        String backSlashedAbsolutePath = absolutePath.replaceAll("\\\\", "/");
        return "glob:" + backSlashedAbsolutePath;
    }

    protected void performRequestedOperationInDirectory() throws IOException, ModificationException {
        IFileLocator fileLocator = new FileLocatorImpl();

        List<Path> foundFiles = fileLocator.findUsingRegex(modFolder, getFileRegexToSearchFor());

        for(Path file: foundFiles) {
            performRequestedOperationOnFile(file);
        }
    }

    /**
     * Examples:
     * {@code
     *  /*.zip; only root level zip files /<any-string>.zip
     *  /**.zip; any .zip file in hierarchy /<any-path>/<...>/.../file.zip
     *  }
     * @return
     */
    protected abstract String getFileRegexToSearchFor();

    protected abstract void performRequestedOperationOnFile(@NotNull Path file) throws IOException, ModificationException;
}
