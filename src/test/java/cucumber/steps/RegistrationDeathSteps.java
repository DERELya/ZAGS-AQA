package cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.data.ApplicantData;
import model.data.CitizenData;
import model.data.DeathData;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import pages.user.*;
import utils.DriverManager;
import utils.TestConfig;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrationDeathSteps {

    ApplicantData applicant = new ApplicantData(
            "Сергеев", "Олег", "Викторович",
            "+375672911256", "12345123", "г.Брест, ул.Московская 320"
    );
    CitizenData citizen = new CitizenData(
            "Сергеев", "Олег", "Викторович",
            "12.09.2005", "12345123", "Муж", "г.Брест, ул.Московская 320"
    );
    DeathData death = new DeathData(
            LocalDate.now().minusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), "г.Брест"
    );

    @Given("заявитель авторизовался,находится на главной странице")
    public void prepareRegistrationData() {
        DriverManager.getDriver().get(TestConfig.getBaseUrl());
    }

    @When("заявитель нажимает кнопку Войти как пользователь")
    public void clickOnButtonUser() {
        new LoginPage().clickButtonUser();
    }

    @When("заполняет форму данные заявителя и нажимает кнопку далее")
    public void fillApplicantFormAndClickNext() {
        new ApplicantPage().fillForm(applicant).clickNextButton();
    }

    @When("выбирает услугу \"Регистрация смерти\" нажатием кнопки")
    public void clickDeathButton() {
        new ChooseServicePage().clickButtonDeath();
    }

    @When("заполняет форму данные гражданина и нажимает кнопку далее")
    public void fillCitizenFormAndClickNext() {
        new CitizenPage().fillForm(citizen).clickNextButton();
    }


    @When("заполняет форму данные услуги и отправляет заявление нажатием кнопки завершить")
    public void fillDeathFormAndClickComplete() {
        new ServiceDeathPage().fillForm(death).clickCompleteButton();
    }

    @Then("отображается сообщение \"Спасибо за обращение!\"")
    public void verifyRegistrationSuccess() {
        boolean isMessageDisplayed = new StatusPage().isSuccessMessageDisplayed("Спасибо за обращение!");

        Assertions.assertTrue(
                isMessageDisplayed,
                "Сообщение 'Спасибо за обращение!' не появилось на странице!"
        );
    }

}
