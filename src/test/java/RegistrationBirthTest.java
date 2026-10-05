import model.ApplicantData;
import model.BirthData;
import model.CitizenData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.user.ServiceBirthPage;
import pages.user.StatusPage;

public class RegistrationBirthTest extends BaseTest{
    ApplicantData applicant = new ApplicantData(
            "Сергеев", "Олег", "Викторович",
            "+375672911256", "12345123", "г.Брест, ул.Московская 320"
    );
    CitizenData citizen = new CitizenData(
            "Сергеев", "Олег", "Викторович",
            "12.09.2005", "12345123", "Муж", "г.Брест, ул.Московская 320"
    );
    BirthData birth = new BirthData(
            "г.Брест","Ольга","Олег","Татьяна","Виктор"
    );

    @Test
    @DisplayName("1. Позитивный сценарий: Успешное заполнение всех шагов и подача заявки на регистрацию рождения")
    public void testRegistrationDeath(){

        new LoginPage().clickButtonUser().
                fillForm(applicant).
                clickNextButton().
                clickButtonBirth().
                fillForm(citizen).
                clickNextButton();
        new ServiceBirthPage().fillForm(birth).
                clickCompleteButton();

        boolean isMessageDisplayed = new StatusPage().isSuccessMessageDisplayed("Спасибо за обращение!");

        Assertions.assertTrue(isMessageDisplayed, "Сообщение 'Спасибо за обращение!' не появилось на странице!");
    }
}
