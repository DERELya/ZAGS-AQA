import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;
import java.io.IOException;
import java.io.InputStream;
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
            applicantPage.fillLastName("Сергеев").
                    fillName("Олег").
                    fillMiddlename("Викторович").
                    fillPhone("+375672911256").
                    fillPassport("12345123").
                    fillAddress("г.Брест, ул.Московская 320").
                    clickNextButton();

            ChooseServicePage chooseServicePage = new ChooseServicePage(driver);
            chooseServicePage.clickButtonWedding();

            CitizenPage citizenPage = new CitizenPage(driver);
            citizenPage.fillLastName("Сергеев").
                    fillName("Олег").
                    fillMiddlename("Викторович").
                    fillDateOfBirth("12.09.2005").
                    fillPassport("12345123").
                    fillGender("Муж").
                    fillAddress("г.Брест, ул.Московская 320").
                    clickNextButton();

            ServicePage servicePage = new ServicePage(driver);
            servicePage.fillDateOfRegistration("26.09.2026")
                    .fillNewLastName("Петрова")
                    .fillLastNameSpouse("Сергеев")
                    .fillNameSpouse("Олег")
                    .fillMiddleNameSpouse("Викторович")
                    .fillDateOfBirthSpouse("12.06.2000")
                    .fillPassportSpouse("12784352617").
                    clickCompleteButton();

            Thread.sleep(15000);
        } catch (Exception e) {
            System.err.println("Error");
        } finally {
            driver.quit();
        }

    }
}
