package ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import pages.LoginPage;
import pages.user.ServiceWeddingPage;
import utils.selinoid.CrossBrowserTest;

@Epic("ZAGS")
@Feature("UI — Проверка создания заявки на регистрацию брака")
public class RegistrationWeddingTest extends BaseTest {

    @CrossBrowserTest
    @DisplayName("1. Позитивный сценарий: Успешное заполнение всех шагов и подача заявки на регистрацию брака")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Проверка создания заявки на регистрацию брака")
    @Description("Проверка, что правильно создается заявка на регистрацию брака")
    public void testSuccessfulWedding() {

        boolean isMessageDisplayed = new LoginPage().clickButtonUser().
                fillForm(applicant).
                clickNextButton().
                clickButtonWedding().
                fillForm(citizen).
                clickNextButton(ServiceWeddingPage::new).
                fillForm(wedding).
                clickCompleteButton().
                isSuccessMessageDisplayed("Спасибо за обращение!");

        Assertions.assertTrue(isMessageDisplayed, "Сообщение 'Спасибо за обращение!' не появилось на странице!");
    }
}
