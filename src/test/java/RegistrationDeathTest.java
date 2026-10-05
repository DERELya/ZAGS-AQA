import model.ApplicantData;
import model.CitizenData;
import model.DeathData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.user.ServiceDeathPage;
import pages.user.StatusPage;

public class RegistrationDeathTest extends BaseTest{

    ApplicantData applicant = new ApplicantData(
            "Сергеев", "Олег", "Викторович",
            "+375672911256", "12345123", "г.Брест, ул.Московская 320"
    );
    CitizenData citizen = new CitizenData(
            "Сергеев", "Олег", "Викторович",
            "12.09.2005", "12345123", "Муж", "г.Брест, ул.Московская 320"
    );
    DeathData death = new DeathData(
            "02.10.2026","г.Брест"
    );

    @Test
    @DisplayName("1. Позитивный сценарий: Успешное заполнение всех шагов и подача заявки на регистрацию смерти")
    public void testRegistrationDeath(){

        new LoginPage().clickButtonUser().
                fillForm(applicant).
                clickNextButton().
                clickButtonDeath().
                fillForm(citizen).
                clickNextButton();
        new ServiceDeathPage().fillForm(death).
                clickCompleteButton();

        boolean isMessageDisplayed = new StatusPage().isSuccessMessageDisplayed("Спасибо за обращение!");

        Assertions.assertTrue(isMessageDisplayed, "Сообщение 'Спасибо за обращение!' не появилось на странице!");
    }
}
