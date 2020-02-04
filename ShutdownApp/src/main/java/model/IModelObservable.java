package model;

import timers.ICustomTimerObserver;

public interface IModelObservable {

    public void attachObserver(IModelObserver observer);
    public void detachObserver(IModelObserver observer);
}
