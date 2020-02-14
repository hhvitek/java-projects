package model.sql.jdbc_dao;

import java.util.List;
import java.util.Optional;

public interface ISqlScheduledActionDao {

    Optional<ISqlScheduledAction> getScheduledActionById(int id);
    List<ISqlScheduledAction> getScheduledActionsByActionId(String name);
    List<ISqlScheduledAction> getAllScheduledActions();
    void saveScheduledAction(ISqlScheduledAction t);
    void updateScheduledAction(ISqlScheduledAction t);
    void deleteScheduledAction(ISqlScheduledAction t);

}
