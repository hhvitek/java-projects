package model.sql;

import actions.ActionAbstract;
import model.ScheduledAction;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface ISqlDbDao {

    void initDbConnection(String dbUrl) throws DbConnectionErrorException;
    void closeConnection() throws DbConnectionErrorException;

    List<ActionAbstract> getAllActions() throws DbConnectionErrorException;
    void deleteAllActions() throws DbConnectionErrorException;
    void saveAllActions(List<ActionAbstract> actions) throws DbConnectionErrorException;

    Optional<ActionAbstract> getAction(String name) throws DbConnectionErrorException;
    void saveAction(ActionAbstract action) throws DbConnectionErrorException;

    List<ScheduledAction> getAllScheduledActions() throws DbConnectionErrorException;
    List<ScheduledAction> getAllScheduledActionsByActionName(String name) throws DbConnectionErrorException;
    List<ScheduledAction> getAllEnabledScheduledActions() throws  DbConnectionErrorException;
    List<ScheduledAction> getAllEnabledScheduledActionsByActionName(String name) throws  DbConnectionErrorException;

    Optional<ScheduledAction> getScheduledActionByScheduledActionId(int scheduledActionId) throws  DbConnectionErrorException;
    void cancelScheduledAction(int scheduledActionId) throws DbConnectionErrorException;
    void deleteScheduledAction(int scheduledActionId) throws DbConnectionErrorException;

    void saveScheduledAction(ScheduledAction scheduledAction) throws DbConnectionErrorException;
    Optional<ScheduledAction> scheduleAction(String actionName, Duration d) throws  DbConnectionErrorException;
    Optional<ScheduledAction> scheduleAction(String actionName, Duration d, List<String> parameters) throws  DbConnectionErrorException;


}
