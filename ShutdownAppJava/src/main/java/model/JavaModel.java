package model;

import actions.IAction;
import actions.NoAction;
import com.sun.tools.javac.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;

public class JavaModel implements IModel {

    private static final Logger logger = LoggerFactory.getLogger(JavaModel.class);

    private IAction action;

    private LocalTime goalTime;

    public JavaModel() {

        action = new NoAction();
    }

    @Override
    public void setAction(IAction action) {
        this.action = action;
    }

    @Override
    public void setAction(IAction action, String parameters) {
        setAction(action);
    }

    @Override
    public void performAction() {
        if (action != null)
            action.execute();
        else
            logger.warn("Unexpected error: The internal variable action is NULL!");
    }

    @Override
    public void performAction(String parameters) {

    }

    @Override
    public void setCountDownGoal(LocalTime goalTime) {
        this.goalTime = goalTime;
    }

    @Override
    public LocalTime getCountDownGoal() {
        return goalTime;
    }

    @Override
    public Duration getCountDownRemainingFromNow() {

        LocalTime nowTime = LocalTime.now();

        int nowSeconds = nowTime.toSecondOfDay();
        int goalSeconds = goalTime.toSecondOfDay();

        int remainingSeconds = goalSeconds - nowSeconds;

        if (remainingSeconds < 0) {
            /* the goal is "before" now; we allow only 24h periods
             * goal: 21:00; now: 23:00 -> -01:00 -> 22:00
             */
            remainingSeconds = (24 * 60 * 60) % -remainingSeconds;
        }

        return Duration.ofSeconds(remainingSeconds);
    }
}
