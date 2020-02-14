package model.sql.jdbc_dao;

public interface ISqlScheduledAction {

    int getId();
    void setId(int id);

    int getActionId();
    void setActionId(int id);

    String getGoalTime();
    void setGoalTime(String goalTime);

    String getResult();
    void setResult(String result);

    String getStatus();
    void setStatus(String status);

    String getCreatedTime();
    void setCreatedTime(String createdTime);

    String getLastModifiedTime();
    void setLastModifiedTime(String lastModifiedTime);

    String toString();
}
