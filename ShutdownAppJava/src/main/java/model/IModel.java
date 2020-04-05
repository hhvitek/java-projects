package model;

import actions.IAction;

import java.time.Duration;
import java.time.LocalTime;

public interface IModel {

    /**
     * Sets the specified action to be performed by performAction() method.
     * @param action
     */
    void setAction(IAction action);

    /**
     * Sets the specified action to be performed by performAction() method.
     * @param action
     */
    void setAction(IAction action, String parameters);

    /**
     * Performs the action. If no action has been specified by setAction() method, does nothing.
     */
    void performAction();

    /**
     * Performs the action. If no action has been specified by setAction() method, does nothing.
     */
    void performAction(String parameters);

    /**
     * CountDownGoal is the Time in the future.
     * @param goalTime
     */
    void setCountDownGoal(LocalTime goalTime);

    LocalTime getCountDownGoal();

    /**
     *
     * @return Duration Time Span between Now and the CountDownGoal
     */
    Duration getCountDownRemainingFromNow();
}
