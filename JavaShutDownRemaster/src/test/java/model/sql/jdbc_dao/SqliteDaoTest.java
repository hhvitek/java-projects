package model.sql.jdbc_dao;

import actions.ActionAbstract;
import model.sql.ISqlDbDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SqliteDaoTest {
/*
    static final ISqlDbDao db = null;

    @BeforeAll
    static void initDbConnection() {
        String sqlitePathToTestDb = "src/test/resources/model/sql/jdbc_dao/sqlite_test.sqlite3";
        String user = "";
        char[] password = "".toCharArray();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File file = new File(sqlitePathToTestDb);

        String dbUrl = "jdbc:sqlite:" + file.getAbsolutePath();

        Assertions.assertDoesNotThrow(
                () -> db.initDbConnection(dbUrl, user, password),
                "DB initialization failed!!!"
        );
    }

    @AfterAll
    static void closeConnection() {
        Assertions.assertDoesNotThrow(
                () -> db.closeConnection(),
                "DB closing failed!!!"
        );
    }

    */
}
