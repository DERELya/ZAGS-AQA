package utils;

import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    private static final int TIMEOUT_SECONDS = 10;

    private DriverManager() {
    }


    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driverThreadLocal.set(driver);
            waitThreadLocal.set(new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS)));
            String username = TestConfig.getUsername();
            String password = TestConfig.getPassword();
            String host = URI.create(TestConfig.getBaseUrl()).getHost();
            ((HasAuthentication) driver).register(
                    uri -> host.equalsIgnoreCase(uri.getHost()),
                    UsernameAndPassword.of(username, password));
        }
        return driverThreadLocal.get();
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

    public static WebDriver getDriverIfExists() {
        return driverThreadLocal.get();
    }
}
