import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.user.ServiceBirthPage;

public class RegistrationBirthTest extends BaseTest {

    @Test
    @DisplayName("1. Позитивный сценарий: Успешное заполнение всех шагов и подача заявки на регистрацию рождения")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Проверка создания заявки на регистрацию рождения")
    @Description("Проверка, что правильно создается заявка на регистрацию рождения")
    public void testRegistrationDeath() {

        boolean isMessageDisplayed = new LoginPage().clickButtonUser().
                fillForm(applicant).
                clickNextButton().
                clickButtonBirth().
                fillForm(citizen).
                clickNextButton(ServiceBirthPage::new).
                fillForm(birth).
                clickCompleteButton().
                isSuccessMessageDisplayed("Спасибо за обращение!");

        Assertions.assertTrue(isMessageDisplayed, "Сообщение 'Спасибо за обращение!' не появилось на странице!");
    }
}
