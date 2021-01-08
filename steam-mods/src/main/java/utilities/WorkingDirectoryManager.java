package utilities;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.file_locators.FileLocatorException;
import utilities.file_locators.FileLocatorImpl;
import utilities.file_locators.IFileLocator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Comparator;

public class WorkingDirectoryManager {

    private static final Logger logger = LoggerFactory.getLogger(WorkingDirectoryManager.class);

    public static void recreateDefaultWorkingDirectory(@NotNull Path templateDirectory, @NotNull Path workingDirectory) throws IOException {
        deleteDirectoryIfExists(workingDirectory);
        //createDirectory(workingDirectory);
        //if not exists creates
        copyDirectory(templateDirectory, workingDirectory);
    }

    public static void deleteDirectoryIfExists(@NotNull Path directory) throws IOException {
        try {
            deleteDirectoryThrowRuntimeError(directory);
        } catch (NoSuchFileException e) {
            logger.debug("Trying to delete non existent file: " + directory);
            return;
        } catch (RuntimeException e) {
            throw new IOException(e);
        }
    }

    public static int countFilesInFolder(Path folder) throws FileLocatorException {
        IFileLocator fileLocator = new FileLocatorImpl();
        return fileLocator.listAllFiles(folder).size();
    }

    /**
     * Streams do not allow for checked exceptions such as IOException.
     * Therefore those are converted into RuntimeExceptions
     *
     * Throws IOException (NoSuchFileException) if initial file does not exist.
     */
    private static void deleteDirectoryThrowRuntimeError(@NotNull Path directory) throws RuntimeException, IOException {
        Files.walk(directory)
            .sorted(Comparator.reverseOrder())
            .forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    private static void createDirectory(@NotNull Path directory) throws IOException {
        Files.createDirectory(directory);
    }

    private static void copyDirectory(@NotNull Path sourceDirectory, @NotNull Path destDirectory) throws IOException {
        Files.walk(sourceDirectory)
                .forEach(sourcePath -> copyThrowRuntimeError(
                            sourcePath,
                            destDirectory.resolve(sourceDirectory.relativize(sourcePath))
                        )
                );
    }

    /**
     * Streams do not allow for checked exceptions such as IOException.
     * Therefore those are converted into RuntimeExceptions
     *
     * If the file already exists fails.
     */
    private static void copyThrowRuntimeError(@NotNull Path source, @NotNull Path dest) throws
            RuntimeException {

        try {
            Files.copy(source, dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 }
