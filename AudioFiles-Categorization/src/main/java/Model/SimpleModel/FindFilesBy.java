package Model.SimpleModel;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindFilesBy {

    private static final Logger logger = LoggerFactory.getLogger(FindFilesBy.class);
    private Path rootFolder;

    public FindFilesBy(@NotNull Path rootFolder) {
        this.rootFolder = rootFolder;
    }

    public Path getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(@NotNull Path rootFolder) {
        this.rootFolder = rootFolder;
    }

    public List<File> findFilesByExtensions(@NotNull List<String> extensions) {
        if (!isRootFolderValid()) {
            logger.error("Root folder is INVALID: \"{}\"", rootFolder);
            return Collections.emptyList();
        }

        List<File> files = new ArrayList<>();
        for(String extension: extensions) {
            files.addAll(findFilesByExtension(extension));
        }
        return files;
    }

    public List<File> findFilesByExtension(@NotNull String extension) {
        if (!isRootFolderValid()) {
            logger.error("Root folder is INVALID: \"{}\"", rootFolder);
            return Collections.emptyList();
        }

        try (Stream<Path> matchedFilePath = Files.find(
                rootFolder,
                10,
                (path, basicFileAttribute) -> {
                    if (basicFileAttribute.isRegularFile()) {
                        if (path.toString().endsWith("." + extension)) {
                            return true;
                        }
                    }
                    return false;
                }
        )) {
            return matchedFilePath
                    .map(x -> x.toFile())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("IOException encountered during audio extension search.", e);
        }
        return Collections.emptyList();
    }

    private boolean isRootFolderValid() {
        if (rootFolder == null || !rootFolder.toFile().isDirectory()) {
            return false;
        }
        return true;
    }
}
