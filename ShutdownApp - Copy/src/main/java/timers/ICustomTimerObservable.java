package timers;

public interface ICustomTimerObservable {

    public void attachObserver(ICustomTimerObserver observer);
    public void detachObserver(ICustomTimerObserver observer);
}
