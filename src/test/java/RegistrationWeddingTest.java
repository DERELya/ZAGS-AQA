import model.ApplicantData;
import model.CitizenData;
import model.WeddingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.user.ServiceWeddingPage;
import pages.user.StatusPage;

public class RegistrationWeddingTest extends BaseTest{
    ApplicantData applicant = new ApplicantData(
            "Сергеев", "Олег", "Викторович",
            "+375672911256", "12345123", "г.Брест, ул.Московская 320"
    );
    CitizenData citizen = new CitizenData(
            "Сергеев", "Олег", "Викторович",
            "12.09.2005", "12345123", "Муж", "г.Брест, ул.Московская 320"
    );
    WeddingData wedding = new WeddingData(
            "02.10.2026","Петров","Сергеевна","Ольга","Викторовна","02.10.2003","Ab127987812"
    );

    @Test
    @DisplayName("1. Позитивный сценарий: Успешное заполнение всех шагов и подача заявки на регистрацию брака")
    public void testSuccessfulWedding(){

        new LoginPage().clickButtonUser().
                fillForm(applicant).
                clickNextButton().
                clickButtonWedding().
                fillForm(citizen).
                clickNextButton();
        new ServiceWeddingPage().fillForm(wedding).
                clickCompleteButton();

        boolean isMessageDisplayed = new StatusPage().isSuccessMessageDisplayed("Спасибо за обращение!");

        Assertions.assertTrue(isMessageDisplayed, "Сообщение 'Спасибо за обращение!' не появилось на странице!");
    }
}
