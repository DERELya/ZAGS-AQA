package db;

import api.dto.AdminRequestDto;
import api.steps.ZagsApiSteps;
import db.model.Admin;
import db.repository.AdminDAO;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import utils.HelperForDate;
import utils.TestDataFactory;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Администратор")
public class DBAdminTest {
    private static Integer createdAdminId;
    private static AdminRequestDto expected;

    private final AdminDAO adminDAO = new AdminDAO();

    @Test
    @DisplayName("Создание администратора")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет администратора")
    void createAdminAndVerifyDataInDatabase() throws SQLException {

        Admin actual = adminDAO.findById(createdAdminId);

        assertNotNull(actual, "Администратор должен существовать в БД");
        assertAll(
                () -> assertEquals(expected.getPersonalLastName(), actual.getSurname()),
                () -> assertEquals(expected.getPersonalFirstName(), actual.getName()),
                () -> assertEquals(expected.getPersonalMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expected.getPersonalNumberOfPassport(), actual.getPassportNumber()),
                () -> assertEquals(expected.getDateOfBirth(), HelperForDate.toIsoDate(actual.getDateOfBirth())),
                () -> assertEquals(expected.getPersonalPhoneNumber(), actual.getPhoneNumber())
        );
    }

    @BeforeAll
    static void createAdmin() throws SQLException {
        expected = TestDataFactory.adminRequest();
        createdAdminId = ZagsApiSteps.createAdministrator(expected);
    }

    @AfterEach
    @Step("Удаление администратора после теста")
    void cleanUp() throws SQLException {
        if (createdAdminId!=0) {
            adminDAO.deleteById(createdAdminId);
        }
    }
}
