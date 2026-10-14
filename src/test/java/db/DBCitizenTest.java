package db;

import db.model.Citizen;
import db.repository.CitizenRepository;
import db.utils.TestDBDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Гражданин")
public class DBCitizenTest {

    private Integer createdCitizenId;

    private final CitizenRepository citizenRepository =
            new CitizenRepository();

    @Test
    @DisplayName("Создание гражданина")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет гражданина")
    void createCitizenAndVerifyDataInDatabase() {
        Citizen expected = TestDBDataFactory.createCitizen();

        createdCitizenId = citizenRepository.save(expected);

        Citizen actual = citizenRepository.findById(createdCitizenId);

        assertNotNull(actual, "Гражданин должен существовать в БД");
        assertAll(
                () -> assertEquals(expected.getSurname(), actual.getSurname()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expected.getPassportNumber(), actual.getPassportNumber()),
                () -> assertEquals(expected.getGender(), actual.getGender()),
                () -> assertEquals(expected.getRegistrationAddress(), actual.getRegistrationAddress())
        );
    }

    @AfterEach
    @Step("Удаление гражданина после теста")
    void cleanUp() {
        if (createdCitizenId != null) {
            citizenRepository.deleteById(createdCitizenId);
        }
    }
}
