import lombok.Value;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WebDriver driver = new ChromeDriver();
        try {
            ((HasAuthentication) driver).register(UsernameAndPassword.of(properties.getProperty("username"), properties.getProperty("password")));
            driver.get(properties.getProperty("URL"));
            System.out.println("Успешно открыли страницу");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.clickButtonUser();

            ApplicantPage applicantPage = new ApplicantPage(driver);
            applicantPage.fillAllTest();
            applicantPage.clickNextButton();

            ChooseServicePage chooseServicePage = new ChooseServicePage(driver);
            chooseServicePage.clickButtonWedding();

            CitizenPage citizenPage = new CitizenPage(driver);
            citizenPage.fillAllTest();
            citizenPage.clickNextButton();

            ServicePage servicePage = new ServicePage(driver);
            servicePage.fillAllTest();
            servicePage.clickCompleteButton();
            Thread.sleep(15000);
        } catch (Exception e) {
            System.err.println("Error");
        } finally {
            driver.quit();
        }

    }
}
