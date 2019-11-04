package app.photos.downloaders;

public interface IObservable {

    void attachObserver(IObserver observer);

    void detachObserver(IObserver observer);

    void notifyObservers();
}
