package utilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class ResourceManager {

    public static @Nullable Path getFilePathFromResourceName(@NotNull String resourceName) {

        ClassLoader classLoader = ResourceManager.class.getClassLoader();

        URL resourceUrl = classLoader.getResource(resourceName);

        if (resourceUrl == null) {
            return null;
        }

        try {
            return Path.of(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static @Nullable Path getFilePathFromResourceNameOnNotFoundThrows(@NotNull String resourceName) throws RuntimeException {

        ClassLoader classLoader = ResourceManager.class.getClassLoader();

        URL resourceUrl = classLoader.getResource(resourceName);

        if (resourceUrl == null) {
            throw new RuntimeException("Resource file not found! " + resourceName);
        }

        try {
            return Path.of(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Resource file not found! " + resourceName, e);
        }
    }
}
