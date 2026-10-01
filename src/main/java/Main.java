import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ApplicantPage;
import pages.CitizenPage;
import pages.ServicePage;

import java.time.Duration;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            ((HasAuthentication) driver).register(UsernameAndPassword.of("user", "senlatest"));
            driver.get("https://regoffice.senla.eu/");
            System.out.println("Успешно открыли страницу");

            By buttonUserLocator = By.xpath("//button[text()=\"Войти как пользователь\"]");
            WebElement buttonUser = wait.until(ExpectedConditions.elementToBeClickable(buttonUserLocator));

            buttonUser.click();

            ApplicantPage applicantPage = new ApplicantPage(driver);
            applicantPage.fillAllTest();
            applicantPage.clickNextButton();

            By buttonRegistrationWeddingLocator = By.xpath("//button[text()=\"Регистрация брака\"]");
            WebElement buttonRegistrationWedding = wait.until(ExpectedConditions.elementToBeClickable(buttonRegistrationWeddingLocator));
            buttonRegistrationWedding.click();

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
