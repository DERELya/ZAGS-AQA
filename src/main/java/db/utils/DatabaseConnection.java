package db.utils;

import utils.TestConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {


        return java.sql.DriverManager.getConnection(
                TestConfig.getDBUrl(),
                TestConfig.getDbUsername(),
                TestConfig.getDbPassword()
        );
    }
}
