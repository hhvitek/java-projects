package model;

public interface IModelObservable {
    void addObserver(IModelObserver observer);
    void removeObserver(IModelObserver observer);
    void notifyObservers();
}
