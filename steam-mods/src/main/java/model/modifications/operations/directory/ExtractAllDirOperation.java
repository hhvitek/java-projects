package model.modifications.operations.directory;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExtractAllDirOperation extends AbstractDirectoryOperation {

    public static final String DEFAULT_NAME = "ExtractAll";
    public static final String DEFAULT_DESC = "Extract all .zip files in the given folder.";

    @Override
    protected String getFileRegexToSearchFor() {

        return preparedGlobPattern + "/*.zip";
    }

    @Override
    protected void performRequestedOperationOnFile(@NotNull Path file) throws IOException {
        InputStream inputStream = new FileInputStream(file.toFile());
        unzip(
                inputStream,
                modFolder.resolve(
                        getFileNameWithoutExtension(
                                file.getFileName().toString()
                        )
                )
        );
    }

    private void unzip(InputStream is, Path targetDir) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(is)) {
            for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null; ) {
                Path resolvedPath = targetDir.resolve(ze.getName());
                if (ze.isDirectory()) {
                    Files.createDirectories(resolvedPath);
                } else {
                    Files.createDirectories(resolvedPath.getParent());
                    Files.copy(zipIn, resolvedPath);
                }
            }
        }
    }

    private String getFileNameWithoutExtension(String fileNameWithExtension) {
        return fileNameWithExtension.replaceAll("\\.[^.]+$", "");
    }


}
