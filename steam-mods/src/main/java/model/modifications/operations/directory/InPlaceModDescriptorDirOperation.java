package model.modifications.operations.directory;

import model.exceptions.ModificationException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class InPlaceModDescriptorDirOperation extends AbstractDirectoryOperation {

    @Override
    protected String getFileRegexToSearchFor() {
        return preparedGlobPattern + "/*/*.mod";
    }

    @Override
    protected void performRequestedOperationOnFile(@NotNull Path modFile) throws IOException, ModificationException {
        String fileContent = Files.readString(modFile);

        String modifiedFileContent = removeFlagLineFromString(fileContent, "archive");
        modifiedFileContent = removeFlagLineFromString(modifiedFileContent, "path");
        modifiedFileContent = appendModifiedPathLine(modifiedFileContent, modFile);
        modifiedFileContent = removeEmptyLines(modifiedFileContent);

        Files.writeString(modFile, modifiedFileContent, Charset.forName("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String removeFlagLineFromString(String value, String flag) {
        // (?m) to interpret ^ as start of a line instead of a whole string
        return value.replaceAll("(?m)^" + flag + "=.*", "");
    }

    private String appendModifiedPathLine(String value, Path modFile) {
        return value + "\n" + getPathLineFromModDescriptorFile(modFile);
    }

    private String getPathLineFromModDescriptorFile(@NotNull Path modFile) {

        String pathStringified = modFile.getParent().toAbsolutePath().toString();
        String forwardSlashed = pathStringified.replaceAll("\\\\", "/");

        return "path=\"" + forwardSlashed + "\"\n" ;
    }

    private String removeEmptyLines(String value) {
        return value.replaceAll("(?m)^\n", "");
    }


}
