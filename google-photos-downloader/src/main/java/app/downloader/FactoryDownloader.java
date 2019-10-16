package app.downloader;

/**
 * Factory for Downloaders.
 * Usage:
 * {@code
 * private IDownloader downloader = FactoryDownloader.getDownloader(FactoryDownloaderType.IO);
 * }
 *
 * @author vitek
 *
 */
public class FactoryDownloader {

    public enum FactoryDownloaderType {
        IO, NIO, ASYNC
    }

    private FactoryDownloader() {
    }

    public static IDownloader getDownloader(FactoryDownloaderType type) {
        switch (type) {
        case IO:
            return new IODownloader();
        case NIO:
            return new NIODownloader();
        case ASYNC:
            throw new UnsupportedOperationException("Invalid operation.");
        default:
            break;
        }
        return null;
    }

}
