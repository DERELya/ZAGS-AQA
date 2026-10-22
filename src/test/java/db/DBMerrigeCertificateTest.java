package db;

import api.dto.AppForMerrigeDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import api.steps.ZagsApiSteps;
import db.dao.ApplicantDAO;
import db.dao.ApplicationDAO;
import db.dao.CitizenDAO;
import db.hibernate.dao.MerrigeCertificateDAO;
import db.model.MerrigeCertificate;
import db.utils.CleanDataTestDB;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Свидетельство о браке")
public class DBMerrigeCertificateTest {

    private Integer createdMerrigeCertificateId;
    private Integer createdCitizenId;
    private Integer createdApplicantId;
    private MerrigeCertificate expectedMerrigeCertificate;

    private final MerrigeCertificateDAO merrigeCertificateDAO = new MerrigeCertificateDAO();

    @BeforeEach
    @Step("Создание заявки на регистрацию брака через API")
    void createMerrigeApplication() {
        ApplicationRequestDto requestDto =
                TestDataFactory.weddingApplication();

        ApplicationResponseDto<AppForMerrigeDto> response =
                ZagsApiSteps.sendApplication(requestDto, new TypeRef<ApplicationResponseDto<AppForMerrigeDto>>() {
                });

        createdMerrigeCertificateId = response.getData().getMerrigeCertificateId();
        createdCitizenId = response.getData().getCitizenId();
        createdApplicantId = response.getData().getApplicantId();

        expectedMerrigeCertificate = new MerrigeCertificate();

        expectedMerrigeCertificate.setMerrigeCertificateId(createdMerrigeCertificateId);
        expectedMerrigeCertificate.setCitizenId(createdCitizenId);
        expectedMerrigeCertificate.setDateOfMerrige(LocalDate.parse(requestDto.getDateOfMarriage(), DateTimeFormatter.ISO_LOCAL_DATE));
        expectedMerrigeCertificate.setNameOfSpouce(requestDto.getAnotherPersonFirstName());
        expectedMerrigeCertificate.setMiddlenameOfSpouce(requestDto.getAnotherPersonMiddleName());
        expectedMerrigeCertificate.setSurnameOfSpouce(requestDto.getAnotherPersonLastName());
        expectedMerrigeCertificate.setDateOfBirthOfSpouce(LocalDate.parse(requestDto.getBirthOfAnotherPerson(), DateTimeFormatter.ISO_LOCAL_DATE));
        expectedMerrigeCertificate.setNewSurnameOfSpouce(requestDto.getNewLastName());
        expectedMerrigeCertificate.setPassportNumberOfSpouce(requestDto.getAnotherPersonPassport());
    }

    @AfterEach
    @Step("Удаление тестовых данных")
    void cleanUp() {
        try {
            CleanDataTestDB.cleanUp(
                    createdApplicantId,
                    createdCitizenId,
                    "свидетельство о браке",
                    () -> {
                        if (createdMerrigeCertificateId != null) {
                            merrigeCertificateDAO.deleteById(
                                    createdMerrigeCertificateId
                            );
                        }
                    }
            );
        } finally {
            createdMerrigeCertificateId = null;
            createdApplicantId = null;
            createdCitizenId = null;
        }
    }


    @Test
    @DisplayName("Создание свидетельства о браке")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет свидетельство о браке")
    void verifyMerrigeCertificateInDatabase() {

        MerrigeCertificate actual = merrigeCertificateDAO.findById(createdMerrigeCertificateId);

        assertNotNull(actual, "Свидетельство о браке должно существовать в БД после сохранения");
        assertAll(
                () -> assertEquals(expectedMerrigeCertificate.getCitizenId(), actual.getCitizenId()),
                () -> assertEquals(expectedMerrigeCertificate.getDateOfMerrige(), actual.getDateOfMerrige()),
                () -> assertEquals(expectedMerrigeCertificate.getNameOfSpouce(), actual.getNameOfSpouce()),
                () -> assertEquals(expectedMerrigeCertificate.getMiddlenameOfSpouce(), actual.getMiddlenameOfSpouce()),
                () -> assertEquals(expectedMerrigeCertificate.getSurnameOfSpouce(), actual.getSurnameOfSpouce()),
                () -> assertEquals(expectedMerrigeCertificate.getDateOfBirthOfSpouce(), actual.getDateOfBirthOfSpouce()),
                () -> assertEquals(expectedMerrigeCertificate.getNewSurnameOfSpouce(), actual.getNewSurnameOfSpouce()),
                () -> assertEquals(expectedMerrigeCertificate.getPassportNumberOfSpouce(), actual.getPassportNumberOfSpouce())
        );
    }
}
