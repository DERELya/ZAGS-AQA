package db;

import db.model.Citizen;
import db.model.DeathCertificate;
import db.repository.CitizenRepository;
import db.repository.DeathCertificateRepository;
import db.utils.TestDBDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Свидетельство о смерти")
public class DBDeathCertificateTest {

    private Integer createdDeathCertificateId;
    private static Integer createdCitizenId;


    private final DeathCertificateRepository deathCertificateRepository =
            new DeathCertificateRepository();

    private static final CitizenRepository citizenRepository =
            new CitizenRepository();


    @Test
    @DisplayName("Создание свидетельства о смерти")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет свидетельство о смерти")
    void createDeathCertificateAndVerifyDataInDatabase() {
        DeathCertificate expected = TestDBDataFactory.createDeathCertificate(createdCitizenId);

        createdDeathCertificateId = deathCertificateRepository.save(expected);

        DeathCertificate actual = deathCertificateRepository.findById(createdDeathCertificateId);

        assertNotNull(actual, "Свидетельство о смерти должно существовать в БД после сохранения");
        assertAll(
                () -> assertEquals(expected.getCitizenId(), actual.getCitizenId()),
                () -> assertEquals(expected.getDeathcertificateid(), actual.getDeathcertificateid()),
                () -> assertEquals(expected.getDateOfDeath(), actual.getDateOfDeath()),
                () -> assertEquals(expected.getPlaceOfDeath(), actual.getPlaceOfDeath())
        );
    }


    @BeforeAll
    @Step("Создание гражданина в бд для теста")
    static void createCitizen() {
        Citizen citizen = TestDBDataFactory.createCitizen();
        createdCitizenId = citizenRepository.save(citizen);
    }

    @AfterAll
    @Step("Удаление гражданина после теста")
    static void cleanUpCitizen() {
        if (createdCitizenId != null) {
            citizenRepository.deleteById(createdCitizenId);
        }
    }

    @AfterEach
    @Step("Удаление свидетельства о смерти после теста")
    void cleanUp() {
        if (createdDeathCertificateId != null) {
            deathCertificateRepository.deleteById(createdDeathCertificateId);
        }
    }
}
