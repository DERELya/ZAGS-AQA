package db;

import api.dto.AdminRequestDto;
import api.steps.ZagsApiSteps;
import db.model.Admin;
import db.repository.AdminRepository;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import utils.HelperForDate;
import utils.TestDataFactory;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Администратор")
public class DBAdminTest {
    private static long createdAdminId;
    private static AdminRequestDto expected;

    private final AdminRepository adminRepository = new AdminRepository();

    @Test
    @DisplayName("Создание администратора")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет администратора")
    void createAdminAndVerifyDataInDatabase() throws SQLException {

        Admin actual = adminRepository.findById(createdAdminId);

        assertNotNull(actual, "Администратор должен существовать в БД");
        assertAll(
                () -> assertEquals(expected.getPersonalLastName(), actual.getSurname()),
                () -> assertEquals(expected.getPersonalFirstName(), actual.getName()),
                () -> assertEquals(expected.getPersonalMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expected.getPersonalNumberOfPassport(), actual.getPassportNumber()),
                () -> assertEquals(expected.getDateOfBirth(), HelperForDate.toIsoDate(actual.getDateofbirth())),
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
            adminRepository.deleteById(createdAdminId);
        }
    }
}
