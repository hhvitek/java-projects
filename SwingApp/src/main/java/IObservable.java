public interface IObservable {

    void attachObserver(IObserver observer);

    void detachObserver(IObserver observer);

    void stop();

    void notifyObservers();

    void done();

}
