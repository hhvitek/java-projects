import actions.ShutdownAction;
import model.IModelObservable;
import model.IModelObserver;
import model.Model;
import timers.MyDateTimeParser;

import java.util.Date;

public class Presenter implements IModelObserver {

    private Model model;
    private ShutdownApp view;

    public Presenter() {
        model = new Model();
        model.attachObserver(this);
    }

    public void setView(ShutdownApp view) {
        this.view = view;
    }

    public void setShutdownAction() {
        model.setAction(new ShutdownAction());
    }

    public void setAfterDelay(String afterDelay) {
        model.setSecondsToCountDown(MyDateTimeParser.getSecondsFromDelay(afterDelay));
    }

    public void setExactTime(Date exactDate) {
        String exactTime = MyDateTimeParser.getStringHHmmFromDate(exactDate);
        int seconds = MyDateTimeParser.getSecondsBetweenNowToExactTime(exactTime);
        model.setSecondsToCountDown(seconds);
    }

    public void startCountDown() {
        model.startCountDown();
    }

    // "01:30"
    public void scheduleAfterDelay(String afterDelay) {
        int seconds = MyDateTimeParser.getSecondsFromDelay(afterDelay);
        model.startCountDown(seconds, 1);
    }

    // "14:44"
    public void scheduleExactTime(Date exactDate) {
        String exactTime = MyDateTimeParser.getStringHHmmFromDate(exactDate);
        int seconds = MyDateTimeParser.getSecondsBetweenNowToExactTime(exactTime);
        model.startCountDown(seconds, 1);
    }


    public void cancelTimer() {
        model.stopCountDown();
    }

    public String getCurrentCountDown() {
        int secondsToCountDownRemaining = model.getSecondsToCountDownRemaining();
        return MyDateTimeParser.getHHmmSSFromSeconds(secondsToCountDownRemaining);
    }

    public String getCountDownGoal() {
        int secondsToCountDownRemaining = model.getSecondsToCountDownRemaining();
        return MyDateTimeParser.getSumDateAndSeconds(new Date(), secondsToCountDownRemaining);
    }

    @Override
    public void notifyObserver() {
        view.updateCountDown();
    }
}
