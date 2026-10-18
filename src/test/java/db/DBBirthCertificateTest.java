package db;

import api.dto.AppForBirthDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import api.steps.ZagsApiSteps;
import db.hibernate.BirthCertificateDAO;
import db.model.BirthCertificate;
import db.repository.CitizenDAO;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("ZAGS")
@Feature("DB — Свидетельство о рождении")
public class DBBirthCertificateTest {
    private Integer createdBirthCertificateId;
    private Integer createdCitizenId;

    private BirthCertificate expectedBirthCertificate;

    private final BirthCertificateDAO birthCertificateDAO =
            new BirthCertificateDAO();

    private final CitizenDAO citizenDAO = new CitizenDAO();


    @BeforeEach
    @Step("Создание заявки на регистрацию смерти через API")
    void createBirthApplication() {
        ApplicationRequestDto requestDto =
                TestDataFactory.birthApplication();

        ApplicationResponseDto<AppForBirthDto> response =
                ZagsApiSteps.sendApplication(requestDto, new TypeRef<ApplicationResponseDto<AppForBirthDto>>() {
                });

        createdBirthCertificateId = response.getData().getBirthCertificateId();

        createdCitizenId = response.getData().getCitizenId();

        expectedBirthCertificate = new BirthCertificate();

        expectedBirthCertificate.setBirthCertificateId(createdBirthCertificateId);
        expectedBirthCertificate.setCitizenId(createdCitizenId);
        expectedBirthCertificate.setPlaceOfBirth(requestDto.getBirthPlace());
        expectedBirthCertificate.setMother(requestDto.getBirthMother());
        expectedBirthCertificate.setFather(requestDto.getBirthFather());
        expectedBirthCertificate.setGrandma(requestDto.getBirthGrandma());
        expectedBirthCertificate.setGrandpa(requestDto.getBirthGrandpa());
    }

    @AfterEach
    @Step("Удаление тестовых данных")
    void cleanUp() throws SQLException {
        try {
            if (createdBirthCertificateId != 0) {
                birthCertificateDAO.deleteById(
                        createdBirthCertificateId
                );
            }
        } finally {
            try {
                if (createdCitizenId != 0) {
                    citizenDAO.deleteById(createdCitizenId);
                }
            } finally {
                createdBirthCertificateId = 0;
                createdCitizenId = 0;
            }
        }
    }

    @Test
    @DisplayName("Создание свидетельства о рождении")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет свидетельство о рождении")
    void VerifyDataInDatabaseBirthCertificate() throws SQLException {

        BirthCertificate actualBirthCertificate = birthCertificateDAO.findById(createdBirthCertificateId);

        assertAll(
                () -> assertEquals(
                        expectedBirthCertificate.getBirthCertificateId(),
                        actualBirthCertificate.getBirthCertificateId()
                ),
                () -> assertEquals(
                        expectedBirthCertificate.getCitizenId(),
                        actualBirthCertificate.getCitizenId()
                ),
                () -> assertEquals(
                        expectedBirthCertificate.getPlaceOfBirth(),
                        actualBirthCertificate.getPlaceOfBirth()
                ),
                () -> assertEquals(
                        expectedBirthCertificate.getMother(),
                        actualBirthCertificate.getMother()
                ),
                () -> assertEquals(
                        expectedBirthCertificate.getFather(),
                        actualBirthCertificate.getFather()
                ),
                () -> assertEquals(
                        expectedBirthCertificate.getGrandma(),
                        actualBirthCertificate.getGrandma()
                ),
                () -> assertEquals(
                        expectedBirthCertificate.getGrandpa(),
                        actualBirthCertificate.getGrandpa()
                )
        );

    }
}
