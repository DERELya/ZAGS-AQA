package db.utils;

import utils.TestConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {

        String url = String.format(
                "jdbc:postgresql://%s:%s/%s?currentSchema=%s",
                TestConfig.getDbHost(),
                TestConfig.getDbPort(),
                TestConfig.getDbName(),
        TestConfig.getDbSchema());
        return java.sql.DriverManager.getConnection(
                url,
                TestConfig.getDbUsername(),
                TestConfig.getDbPassword()
        );
    }
}
