package model.sql.jdbc_dao;

import actions.ActionAbstract;
import dynamic_classloader.ClassLoadingException;
import dynamic_classloader.LoaderByClassNames;
import model.ScheduledAction;
import model.sql.DbConnectionErrorException;
import model.sql.ISqlDbDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SqliteDao {

    private static final Logger logger = LoggerFactory.getLogger(SqliteDao.class);

    private String user;
    private char[] password;
    private Connection connection;
    public static String dbUrl;

    public SqliteDao() {

    }


    public void initDbConnection(String dbUrl, String user, char[] password) throws DbConnectionErrorException {

        if (connection != null) {
            this.closeConnection();
        }

        this.user = user;
        this.password = Arrays.copyOf(password, password.length);
        this.dbUrl = dbUrl;

        try {
            this.connection = DriverManager.getConnection(dbUrl, user, password.toString());
        } catch (SQLException e) {
            throw new DbConnectionErrorException(e);
        }
    }


    public void closeConnection() throws DbConnectionErrorException {
        Arrays.fill(this.password, '\0');
        this.password = null;

        if (this.connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbConnectionErrorException(e);
            }
        }
    }


    public Optional<ActionAbstract> getActionById(int id) {

        String query = "SELECT * from action " +
                "WHERE id = ?";

        return getActionByQueryAndParameter(query, String.valueOf(id));
    }


    public Optional<ActionAbstract> getActionByName(String name) {

        String query = "SELECT * from action " +
                "WHERE name = ?";

        return getActionByQueryAndParameter(query, name);

    }


    public Optional<ActionAbstract> getActionByClassName(String className) {

        String query = "SELECT * from action " +
                "WHERE class_name = ?";

        return getActionByQueryAndParameter(query, className);
    }

    private Optional<ActionAbstract> getActionByQueryAndParameter(String query, String parameter) {

        Connection conn = getConnection();
        try (PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, parameter);
            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()) {
                ActionAbstract action = createAction(rs);
                return Optional.of(action);
            }
        } catch (SQLException e) {
            logger.warn("Error working with DB.", parameter, e);
        }

        return Optional.empty();
    }


    public List<ActionAbstract> getAllActions() {
        return null;
    }




    public Optional<ScheduledAction> getScheduledActionById(int id) {

        String query = "SELECT * from scheduled_action " +
                "WHERE id = ?";

        Connection conn = getConnection();
        try (PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setInt(1, id);
            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()) {
                Optional<ScheduledAction> action = createScheduledAction(rs);
                return action;
            }
        } catch (SQLException e) {
            logger.warn("Error working with DB.", id, e);
        }

        return Optional.empty();
    }


    public List<ScheduledAction> getAllScheduledActions() {
        return null;
    }




    private Connection getConnection() {
        return connection;
    }

    private Optional<ScheduledAction> createScheduledAction(ResultSet rs) throws  DbConnectionErrorException {

        try {
            int id = rs.getInt("id");
            int actionId = rs.getInt("action_id");
            LocalDateTime dateTime = rs.getTimestamp("goalTime").toLocalDateTime();
            String result = rs.getString("result");
            String status = rs.getString("status");
            String createdTime = rs.getString("created_time");
            String lastModifiedTime = rs.getString("last_modified_time");

            Optional<ActionAbstract> action = getActionById(actionId);
            if (action.isPresent()) {
                ScheduledAction scheduledAction = new ScheduledAction(action.get(), dateTime);
                return Optional.of(scheduledAction);
            }
        } catch (SQLException e) {
            throw new DbConnectionErrorException(e);
        }

        return Optional.empty();

    }

    private ActionAbstract createAction(ResultSet rs) throws DbConnectionErrorException {

        String className = "";
        try {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            className = rs.getString("class_name");
            String desc = rs.getString("description");
            boolean acceptParameter = rs.getBoolean("accept_parameter");
            boolean isProducingResult = rs.getBoolean("is_producing_result");
            int parametersCount = rs.getInt("parameters_count");

            logger.info("Id: {}, Name: {}, Class: {}, Desc: {}, Accept: {}, Count: {}, Result: {}",
                    id, name, className, desc, acceptParameter, parametersCount, isProducingResult);

            ActionAbstract action = LoaderByClassNames.load(className);
            action.load(name, desc, parametersCount, isProducingResult);
            return action;

        } catch (SQLException e) {
            throw new DbConnectionErrorException(e);
        } catch (ClassLoadingException e) {
            throw new DbConnectionErrorException("Unrecognized class_name in database: " + className, e);
        }

    }




}
