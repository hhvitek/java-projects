package model.sql;

import java.sql.SQLException;

public class DbConnectionErrorException extends SQLException {

    public DbConnectionErrorException() {  }

    public DbConnectionErrorException(String message) {
        super(message);
    }

    public DbConnectionErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbConnectionErrorException(Throwable cause) {
        super(cause);
    }
}
