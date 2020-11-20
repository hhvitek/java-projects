package utilities;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class WorkingDirectoryManager {

    public static void recreateDefaultWorkingDirectory(@NotNull Path templateDirectory, @NotNull Path workingDirectory) throws IOException {
        deleteDirectory(workingDirectory);
        //createDirectory(workingDirectory);
        //if not exists creates
        copyDirectory(templateDirectory, workingDirectory);
    }

    private static void deleteDirectory(@NotNull Path directory) throws IOException {
        try {
            deleteDirectoryThrowRuntimeError(directory);
        } catch (RuntimeException e) {
            throw new IOException(e);
        }
    }

    /**
     * Streams do not allow for checked exceptions such as IOException.
     * Therefore those are converted into RuntimeExceptions
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
