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
