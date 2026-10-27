package db;

import api.dto.AppForDeathDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import api.steps.ZagsApiSteps;
import db.hibernate.dao.DeathCertificateDAO;
import db.model.DeathCertificate;
import db.utils.CleanDataTestDB;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HelperForDate;
import utils.TestDataFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Свидетельство о смерти")
public class DBDeathCertificateTest {

    private Integer createdDeathCertificateId;
    private Integer createdCitizenId;
    private Integer createdApplicantId;
    private DeathCertificate expectedDeathCertificate;

    private final DeathCertificateDAO deathCertificateDAO = new DeathCertificateDAO();

    @BeforeEach
    @Step("Создание заявки на регистрацию смерти через API")
    void createDeathApplication() {
        ApplicationRequestDto requestDto =
                TestDataFactory.deathApplication();

        ApplicationResponseDto<AppForDeathDto> response =
                ZagsApiSteps.sendApplication(requestDto, new TypeRef<ApplicationResponseDto<AppForDeathDto>>() {
                });

        createdDeathCertificateId = response.getData().getDeathCertificateId();
        createdCitizenId = response.getData().getCitizenId();
        createdApplicantId = response.getData().getApplicantId();

        expectedDeathCertificate = new DeathCertificate();

        expectedDeathCertificate.setDeathCertificateId(createdDeathCertificateId);
        expectedDeathCertificate.setCitizenId(createdCitizenId);
        expectedDeathCertificate.setPlaceOfDeath(requestDto.getDeathPlaceOfDeath());
        expectedDeathCertificate.setDateOfDeath(requestDto.getDeathDateOfDeath());
    }

    @AfterEach
    @Step("Удаление тестовых данных")
    void cleanUp() {
        try {
            CleanDataTestDB.cleanUp(
                    createdApplicantId,
                    createdCitizenId,
                    "свидетельство о смерти",
                    () -> {
                        if (createdDeathCertificateId != null) {
                            deathCertificateDAO.deleteById(
                                    createdDeathCertificateId
                            );
                        }
                    }
            );
        } finally {
            createdDeathCertificateId = null;
            createdApplicantId = null;
            createdCitizenId = null;
        }
    }

    @Test
    @DisplayName("Создание свидетельства о смерти")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет свидетельство о смерти")
    void verifyDeathCertificateInDatabase() {

        DeathCertificate actual = assertDoesNotThrow(() -> deathCertificateDAO.findById(createdDeathCertificateId),
                "Ошибка при получении свидетельства о смерти из БД");

        assertNotNull(actual, "Свидетельство о смерти должно существовать в БД после сохранения");
        assertAll(
                () -> assertEquals(expectedDeathCertificate.getCitizenId(), actual.getCitizenId()),
                () -> assertEquals(expectedDeathCertificate.getDateOfDeath(), HelperForDate.toIsoDateFromTimestamp(actual.getDateOfDeath())),
                () -> assertEquals(expectedDeathCertificate.getPlaceOfDeath(), actual.getPlaceOfDeath())
        );
    }


}
