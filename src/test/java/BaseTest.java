import driver.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://regoffice.senla.eu/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.manage().deleteAllCookies(); // Очищаем куки
        }
        DriverManager.quitDriver();
    }
}
