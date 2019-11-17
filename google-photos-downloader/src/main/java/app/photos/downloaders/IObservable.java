package app.photos.downloaders;

import org.jetbrains.annotations.NotNull;

public interface IObservable {

    void attachObserver(@NotNull IObserver observer);

    void detachObserver(@NotNull IObserver observer);

    //void notifyObservers();

}
