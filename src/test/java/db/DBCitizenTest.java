package db;

import api.dto.AppForDeathDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import api.steps.ZagsApiSteps;
import db.model.Citizen;
import db.repository.CitizenDAO;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("DB — Гражданин")
public class DBCitizenTest {

    private Integer createdCitizenId;
    private Citizen expectedCitizen;

    private final CitizenDAO citizenDAO =
            new CitizenDAO();

    @Test
    @DisplayName("Создание гражданина")
    @Story("Тестирование базы данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что база данных корректно сохраняет гражданина")
    void createCitizenAndVerifyDataInDatabase() throws SQLException {

        Citizen actual = citizenDAO.findById(createdCitizenId);

        assertNotNull(actual, "Гражданин должен существовать в БД");
        assertAll(
                () -> assertEquals(expectedCitizen.getSurname(), actual.getSurname()),
                () -> assertEquals(expectedCitizen.getName(), actual.getName()),
                () -> assertEquals(expectedCitizen.getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expectedCitizen.getPassportNumber(), actual.getPassportNumber()),
                () -> assertEquals(expectedCitizen.getGender(), actual.getGender()),
                () -> assertEquals(expectedCitizen.getRegistrationAddress(), actual.getRegistrationAddress())
        );
    }

    @BeforeEach
    void createCitizen() {
        ApplicationRequestDto requestDto =
                TestDataFactory.deathApplication();


        expectedCitizen = new Citizen();
        expectedCitizen.setName(requestDto.getCitizenFirstName());
        expectedCitizen.setSurname(requestDto.getCitizenLastName());
        expectedCitizen.setMiddleName(requestDto.getCitizenMiddleName());
        expectedCitizen.setRegistrationAddress(
                requestDto.getCitizenAddress()
        );
        expectedCitizen.setGender(
                requestDto.getCitizenGender()
        );
        expectedCitizen.setPassportNumber(
                requestDto.getCitizenNumberOfPassport()
        );

        ApplicationResponseDto<AppForDeathDto> response =
                ZagsApiSteps.sendApplication(requestDto,new TypeRef<ApplicationResponseDto<AppForDeathDto>>() {});

        createdCitizenId = response.getData().getCitizenId();
    }

    @AfterEach
    @Step("Удаление гражданина после теста")
    void cleanUp() throws SQLException {
        if (createdCitizenId != 0) {
            citizenDAO.deleteById(createdCitizenId);
        }
    }
}
