package model;

import actions.ActionAbstract;
import model.sql.DbConnectionErrorException;
import model.sql.ISqlDbDao;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class ModelImpl implements IModel {

    private ISqlDbDao db;

    public ModelImpl(ISqlDbDao db) {
        setSqlDb(db);
    }

    @Override
    public void setSqlDb(ISqlDbDao db) {
        this.db = db;
    }

    @Override
    public ISqlDbDao getSqlDb() {
        return db;
    }

    @Override
    public void initDbConnection(String dbUrl) throws DbConnectionErrorException {
        db.initDbConnection(dbUrl);
    }

    @Override
    public void closeConnection() throws DbConnectionErrorException {
        db.closeConnection();
    }

    @Override
    public List<ActionAbstract> getAllActions() throws DbConnectionErrorException {
        return db.getAllActions();
    }

    @Override
    public void deleteAllActions() throws DbConnectionErrorException {

    }

    @Override
    public void saveAllActions(List<ActionAbstract> actions) throws DbConnectionErrorException {
        db.saveAllActions(actions);
    }

    @Override
    public Optional<ActionAbstract> getAction(String name) throws DbConnectionErrorException {
        return db.getAction(name);
    }

    @Override
    public void saveAction(ActionAbstract action) throws DbConnectionErrorException {
        db.saveAction(action);
    }

    @Override
    public List<ScheduledAction> getAllScheduledActions() throws DbConnectionErrorException {
        return db.getAllScheduledActions();
    }

    @Override
    public List<ScheduledAction> getAllScheduledActionsByActionName(String name) throws DbConnectionErrorException {
        return db.getAllScheduledActionsByActionName(name);
    }

    @Override
    public List<ScheduledAction> getAllEnabledScheduledActions() throws DbConnectionErrorException {
        return db.getAllEnabledScheduledActions();
    }

    @Override
    public List<ScheduledAction> getAllEnabledScheduledActionsByActionName(String name) throws DbConnectionErrorException {
        return getAllEnabledScheduledActionsByActionName(name);
    }

    @Override
    public Optional<ScheduledAction> getScheduledActionByScheduledActionId(int scheduledActionId) throws DbConnectionErrorException {
        return db.getScheduledActionByScheduledActionId(scheduledActionId);
    }

    @Override
    public void cancelScheduledAction(int scheduledActionId) throws DbConnectionErrorException {
        db.cancelScheduledAction(scheduledActionId);
    }

    @Override
    public void deleteScheduledAction(int scheduledActionId) throws DbConnectionErrorException {
        db.deleteScheduledAction(scheduledActionId);
    }

    @Override
    public void saveScheduledAction(ScheduledAction scheduledAction) throws DbConnectionErrorException {
        db.saveScheduledAction(scheduledAction);
    }

    @Override
    public Optional<ScheduledAction> scheduleAction(String actionName, Duration d) throws DbConnectionErrorException {
        return db.scheduleAction(actionName, d);
    }

    @Override
    public Optional<ScheduledAction> scheduleAction(String actionName, Duration d, List<String> parameters) throws DbConnectionErrorException {
        return db.scheduleAction(actionName, d, parameters);
    }


}
