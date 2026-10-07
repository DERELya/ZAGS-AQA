import driver.DriverManager;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    ApplicantData applicant = new ApplicantData(
            "Сергеев", "Олег", "Викторович",
            "+375672911256", "12345123", "г.Брест, ул.Московская 320"
    );
    CitizenData citizen = new CitizenData(
            "Сергеев", "Олег", "Викторович",
            "12.09.2005", "12345123", "Муж", "г.Брест, ул.Московская 320"
    );
    BirthData birth = new BirthData(
            "г.Брест", "Ольга", "Олег", "Татьяна", "Виктор"
    );
    DeathData death = new DeathData(
            LocalDate.now().minusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), "г.Брест"
    );
    WeddingData wedding = new WeddingData(
            LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), "Петров", "Сергеевна", "Ольга", "Викторовна", "02.10.2003", "Ab127987812"
    );
    AdminData adminData = new AdminData(
            "Сергеев", "Олег", "Викторович",
            "+375672911256", "12345123", "12.04.2000"
    );

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
