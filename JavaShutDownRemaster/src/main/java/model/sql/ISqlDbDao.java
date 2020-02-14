package model.sql;

import actions.ActionAbstract;
import model.ScheduledAction;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface ISqlDbDao {

    void initDbConnection(String dbUrl, String user, char[] password) throws DbConnectionErrorException;
    void closeConnection() throws DbConnectionErrorException;

    List<ActionAbstract> getAllActions() throws DbConnectionErrorException;
    List<ScheduledAction> getAllScheduledActions() throws DbConnectionErrorException;
    List<ScheduledAction> getAllScheduledActionsByActionName(String name) throws DbConnectionErrorException;
    List<ScheduledAction> getAllEnabledScheduledActions() throws  DbConnectionErrorException;
    List<ScheduledAction> getAllEnabledScheduledActionsByActionName(String name) throws  DbConnectionErrorException;

    Optional<ScheduledAction> getScheduledActionByScheduledActionId(int scheduledActionId) throws  DbConnectionErrorException;
    void cancelScheduledAction(int scheduledActionId) throws DbConnectionErrorException;

    ScheduledAction scheduleAction(String actionName, Duration d) throws  DbConnectionErrorException;
    ScheduledAction scheduleAction(String actionName, Duration d,List<String> parameters) throws  DbConnectionErrorException;


}
