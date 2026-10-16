package db;

import db.model.Applicant;
import db.repository.ApplicantRepository;
import db.utils.TestDBDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Заявитель")
public class DBApplicantTest {
    private Integer createdApplicantId;


    private final ApplicantRepository applicantRepository =
            new ApplicantRepository();


    @Test
    @DisplayName("Создание заявителя")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет заявителя")
    void createApplicantAndVerifyDataInDatabase() {
        Applicant expected = TestDBDataFactory.createApplicant();

        createdApplicantId = applicantRepository.save(expected);

        Applicant actual = applicantRepository.findById(createdApplicantId);

        assertNotNull(actual, "Заявитель должен существовать в БД");

        assertAll(
                () -> assertEquals(expected.getSurname(), actual.getSurname()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expected.getPassportNumber(), actual.getPassportNumber()),
                () -> assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber()),
                () -> assertEquals(expected.getRegistrationAddress(), actual.getRegistrationAddress())
        );
    }


    @AfterEach
    @Step("Удаление заявителя после теста")
    void cleanUp() {
        if (createdApplicantId != null) {
            applicantRepository.deleteById(createdApplicantId);
        }
    }

}
