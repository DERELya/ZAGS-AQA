package db;

import api.dto.AdminRequestDto;
import api.steps.ZagsApiSteps;
import db.dao.AdminDAO;
import db.model.Admin;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HelperForDate;
import utils.TestDataFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Администратор")
public class DBAdminTest {
    private static Integer createdAdminId;
    private static AdminRequestDto expected;

    private final AdminDAO adminDAO = new AdminDAO();

    @BeforeEach
    void createAdmin() {
        expected = TestDataFactory.adminRequest();
        createdAdminId = ZagsApiSteps.createAdministrator(expected);
    }

    @AfterEach
    @Step("Удаление администратора после теста")
    void cleanUp() {
        if (createdAdminId != null) {
            assertDoesNotThrow(() -> adminDAO.deleteById(createdAdminId), "Не удалось удалить администратора");
        }
    }

    @Test
    @DisplayName("Создание администратора")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет администратора")
    void verifyAdminInDatabase() {

        Admin actual = assertDoesNotThrow(() -> adminDAO.findById(createdAdminId), "Ошибка при получении администратора из БД");

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
}
