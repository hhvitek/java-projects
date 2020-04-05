package app.photos.downloaders;

public interface IObserver {

    void updateObserver(int count, int total, String fileName, String message, Status status);

}
