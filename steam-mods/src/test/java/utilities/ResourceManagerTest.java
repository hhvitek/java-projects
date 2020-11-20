package utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class ResourceManagerTest {

    private ResourceManager resourceManager = new ResourceManager();

    @Test
    public void getTestResourceTest() {

        String expectedFileName = "test_resource_name.txt";

        Path resourceFile = resourceManager.getFilePathFromResourceName("utilities/test_resource_name.txt");

        Assertions.assertNotNull(resourceFile);

        String actualFileName = resourceFile.getFileName().toString();

        Assertions.assertEquals(expectedFileName, actualFileName);
    }

}
