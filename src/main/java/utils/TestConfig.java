package utils;

public class TestConfig {
    private TestConfig() {
    }

    public static String getUsername() {
        return getRequiredValue("username", "APP_USERNAME");
    }

    public static String getPassword() {
        return getRequiredValue("password", "APP_PASSWORD");
    }

    public static String getBaseUrl() {
        return getRequiredValue("base.url", "BASE_URL");
    }

    public static String getDbHost() {
        return getRequiredValue("db.host", "DB_HOST");
    }

    public static String getDbPort() {
        return getRequiredValue("db.port", "DB_PORT");
    }

    public static String getDbName() {return getRequiredValue("db.name", "DB_NAME");}

    public static String getDbUsername() {
        return getRequiredValue("db.username", "DB_USERNAME");
    }

    public static String getDbPassword() {
        return getRequiredValue("db.password", "DB_PASSWORD");
    }

    public static String getDbSchema() {return getRequiredValue("db.schema", "DB_SCHEMA");}

    private static String getRequiredValue(String propertyName, String envName) {
        String value = System.getProperty(propertyName);
        if (value == null || value.isBlank()) {
            value = System.getenv(envName);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing configuration: set -D" + propertyName + " or environment variable " + envName);
        }
        return value;
    }
}
