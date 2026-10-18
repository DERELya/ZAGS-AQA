package db;

import api.dto.AppForDeathDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import api.steps.ZagsApiSteps;
import db.hibernate.DeathCertificateDAO;
import db.model.DeathCertificate;
import db.repository.CitizenDAO;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HelperForDate;
import utils.TestDataFactory;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Свидетельство о смерти")
public class DBDeathCertificateTest {

    private Integer createdDeathCertificateId;
    private Integer createdCitizenId;

    private DeathCertificate expectedDeathCertificate;

    private final DeathCertificateDAO deathCertificateDAO =
            new DeathCertificateDAO();

    private final CitizenDAO citizenDAO = new CitizenDAO();

    @BeforeEach
    @Step("Создание заявки на регистрацию смерти через API")
    void createDeathApplication() {
        ApplicationRequestDto requestDto =
                TestDataFactory.deathApplication();

        ApplicationResponseDto<AppForDeathDto> response =
                ZagsApiSteps.sendApplication(requestDto,new TypeRef<ApplicationResponseDto<AppForDeathDto>>() {});

        createdDeathCertificateId = response.getData().getDeathCertificateId();

        createdCitizenId = response.getData().getCitizenId();

        expectedDeathCertificate = new DeathCertificate();

        expectedDeathCertificate.setDeathCertificateId(createdDeathCertificateId);
        expectedDeathCertificate.setCitizenId(createdCitizenId);
        expectedDeathCertificate.setPlaceOfDeath(requestDto.getDeathPlaceOfDeath());
        expectedDeathCertificate.setDateOfDeath(requestDto.getDeathDateOfDeath());
    }

    @AfterEach
    @Step("Удаление тестовых данных")
    void cleanUp() throws SQLException {
        try {
            if (createdDeathCertificateId != 0) {
                deathCertificateDAO.deleteById(
                        createdDeathCertificateId
                );
            }
        } finally {
            try {
                if (createdCitizenId != 0) {
                    citizenDAO.deleteById(createdCitizenId);
                }
            } finally {
                createdDeathCertificateId = 0;
                createdCitizenId = 0;
            }
        }
    }

    @Test
    @DisplayName("Создание свидетельства о смерти")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет свидетельство о смерти")
    void VerifyDataInDatabaseDeathCertificate() {

        DeathCertificate actual = deathCertificateDAO.findById(createdDeathCertificateId);

        assertNotNull(actual, "Свидетельство о смерти должно существовать в БД после сохранения");
        assertAll(
                () -> assertEquals(expectedDeathCertificate.getDeathCertificateId(), actual.getDeathCertificateId()),
                () -> assertEquals(expectedDeathCertificate.getCitizenId(), actual.getCitizenId()),
                () -> assertEquals(expectedDeathCertificate.getDateOfDeath(), HelperForDate.toIsoDateFromTimestamp(actual.getDateOfDeath())),
                () -> assertEquals(expectedDeathCertificate.getPlaceOfDeath(), actual.getPlaceOfDeath())
        );
    }


}
