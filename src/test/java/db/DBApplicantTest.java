package db;

import api.dto.AppForDeathDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import api.steps.ZagsApiSteps;
import db.dao.ApplicantDAO;
import db.hibernate.dao.DeathCertificateDAO;
import db.model.Applicant;
import db.utils.CleanDataTestDB;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Заявитель")
public class DBApplicantTest {
    private Integer createdApplicantId;
    private Applicant expectedApplicant;
    private Integer createdCitizenId;
    private Integer createdDeathCertificateId;

    private final ApplicantDAO applicantDAO = new ApplicantDAO();
    private final DeathCertificateDAO deathCertificateDAO = new DeathCertificateDAO();

    @BeforeEach
    void createApplicant() {
        ApplicationRequestDto requestDto =
                TestDataFactory.deathApplication();


        expectedApplicant = new Applicant();
        expectedApplicant.setName(requestDto.getPersonalFirstName());
        expectedApplicant.setSurname(requestDto.getPersonalLastName());
        expectedApplicant.setMiddleName(requestDto.getPersonalMiddleName());
        expectedApplicant.setRegistrationAddress(
                requestDto.getPersonalAddress()
        );
        expectedApplicant.setPhoneNumber(
                requestDto.getPersonalPhoneNumber()
        );
        expectedApplicant.setPassportNumber(
                requestDto.getPersonalNumberOfPassport()
        );

        ApplicationResponseDto<AppForDeathDto> response =
                ZagsApiSteps.sendApplication(requestDto, new TypeRef<ApplicationResponseDto<AppForDeathDto>>() {
                });

        createdApplicantId = response.getData().getApplicantId();
        createdCitizenId = response.getData().getCitizenId();
        createdDeathCertificateId = response.getData().getDeathCertificateId();
    }

    @AfterEach
    @Step("Удаление данных после теста")
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
    @DisplayName("Создание заявителя")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет заявителя")
    void verifyApplicantInDatabase() {

        Applicant actual = assertDoesNotThrow(() -> applicantDAO.findById(createdApplicantId), "Ошибка при получении заявителя из БД");

        assertNotNull(actual, "Заявитель должен существовать в БД");
        assertAll(
                () -> assertEquals(expectedApplicant.getSurname(), actual.getSurname()),
                () -> assertEquals(expectedApplicant.getName(), actual.getName()),
                () -> assertEquals(expectedApplicant.getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expectedApplicant.getPassportNumber(), actual.getPassportNumber()),
                () -> assertEquals(expectedApplicant.getPhoneNumber(), actual.getPhoneNumber()),
                () -> assertEquals(expectedApplicant.getRegistrationAddress(), actual.getRegistrationAddress())
        );
    }
}
