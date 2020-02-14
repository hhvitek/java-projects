package model.sql.jdbc_dao;

import actions.ActionAbstract;

import java.util.List;
import java.util.Optional;

public interface ISqlActionDao {

    Optional<ISqlAction> getActionById(int id);
    Optional<ISqlAction> getActionByName(String name);
    Optional<ISqlAction> getActionByClassName(String name);
    List<ISqlAction> getAllActions();
    void saveAction(ISqlAction t);
    void updateAction(ISqlAction t);
    void deleteAction(ISqlAction t);

    List<ISqlScheduledAction> getAllScheduledActions();
    void setScheduledAction(ISqlScheduledAction t);
}
