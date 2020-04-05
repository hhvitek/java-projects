package app.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Uses buffering into the application memory:
 * <pre>
 * {@code
 * Files.copy(InputStream in, Path target, StandardCopyOption opt);
 * }
 * </pre>
 */
public class IODownloader implements IDownloader {

    private static final Logger logger = LoggerFactory.getLogger(IODownloader.class);

    private boolean replaceExisting;

    @Override
    public void setReplaceExisting(boolean replace) {
        replaceExisting = replace;
    }

    @Override
    public void downloadItem(String url, Path targetPath)
            throws IOException, FileAlreadyExistsException {
        URI u = URI.create(url);
        logger.info("Copying file to: {}", targetPath);

        try (InputStream in = u.toURL()
                               .openStream()
        ) {
            if (replaceExisting) {
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(in, targetPath);
            }
        }
    }

}
