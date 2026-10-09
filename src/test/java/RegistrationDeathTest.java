import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.user.ServiceDeathPage;

public class RegistrationDeathTest extends BaseTest {

    @Test
    @DisplayName("1. Позитивный сценарий: Успешное заполнение всех шагов и подача заявки на регистрацию смерти")
    public void testRegistrationDeath() {

        boolean isMessageDisplayed = new LoginPage().clickButtonUser().
                fillForm(applicant).
                clickNextButton().
                clickButtonDeath().
                fillForm(citizen).
                clickNextButton(ServiceDeathPage::new).
                fillForm(death).
                clickCompleteButton().
                isSuccessMessageDisplayed("Спасибо за обращение!");

        Assertions.assertTrue(isMessageDisplayed, "Сообщение 'Спасибо за обращение!' не появилось на странице!");
    }
}
