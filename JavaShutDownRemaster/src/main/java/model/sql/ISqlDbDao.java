package model.sql;

import actions.ActionAbstract;
import model.ScheduledAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Defines interface/methods to access internal Data
 * Specifies ONE partly hidden connection.
 * Any method uses ONE and the SAME internal connection.
 */
public interface ISqlDbDao {

    /**
     * Initializes connection to Data.
     * If the connection has already been established, it closes the old one
     * and establishes a new one.
     *
     * @param dbUrl JDBC connection string. E.g. "jdbc:sqlite:sqlite.sqlite3"
     * @throws DbConnectionErrorException
     */
    void initDbConnection(String dbUrl) throws DbConnectionErrorException;

    /**
     * Closes previously established connection or it does nothing.
     * @throws DbConnectionErrorException
     */
    void closeConnection() throws DbConnectionErrorException;

    /**
     * Get all Actions from DB.
     * @return returns List of ActionAbstract representing all Actions in DB.
     * @throws DbConnectionErrorException
     */
    List<ActionAbstract> getAllActions() throws DbConnectionErrorException;

    /**
     * Get specified Action from DB. The Action is specified by it's name.
     * @param name Name of requested Action.
     * @return Specified Action or empty Optional if requested Action is not in DB.
     * @throws DbConnectionErrorException
     */
    Optional<ActionAbstract> getAction(@NotNull String name) throws DbConnectionErrorException;

    /**
     * Cleans Action DB. Removes all existing Actions from DB.
     * @throws DbConnectionErrorException
     */
    void deleteAllActions() throws DbConnectionErrorException;

    /**
     * Saves specified Actions into DB. If the action already exists in the DB, please update the record.
     * @param actions List of Actions to be stored into DB.
     * @throws DbConnectionErrorException
     */
    void saveAllActions(@NotNull List<ActionAbstract> actions) throws DbConnectionErrorException;

    /**
     * Stores one Action into DB. If the action already exists in the DB, please update the record
     * @param action Action to be stored into DB.
     * @throws DbConnectionErrorException
     */
    void saveAction(@NotNull ActionAbstract action) throws DbConnectionErrorException;

    /**
     * Retrieves all existing scheduled Actions from DB.
     * @return
     * @throws DbConnectionErrorException
     */
    List<ScheduledAction> getAllScheduledActions() throws DbConnectionErrorException;

    /**
     * Retrieves scheduled Actions from DB. Specifically the following: SCHEDULED, RUNNING and FINISHED.
     * @return
     * @throws DbConnectionErrorException
     */
    List<ScheduledAction> getAllEnabledScheduledActions() throws  DbConnectionErrorException;

    /**
     * Retrieves one scheduled Action, identified by it's unique ID.
     * @param scheduledActionId scheduled Action unique ID.
     * @return
     * @throws DbConnectionErrorException
     */
    Optional<ScheduledAction> getScheduledActionByScheduledActionId(int scheduledActionId) throws  DbConnectionErrorException;

    /**
     * Cancels one scheduled action. Specifically it configures the scheduled Action's status to CANCELLED.
     * @param scheduledActionId scheduled Action unique ID.
     * @throws DbConnectionErrorException
     */
    void cancelScheduledAction(int scheduledActionId) throws DbConnectionErrorException;

    /**
     * Deletes one scheduled action. Specifically it deletes the database record.
     * @param scheduledActionId scheduled Action unique ID.
     * @throws DbConnectionErrorException
     */
    void deleteScheduledAction(int scheduledActionId) throws DbConnectionErrorException;

    /**
     * Stores all specified scheduled Actions into DB.
     * @param scheduledActions
     * @throws DbConnectionErrorException
     */
    void saveAllScheduledActions(@NotNull List<ScheduledAction> scheduledActions) throws DbConnectionErrorException;

    /**
     * Stores one specified scheduled Action into DB
     * @param scheduledAction
     * @throws DbConnectionErrorException
     */
    void saveScheduledAction(@NotNull ScheduledAction scheduledAction) throws DbConnectionErrorException;

    /**
     * Creates and stores new scheduled Action
     * @param actionName Action's name
     * @param d Duration when the action is to be executed.
     * @return New scheduled Action.
     * @throws DbConnectionErrorException
     */
    Optional<ScheduledAction> createScheduledAction(@NotNull String actionName, @NotNull Duration d) throws  DbConnectionErrorException;

    /**
     * Creates and stores new scheduled Action.
     * @param actionName Action's name
     * @param d Duration when the action is to be executed.
     * @param parameters Action's parameters. May be null.
     * @return New scheduled Action.
     * @throws DbConnectionErrorException
     */
    Optional<ScheduledAction> createScheduledAction(@NotNull String actionName, @NotNull Duration d, @Nullable List<String> parameters) throws  DbConnectionErrorException;

    List<ScheduledAction> getAllExpiredScheduledActions() throws DbConnectionErrorException;

}
