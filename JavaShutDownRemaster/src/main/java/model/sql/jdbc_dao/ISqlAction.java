package model.sql.jdbc_dao;

import java.util.List;

public interface ISqlAction {

    int getId();
    void setId(int id);

    String getName();
    void setName(String name);

    String getClassName();
    void setClassName(String className);

    String getDescription();
    void setDescription(String description);

    int getParametersCount();
    void setParametersCount(int parametersCount );

    boolean isProducingResult();
    void setIsProducingResult(boolean isProducingResult);

    String toString();
}
