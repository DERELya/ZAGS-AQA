import driver.DriverManager;
import org.openqa.selenium.*;
import pages.*;
import pages.user.ApplicantPage;
import pages.user.ChooseServicePage;
import pages.user.CitizenPage;
import pages.user.ServiceWeddingPage;


public class Main {
    public static void main(String[] args) throws InterruptedException {



        WebDriver driver = DriverManager.getDriver();
        try {
            driver.get(DriverManager.getProperty("URL"));
            System.out.println("Успешно открыли страницу");

            LoginPage loginPage = new LoginPage();
            loginPage.clickButtonUser();

            ApplicantPage applicantPage = new ApplicantPage();
            applicantPage.fillLastName("Сергеев").
                    fillName("Олег").
                    fillMiddlename("Викторович").
                    fillPhone("+375672911256").
                    fillPassport("12345123").
                    fillAddress("г.Брест, ул.Московская 320").
                    clickNextButton();

            ChooseServicePage chooseServicePage = new ChooseServicePage();
            chooseServicePage.clickButtonWedding();

            CitizenPage citizenPage = new CitizenPage();
            citizenPage.fillLastName("Сергеев").
                    fillName("Олег").
                    fillMiddleName("Викторович").
                    fillDateOfBirth("12.09.2005").
                    fillPassport("12345123").
                    fillGender("Муж").
                    fillAddress("г.Брест, ул.Московская 320").
                    clickNextButton();

            ServiceWeddingPage serviceWeddingPage = new ServiceWeddingPage();
            serviceWeddingPage.fillDateOfRegistration("26.09.2026")
                    .fillNewLastName("Петрова")
                    .fillLastNameSpouse("Сергеев")
                    .fillNameSpouse("Олег")
                    .fillMiddleNameSpouse("Викторович")
                    .fillDateOfBirthSpouse("12.06.2000")
                    .fillPassportSpouse("12784352617").
                    clickCompleteButton();

        } catch (Exception e) {
            System.err.println("Error");
        } finally {
            driver.quit();
        }

    }
}
