package db;

import api.dto.AppForBirthDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import api.steps.ZagsApiSteps;
import db.dao.CitizenDAO;
import db.hibernate.dao.BirthCertificateDAO;
import db.model.BirthCertificate;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

import static org.junit.jupiter.api.Assertions.*;

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
    void cleanUp() {
        try {
            if (createdBirthCertificateId != null) {
                assertDoesNotThrow(
                        () -> birthCertificateDAO.deleteById(createdBirthCertificateId), "Не удалось удалить свидетельство о рождении");
            }
        } finally {
            try {
                if (createdCitizenId != null) {
                    assertDoesNotThrow(
                            () -> citizenDAO.deleteById(createdCitizenId), "Не удалось удалить гражданина");
                }
            } finally {
                createdBirthCertificateId = null;
                createdCitizenId = null;
            }
        }
    }

    @Test
    @DisplayName("Создание свидетельства о рождении")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет свидетельство о рождении")
    void verifyBirthCertificateInDatabase() {

        BirthCertificate actualBirthCertificate = assertDoesNotThrow(
                () -> birthCertificateDAO.findById(createdBirthCertificateId),
                "Ошибка при получении свидетельства о рождении из БД"
        );

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
