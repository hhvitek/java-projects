package timers;

public interface ICustomTimer extends ICustomTimerObservable {

    public void cancel();
    public boolean isAlreadyScheduled();
    public void scheduleAtFixedRate(int secondsRate);
}
