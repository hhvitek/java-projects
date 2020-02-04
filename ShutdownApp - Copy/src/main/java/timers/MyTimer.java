package timers;

import javafx.beans.InvalidationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer implements ICustomTimer {

    private Timer timer;
    private int secondsRate;
    private int internalTimeoutCounter = 0;

    public int getSecondsRate() {
        return secondsRate;
    }

    private List<ICustomTimerObserver> observers;

    public MyTimer(ICustomTimerObserver observer) {
        observers = new ArrayList<>();
        attachObserver(observer);
    }


    @Override
    public void scheduleAtFixedRate(int secondsRate) {



        if (timer != null) {
            timer.cancel();
        }

        this.secondsRate = secondsRate;
        this.internalTimeoutCounter = 0;

        timer = new Timer();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        observers.forEach(x -> x.notifyObserver());
                        internalTimeoutCounter++;
                    }
                },
                0L,
                secondsRate*1000L);

    }

    @Override
    public boolean isAlreadyScheduled() {
        return timer != null;
    }

    @Override
    public void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public String toString() {
        return "MyTimer{" +
                "secondsRate=" + secondsRate +
                " internalTimeoutsCounter=" + internalTimeoutCounter +
                '}';
    }

    @Override
    public void attachObserver(ICustomTimerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(ICustomTimerObserver observer) {
        observers.remove(observer);
    }


}
