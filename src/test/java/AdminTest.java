import model.AdminData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.admin.AdminstrationApplicantions;
import pages.admin.LoginAdminPage;
import pages.LoginPage;

public class AdminTest extends BaseTest{
    String appNumber = "69956";

    AdminData adminData = new AdminData(
            "Сергеев", "Олег", "Викторович",
            "+375672911256", "12345123", "12.04.2000"
    );

    @Test
    @DisplayName("1. Проверка статуса заявки")
    public void testStatusApplication() {


        new LoginPage().clickButtonAdmin();
        new LoginAdminPage().fillForm(adminData).clickNextButton();

        String actualStatus = new  AdminstrationApplicantions().getStatusByApplicationNumber(appNumber);

        Assertions.assertTrue(
                actualStatus.contains("На рассмотрении"),
                "Ожидался статус 'На рассмотрении', но получен: " + actualStatus
        );
    }
}
