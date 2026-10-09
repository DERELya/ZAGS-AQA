import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.user.ServiceWeddingPage;

public class RegistrationWeddingTest extends BaseTest {

    @Test
    @DisplayName("1. Позитивный сценарий: Успешное заполнение всех шагов и подача заявки на регистрацию брака")
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
