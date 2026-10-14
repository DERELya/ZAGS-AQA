package db;

import db.model.Admin;
import db.repository.AdminRepository;
import db.utils.TestDBDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Администратор")
public class DBAdminTest {
    private Integer createdAdminId;


    private final AdminRepository adminRepository = new AdminRepository();

    @Test
    @DisplayName("Создание администратора")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет администратора")
    void createAdminAndVerifyDataInDatabase() {
        Admin expected = TestDBDataFactory.createAdmin();

        createdAdminId = adminRepository.save(expected);

        Admin actual = adminRepository.findById(createdAdminId);

        assertNotNull(actual, "Администратор должен существовать в БД");
        assertAll(
                () -> assertEquals(expected.getSurname(), actual.getSurname()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expected.getPassportNumber(), actual.getPassportNumber()),
                () -> assertEquals(expected.getDateofbirth(), actual.getDateofbirth()),
                () -> assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber())
        );
    }

    @AfterEach
    @Step("Удаление администратора после теста")
    void cleanUp() {
        if (createdAdminId != null) {
            adminRepository.deleteById(createdAdminId);
        }
    }
}
