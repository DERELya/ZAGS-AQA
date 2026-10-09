package ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.user.ServiceBirthPage;
import pages.user.StatusPage;

public class AdminTest extends BaseTest {

    private String appNumber;

    @BeforeEach
    void createTestApplication() {
        appNumber = createApplication();
        new StatusPage().clickCloseButton();
    }

    @Test
    @DisplayName("1. Проверка статуса заявки")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Проверка статуса заявления")
    @Description("Проверка, что после создания заявления оно получает статус 'На рассмотрении'")
    public void testStatusApplication() {
        String actualStatus = new LoginPage().clickButtonAdmin().fillForm(adminData).
                clickNextButton()
                .getStatusByApplicationNumber(appNumber);
        ;

        Assertions.assertTrue(
                actualStatus.contains("На рассмотрении"),
                "Ожидался статус 'На рассмотрении', но получен: " + actualStatus
        );
    }

    @Step("Создание заявления пользователем")
    private String createApplication() {
        return new LoginPage().clickButtonUser().
                fillForm(applicant).
                clickNextButton().
                clickButtonBirth().
                fillForm(citizen).
                clickNextButton(ServiceBirthPage::new).
                fillForm(birth).
                clickCompleteButton().
                getApplicationNumberMessage();
    }
}
