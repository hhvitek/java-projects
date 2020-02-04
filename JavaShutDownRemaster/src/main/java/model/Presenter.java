package model;

import actions.IAction;
import gui_swing.MainForm;

import java.time.Duration;
import java.time.LocalTime;

public class Presenter {

    private IModel model;

    public Presenter(IModel model) {
        this.model = model;
    }

    public void setCountDownGoal(Duration delay) {

        model.setCountDownGoal((LocalTime) delay.addTo(LocalTime.now()));
    }

    public LocalTime getCountDownGoal() {
        return model.getCountDownGoal();
    }

    public Duration getCountDownRemaining() {
        return model.getCountDownRemainingFromNow();
    }

    public void setAction(IAction action) {
        model.setAction(action);
    }

    public void performAction() {
        model.performAction();
    }
}
