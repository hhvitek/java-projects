package app.downloader;

/**
 * Factory for Downloaders.
 * Usage:
 * <pre>
 * {@code
 * private IDownloader downloader = FactoryDownloader.getDownloader(FactoryDownloaderType.IO);
 * private IDownloader downloader = FactoryDownloader.getDownloader(FactoryDownloaderType.NIO);
 * }
 * </pre>
 */
public final class FactoryDownloader {

    public enum FactoryDownloaderType {
        IO,
        NIO
    }

    private FactoryDownloader() {
    }

    public static IDownloader getDownloader(FactoryDownloaderType type) {
        switch (type) {
            case IO:
                return new IODownloader();
            case NIO:
                return new NIODownloader();
            default:
                throw new UnsupportedOperationException("Unknown parameter: " + type);
        }
    }

    public static IDownloader getDefaultDownloader() {
        return new IODownloader();
    }

}
