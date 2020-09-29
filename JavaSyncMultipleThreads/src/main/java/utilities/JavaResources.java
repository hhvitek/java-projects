package utilities;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

public final class JavaResources {

    public static final Logger logger = LoggerFactory.getLogger(JavaResources.class);

    private JavaResources() {
    }

    public static File getFileFromResources(@NotNull String file) throws IllegalArgumentException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        URL url = classLoader.getResource(file);
        if (url == null) {
            String errorMessage = "The resource-file not found!. Expected in: " + file;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } else {
            return new File(url.getFile());
        }
    }
}
