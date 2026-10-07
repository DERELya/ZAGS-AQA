package driver;

import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    private static final int TIMEOUT_SECONDS = 10;
    private static final Properties properties=new Properties();

    static {
        try (InputStream is = DriverManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private DriverManager() {}


    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driverThreadLocal.set(driver);
            waitThreadLocal.set(new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS)));
            String username = getCredential("username", "APP_USERNAME");
            String password = getCredential("password", "APP_PASSWORD");

            ((HasAuthentication) driver)
                    .register(UsernameAndPassword.of(username, password));
        }
        return driverThreadLocal.get();
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    private static String getCredential(String propertyName, String envName) {
        String value = System.getProperty(propertyName);

        if (value == null || value.isBlank()) {
            value = System.getenv(envName);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Credential is not configured: " + propertyName
            );
        }

        return value;
    }
    public static WebDriverWait getWait() {
        if (waitThreadLocal.get() == null) {
            getDriver();
        }
        return waitThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
            waitThreadLocal.remove();
        }
    }
}
