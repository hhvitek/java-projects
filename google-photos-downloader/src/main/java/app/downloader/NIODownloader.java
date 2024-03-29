package app.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Transfers bytes directly between 2 channels. Avoids memory buffering:
 * <pre>
 * {@code
 * URL u = new URL(url);
 * try (ReadableByteChannel readableByteChannel = Channels.newChannel(u.openStream());
 *      FileOutputStream fileOutputStream = new FileOutputStream(String path);
 *      FileChannel fileChannel = fileOutputStream.getChannel();) {
 *      fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
 * }
 * }
 * </pre>
 */
public class NIODownloader implements IDownloader {

    private static final Logger logger = LoggerFactory.getLogger(NIODownloader.class);

    private boolean replaceExisting;

    @Override
    public void setReplaceExisting(boolean replace) {
        replaceExisting = replace;
    }

    @Override
    public void downloadItem(String url, Path targetPath)
            throws IOException, FileAlreadyExistsException {

        if (!replaceExisting) {
            if (Files.exists(targetPath)) {
                throw new FileAlreadyExistsException(
                        String.format("The file: %s already exists.", targetPath.toString()));
            }
        }

        URL u = new URL(url);
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(u.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(targetPath.toString());
             FileChannel fileChannel = fileOutputStream.getChannel()
        ) {
            fileChannel.transferFrom(readableByteChannel, 0L, Long.MAX_VALUE);
        }
    }

}
