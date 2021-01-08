package model.modifications.operations.directory;

import model.exceptions.ModificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.WorkingDirectoryManager;
import utilities.file_locators.FileLocatorException;
import utilities.file_locators.FileLocatorImpl;
import utilities.file_locators.IFileLocator;

import java.io.IOException;


public class ExtractAllOperationTest extends AbstractOperationTest {

    public ExtractAllOperationTest() {
        super("src/test/resources/model/modifications/operations");
        executableOperation = new ExtractAllDirOperation();
    }

    @Override
    protected String getOperationDirectoryName() {
        return "EXTRACT_ALL";
    }

    @Test
    public void extractAllZipInWorkingDirectory() throws IOException, ModificationException, FileLocatorException {
        executableOperation.execute(workingDirectory);

        int expectedFilesCount = 196;
        int actualFilesCount = WorkingDirectoryManager.countFilesInFolder(workingDirectory);

        Assertions.assertEquals(expectedFilesCount, actualFilesCount);
    }


}
