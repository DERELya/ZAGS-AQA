package db;

import db.model.BirthCertificate;
import db.model.Citizen;
import db.repository.BirthCertificateRepository;
import db.repository.CitizenRepository;
import db.utils.TestDBDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
@Epic("ZAGS")
@Feature("DB — Свидетельство о рождении")
public class DBBirthCertificateTest {
    private static Integer createdCitizenId;
    private Integer createdBirthCertificateId;

    private static final CitizenRepository citizenRepository =
            new CitizenRepository();
    private static final BirthCertificateRepository birthCertificateRepository =
            new BirthCertificateRepository();


    @Test
    @DisplayName("Создание свидетельства о рождении")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет свидетельство о рождении")
    void createBirthCertificateAndVerifyDataInDatabase() throws SQLException {
        BirthCertificate expected = TestDBDataFactory.createBirthCertificate(createdCitizenId);

        createdBirthCertificateId = birthCertificateRepository.save(expected);

        BirthCertificate actualBirthCertificate = birthCertificateRepository.findById(createdBirthCertificateId);

        assertAll(
                () -> assertEquals(
                        createdBirthCertificateId,
                        actualBirthCertificate.getBirthCertificateId()
                ),
                () -> assertEquals(
                        createdCitizenId,
                        actualBirthCertificate.getCitizenId()
                ),
                () -> assertEquals(
                        expected.getPlaceOfBirth(),
                        actualBirthCertificate.getPlaceOfBirth()
                ),
                () -> assertEquals(
                        expected.getMother(),
                        actualBirthCertificate.getMother()
                ),
                () -> assertEquals(
                        expected.getFather(),
                        actualBirthCertificate.getFather()
                ),
                () -> assertEquals(
                        expected.getGrandma(),
                        actualBirthCertificate.getGrandma()
                ),
                () -> assertEquals(
                        expected.getGrandpa(),
                        actualBirthCertificate.getGrandpa()
                )
        );

    }

    @BeforeAll
    @Step("Создание гражданина в бд для теста")
    static void createCitizen() {
        Citizen citizen = TestDBDataFactory.createCitizen();
        createdCitizenId = citizenRepository.save(citizen);
    }

    @AfterEach
    @Step("Удаление свидетельства о рождении после теста")
    void cleanUp() throws SQLException {
        if (createdBirthCertificateId == null) {
            return;
        }
        birthCertificateRepository.deleteById(createdBirthCertificateId);
    }

    @AfterAll
    @Step("Удаление гражданина после теста")
    static void cleanUpCitizen() {
        if (createdCitizenId != null) {
            citizenRepository.deleteById(createdCitizenId);
        }
    }
}
