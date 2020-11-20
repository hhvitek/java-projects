package utilities.file_locators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.ResourceManager;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

abstract class FileLocatorTest {

    IFileLocator locator;
    static Path rootFolder;

    @BeforeAll
    static void locateAndSetRootFolder() {

        rootFolder = ResourceManager.getFilePathFromResourceName("utilities/file_locators/root_folder_name");

        if (rootFolder == null) {
            Assertions.fail("Cannot found specified root folder");
        }
    }

    abstract void listAllFilesTest();

    @Test
    void listAllRegularFilesTest()
    {
        int expectedFoundFiles = 8;

        List<Path> foundFiles = locator.listAllFiles(rootFolder);
        int actualFoundFiles = foundFiles.size();

        Assertions.assertEquals(expectedFoundFiles, actualFoundFiles);
    }

}
