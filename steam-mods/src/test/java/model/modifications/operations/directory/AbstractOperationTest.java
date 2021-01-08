package model.modifications.operations.directory;

import model.modifications.operations.directory.AbstractDirectoryOperation;
import org.junit.jupiter.api.BeforeEach;
import utilities.WorkingDirectoryManager;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

abstract public class AbstractOperationTest {

    protected String rootDirectory;

    protected Path templateDirectory;
    protected Path workingDirectory;

    protected AbstractDirectoryOperation executableOperation;

    protected AbstractOperationTest(String rootDirectory) {
        this.rootDirectory = rootDirectory;

        setTemplateAndWorkingDirectoriesBasedOnDirectoryName(getOperationDirectoryName());
    }

    protected abstract String getOperationDirectoryName();

    public void setTemplateAndWorkingDirectoriesBasedOnDirectoryName(String directoryName) {
        templateDirectory = Path.of(rootDirectory, directoryName, "do-not-change").toAbsolutePath();
        workingDirectory = Path.of(rootDirectory, directoryName, "mod").toAbsolutePath();
    }

    @BeforeEach
    public void init() {
        try {
            createAndPrepareTestDirectory();
        } catch (IOException e) {
            fail("Cannot recreate test directory. " +
                    "Please check if the following folder exists and is not empty: " + templateDirectory.toAbsolutePath(),
                    e);
        }
    }

    public void createAndPrepareTestDirectory() throws IOException {
        WorkingDirectoryManager.recreateDefaultWorkingDirectory(templateDirectory, workingDirectory);
    }
}
