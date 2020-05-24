package model.simplemodel;

import model.IModel;
import model.IModelObservable;
import model.IModelObserver;

import java.util.LinkedList;
import java.util.List;

public class SimpleModelObservable implements IModelObservable {

    private IModel model;
    private List<IModelObserver> observers;

    public SimpleModelObservable() {
        observers = new LinkedList<>();
    }

    public SimpleModelObservable(IModel model) {
        this.model = model;
    }

    @Override
    public void addObserver(IModelObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(IModelObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(
                observer -> observer.update()
        );
    }
}
