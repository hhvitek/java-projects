package model;

import model.sql.ISqlDbDao;

public interface IModel extends ISqlDbDao {

    void setSqlDb(ISqlDbDao db);
    ISqlDbDao getSqlDb();

}
