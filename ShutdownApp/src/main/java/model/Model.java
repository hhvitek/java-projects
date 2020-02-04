package model;

import actions.IAction;
import timers.ICustomTimerObserver;
import timers.MyTimer;

import java.util.ArrayList;
import java.util.List;

public class Model implements IModelObservable, ICustomTimerObserver {

    private MyTimer myTimer;

    private IAction action;
    private int  secondsToCountDown = -1;
    private int timeoutEveryXXSeconds = 1;
    private int secondsToCountDownRemaining = -1;

    List<IModelObserver> observers = new ArrayList<>();

    public Model() {
        myTimer = new MyTimer(this);
    }

    public void setAction(IAction action) {
        this.action = action;
    }

    public void setSecondsToCountDown(int secondsToCountDown) {

        this.secondsToCountDown = secondsToCountDown;
        this.secondsToCountDownRemaining = secondsToCountDown;
    }

    public void setTimeoutEveryXXSeconds(int timeoutEveryXXSeconds) {
        this.timeoutEveryXXSeconds = timeoutEveryXXSeconds;
    }

    public void startCountDown() {
        startCountDown(secondsToCountDown, timeoutEveryXXSeconds);
    }

    public void startCountDown(int secondsToCountDown, int timeoutEverySeconds) {
        this.secondsToCountDown = secondsToCountDown;
        this.timeoutEveryXXSeconds = timeoutEverySeconds;
        this.secondsToCountDownRemaining = secondsToCountDown;
        myTimer.scheduleAtFixedRate(timeoutEverySeconds);
    }

    public void stopCountDown() {
        myTimer.cancel();
    }

    public int getSecondsToCountDown() {
        return secondsToCountDown;
    }

    public int getTimeoutEveryXXSeconds() {
        return timeoutEveryXXSeconds;
    }

    public int getSecondsToCountDownRemaining() {
        return secondsToCountDownRemaining;
    }

    @Override
    public void notifyObserver() {
        secondsToCountDownRemaining -= timeoutEveryXXSeconds;
        observers.forEach(x -> x.notifyObserver());

        if (secondsToCountDownRemaining < 1) {
            myTimer.cancel();
            action.performAction();

        }
    }

    @Override
    public void attachObserver(IModelObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IModelObserver observer) {
        observers.remove(observer);
    }
}
