package app.downloader;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageIODownloader implements IDownloader {

    private static final Logger logger = LoggerFactory.getLogger(ImageIODownloader.class);

    private boolean replaceExisting = false;

    @Override
    public void setReplaceExisting(boolean replace) {
        this.replaceExisting = replace;

    }

    @Override
    public void setTimeout(int seconds) {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadItem(String url, Path targetPath)
            throws IOException, FileAlreadyExistsException {

    }

}
