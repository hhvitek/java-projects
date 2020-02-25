package model.sql.jdbc_dao;

import actions.ActionAbstract;
import dynamic_classloader.ClassLoadingException;
import dynamic_classloader.LoaderByClassNames;
import model.ScheduledAction;
import model.sql.DbConnectionErrorException;
import model.sql.ISqlDbDao;
import net.bytebuddy.asm.Advice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

public class JdbcSqlImpl implements ISqlDbDao {

    private Connection connection;
    private String dbUrl;

    private static final Logger logger = LoggerFactory.getLogger(JdbcSqlImpl.class);

    @Override
    public void initDbConnection(String dbUrl) throws DbConnectionErrorException {
        if (this.connection != null) {
            closeConnection();
        }

        this.dbUrl = dbUrl;

        try {
            this.connection = DriverManager.getConnection(dbUrl, null, null);
        } catch (SQLException e) {
            String errorMessage = "Failed to create any connection to the DB: " + dbUrl;
            logger.error(errorMessage, e);
            throw new DbConnectionErrorException(errorMessage, e);
        }
    }

    @Override
    public void closeConnection() throws DbConnectionErrorException {
        if (this.connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                String errorMessage = "Failed to close the connection to the DB: " + dbUrl;
                logger.error(errorMessage, e);
                throw new DbConnectionErrorException(errorMessage, e);
            }
        }
    }

    @Override
    public List<ActionAbstract> getAllActions() throws DbConnectionErrorException {
        String query = "SELECT * from action";

        Connection conn = getConnection();

        List<ActionAbstract> actions = new ArrayList<>();
        try (PreparedStatement prepStatement = conn.prepareStatement(query)) {
            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()) {
                ActionAbstract action = createAction(rs);
                actions.add(action);
            }
        } catch (SQLException e) {
            logger.warn("Error working with DB.", e);
            throw new DbConnectionErrorException(e);
        }

        return actions;
    }

    /**
     * Creates object of ActionAbstract from ResultSet.
     * The method is expecting the following columns in the set:
     * id, name, class_name, description, is_producing_result, parameters_count
     * @param rs
     * @return
     * @throws DbConnectionErrorException
     */
    private ActionAbstract createAction(ResultSet rs) throws DbConnectionErrorException {

        String className = "";
        try {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            className = rs.getString("class_name");
            String desc = rs.getString("description");
            boolean isProducingResult = rs.getBoolean("is_producing_result");
            int parametersCount = rs.getInt("parameters_count");

            ActionAbstract action = LoaderByClassNames.load(className);
            action.load(name, desc, parametersCount, isProducingResult);
            return action;

        } catch (SQLException e) {
            throw new DbConnectionErrorException(e);
        } catch (ClassLoadingException e) {
            throw new DbConnectionErrorException("Unrecognized class_name in database: " + className, e);
        }
    }

    @Override
    public void saveAllActions(@NotNull List<ActionAbstract> actions) throws DbConnectionErrorException {

        for(ActionAbstract action: actions) {
            saveAction(action);
        }
    }

    @Override
    public void deleteAllActions() throws DbConnectionErrorException {
        String query = "DELETE from action";

        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Failed to delete all rows from action table.", e);
            throw new DbConnectionErrorException(e);
        }
    }

    @Override
    public Optional<ActionAbstract> getAction(String name) throws DbConnectionErrorException {
        String query = "SELECT * from action " +
                "WHERE name = ?";

        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                ActionAbstract action = createAction(rs);
                return Optional.of(action);
            }
        } catch (SQLException e) {
            throw new DbConnectionErrorException(e);
        }
        return Optional.empty();
    }

    @Override
    public void saveAction(@NotNull ActionAbstract action) throws DbConnectionErrorException {
        // ignore if already stored in the database
        // String query = "INSERT OR IGNORE INTO action " +
        //      "(name, class_name, description, parameters_count, is_producing_result) " +
        //      "VALUES (?, ?, ?, ? ,?) ";

        // upsert if exist update non-unique
        String query = "INSERT INTO action " +
              "(name, class_name, description, parameters_count, is_producing_result) " +
              "VALUES (?, ?, ?, ? ,?) " +
              "ON CONFLICT(name) DO UPDATE SET " +
                  "description = excluded.description, " +
                  "parameters_count = excluded.parameters_count, " +
                  "is_producing_result = excluded.is_producing_result ";

        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
            preparedStatement.setString(1, action.getName());
            preparedStatement.setString(2, action.getClass().getName());
            preparedStatement.setString(3, action.getDescription());
            preparedStatement.setInt(4, action.parametersCount());
            preparedStatement.setBoolean(5, action.isProducingResult());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated != 1) {
                logger.warn("Something went wrong during DB insert/update. Rows updated: {}, Expected 1 row inserted/updated",
                        rowsUpdated);
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            throw new DbConnectionErrorException(e);
        }
    }

    @Override
    public List<ScheduledAction> getAllScheduledActions() throws DbConnectionErrorException {
        String query = "SELECT * FROM scheduled_action as SA " +
                "INNER JOIN action as A " +
                "ON (SA.action_id = A.id)";

        return getScheduledActionsByQuery(query);
    }

    /**
     * Creates ScheduledAction object from ResultSet.
     * Expects all the columns from both action AND scheduled_action tables.
     * @param rs
     * @return
     * @throws DbConnectionErrorException
     */
    private ScheduledAction createScheduledAction(@NotNull ResultSet rs) throws DbConnectionErrorException {
        try {
            ActionAbstract action = createAction(rs);
            int id = rs.getInt("id");
            String goalTime = rs.getString("goal_time");
            String result = rs.getString("result");
            String status = rs.getString("status");

            LocalDateTime goalTimeParsed = convertStringIntoLocalDateTime(goalTime);

            ScheduledAction scheduledAction = new ScheduledAction(action, goalTimeParsed);
            scheduledAction.setId(id);
            scheduledAction.setResult(result);
            scheduledAction.setStatus(status);
            return scheduledAction;

        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            throw new DbConnectionErrorException(e);
        }
    }

    @Override
    public List<ScheduledAction> getAllEnabledScheduledActions() throws DbConnectionErrorException {
        // either 1 get all ids than call getScheduledActionByScheduledActionId()
        // or 2 whole own sql query

        String query = "SELECT * FROM scheduled_action AS sa " +
                "INNER JOIN action AS a ON(sa.action_id = a.id) " +
                "WHERE sa.status IN('SCHEDULED', 'RUNNING', 'FINISHED')";

        return getScheduledActionsByQuery(query);
    }

    @NotNull
    private List<ScheduledAction> getScheduledActionsByQuery(String query) throws DbConnectionErrorException {
        Connection conn = getConnection();

        List<ScheduledAction> scheduledActions = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                ScheduledAction scheduledAction = createScheduledAction(rs);
                scheduledActions.add(scheduledAction);
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            throw new DbConnectionErrorException(e);
        }

        return scheduledActions;
    }

    @Override
    public Optional<ScheduledAction> getScheduledActionByScheduledActionId(int scheduledActionId) throws DbConnectionErrorException {
        String query = "SELECT * FROM scheduled_action AS sa " +
                "INNER JOIN action AS a ON(sa.action_id = a.id) " +
                "WHERE sa.id = ?";

        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, scheduledActionId);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                ScheduledAction scheduledAction = createScheduledAction(rs);
                return Optional.of(scheduledAction);
            } else {
                logger.warn("No record has been found for scheduled_action id: {}", scheduledActionId);
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            throw new DbConnectionErrorException(e);
        }


    }

    @Override
    public void cancelScheduledAction(int scheduledActionId) throws DbConnectionErrorException {
        // CANCELLED has to be quoted otherwise sql-error, it thinks it is column name
        String query = "UPDATE scheduled_action " +
                "SET status = 'CANCELLED' " +
                "WHERE id = ?";

        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, scheduledActionId);
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                logger.warn("Expected 1 row UPDATED... Expected 1 scheduled action cancelled... Actual is: " + rows);
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new DbConnectionErrorException(e);
        }
    }

    @Override
    public void deleteScheduledAction(int scheduledActionId) throws DbConnectionErrorException {
        String query = "DELETE FROM scheduled_action " +
                "WHERE id = ?";

        Connection conn = getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, scheduledActionId);
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                logger.warn("Expected 1 row DELETED... Expected 1 scheduled action DELETED... Actual is: " + rows);
            }
        } catch (SQLException e) {
            logger.warn(e.getLocalizedMessage(),e);
            throw new DbConnectionErrorException(e);
        }
    }

    @Override
    public void saveAllScheduledActions(@NotNull List<ScheduledAction> scheduledActions) throws DbConnectionErrorException {
        for(ScheduledAction scheduledAction: scheduledActions) {
            saveScheduledAction(scheduledAction);
        }
    }

    @Override
    public void saveScheduledAction(@NotNull ScheduledAction scheduledAction) throws DbConnectionErrorException {

        String query = "INSERT INTO scheduled_action " +
                "(id, action_id, goal_time, result, status) " +
                "VALUES(?, ?, ?, ?, ?) " +
                "ON CONFLICT(id) DO UPDATE SET " +
                    "goal_time = excluded.goal_time," +
                    "result = excluded.result," +
                    "status = excluded.status ";

        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
            preparedStatement.setInt(1, scheduledAction.getId());

            int actionId = getActionIdByName(scheduledAction.getAction().getName());
            // should fail by sqlite foreign key violation action_id not exists...
/*            if (actionId < 0) {
                String errorMessage = "Trying to insert scheduled action without the actual Action existing yet.";
                logger.warn(errorMessage);
                throw new DbConnectionErrorException(errorMessage);
            }*/
            preparedStatement.setInt(2, actionId);
            preparedStatement.setString(3, scheduledAction.getGoalTime().toString());
            preparedStatement.setString(4, scheduledAction.getResult());
            preparedStatement.setString(5, scheduledAction.getStatus());

            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                logger.warn("Something went wrong during DB insert/update. Rows updated: {}, Expected 1 row inserted/updated",
                        rows);
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new DbConnectionErrorException(e);
        }
    }

    @Override
    public Optional<ScheduledAction> createScheduledAction(@NotNull String actionName,@NotNull Duration d) throws DbConnectionErrorException {
        int actionId = getActionIdByName(actionName);
        if (actionId > 0) {
            return createScheduledAction(actionId, d);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ScheduledAction> createScheduledAction(@NotNull String actionName,@NotNull Duration d, @Nullable List<String> parameters) throws DbConnectionErrorException {

        Optional<ScheduledAction> scheduledActionOptional = createScheduledAction(actionName, d);
        if (scheduledActionOptional.isPresent() && Objects.nonNull(parameters)) {
            ScheduledAction scheduledAction = scheduledActionOptional.get();
            scheduledAction.setParameters(parameters);
            this.saveScheduledAction(scheduledAction);
        }
        return scheduledActionOptional;
    }

    @Override
    public List<ScheduledAction> getAllExpiredScheduledActions() throws DbConnectionErrorException {

        String query = "SELECT * FROM scheduled_action AS sa " +
                "INNER JOIN action AS a ON(sa.action_id = a.id) " +
                "WHERE sa.status IN('SCHEDULED', 'RUNNING', 'FINISHED') " +
                "AND strftime('%s', sa.goal_time) <= strftime('%s', 'now', 'localtime') ";

        return getScheduledActionsByQuery(query);
    }

    private Optional<ScheduledAction> createScheduledAction(int actionId, @NotNull Duration d) throws DbConnectionErrorException {
        String query = "INSERT INTO scheduled_action (action_id, goal_time) " +
                "VALUES (?, ?)";

        Connection conn = getConnection();

        try (PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setInt(1, actionId);
            prepStatement.setString(2, LocalDateTime.now().plus(d).toString());

            // insert a new record
            int rows = prepStatement.executeUpdate();
            if (rows != 1) {
                logger.warn("Unexpected rows count updated. Expected 1, Actual: " +rows);
            } else {
                // recover an id of the new record
                ResultSet rs = prepStatement.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    Optional<ScheduledAction> scheduledAction = getScheduledActionByScheduledActionId(id);
                    return scheduledAction;
                } else {
                    logger.error("Record not found, indicating an SQL error. The record should be in the DB!!!");
                }
            }
        } catch (SQLException e) {
            logger.warn("Error working with DB.", e);
            throw new DbConnectionErrorException(e);
        }
        return Optional.empty();
    }

    private int getActionIdByName(String name) throws DbConnectionErrorException {

        String query = "SELECT id from action " +
                "WHERE name = ?";

        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            throw new DbConnectionErrorException(e);
        }
        return Integer.MIN_VALUE;
    }

    private LocalDateTime convertStringIntoLocalDateTime(String input) throws DbConnectionErrorException {
        try {
            return LocalDateTime.parse(input);
        } catch (DateTimeParseException e) {
            String errorMessage = "GoalTime stored in the Db is in the incorrect format: " + input;
            logger.error(errorMessage, e);
            throw new DbConnectionErrorException(errorMessage, e);
        }

    }

    private Connection getConnection() throws DbConnectionErrorException {
        if (connection == null) {
            String errorMessage = "Please call initDbConnection() first!";
            logger.error(errorMessage);
            throw new DbConnectionErrorException(errorMessage);
        } else {
            try {
                if(connection.isValid(1)) {
                    return connection;
                } else {
                    String errorMessage = "Current connection is invalid...";
                    logger.error(errorMessage);
                    throw new DbConnectionErrorException(errorMessage);
                }
            } catch (SQLException e) {
                String errorMessage = "Current connection is invalid...";
                logger.error(errorMessage, e);
                throw new DbConnectionErrorException(errorMessage, e);
            }
        }
    }
}
