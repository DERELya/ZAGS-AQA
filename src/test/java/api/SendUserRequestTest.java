package api;

import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import model.dto.AppForDeathDto;
import model.dto.ApplicationRequestDto;
import model.dto.ApplicationResponseDto;
import model.specs.RequestSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("API — создание заявки")
public class SendUserRequestTest {

    @Test
    @DisplayName("Создание заявки на регистрацию смерти")
    @Story("Отправка заявки на регистрацию смерти")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяет создание заявки на регистрацию смерти через POST /sendUserRequest.")
    void sendUserRequest() {

        ApplicationRequestDto application = createDeathApplication();

        ApplicationResponseDto<AppForDeathDto> response =
                given()
                        .spec(RequestSpecs.baseRequestSpec())
                        .body(application)
                        .when()
                        .post("/sendUserRequest")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(new TypeRef<ApplicationResponseDto<AppForDeathDto>>() {
                        });

        assertNotNull(response.getData(), "Данные заявки отсутствуют");

        AppForDeathDto data = response.getData();

        assertAll(
                "Проверка ответа при создании заявки на регистрацию смерти",

                () -> assertNotNull(
                        response.getRequestId(),
                        "requestId отсутствует"
                ),

                () -> assertNotNull(
                        data.getDeathcertificateid(),
                        "deathcertificateid отсутствует"
                ),

                () -> assertNotNull(
                        data.getApplicantid(),
                        "applicantid отсутствует"
                ),

                () -> assertNotNull(
                        data.getCitizenid(),
                        "citizenid отсутствует"
                ),

                () -> assertNotNull(
                        data.getApplicationid(),
                        "applicationid отсутствует"
                ),
                () -> assertTrue(data.getApplicantid() > 0, "Некорректный ApplicantId"),
                () -> assertTrue(data.getCitizenid() > 0, "Некорректный CitizenId"),
                () -> assertTrue(data.getDeathcertificateid() > 0, "Некорректный DeathCertificateId"),
                () -> assertTrue(data.getApplicantid() > 0, "Некорректный ApplicantId")
        );
    }

    @Step("Подготавливаем данные заявки на регистрацию смерти")
    private ApplicationRequestDto createDeathApplication() {
        ApplicationRequestDto application = new ApplicationRequestDto();

        application.setMode("death");
        application.setPersonalLastName("Сергеев");
        application.setPersonalFirstName("Олег");
        application.setPersonalMiddleName("Олегович");
        application.setPersonalPhoneNumber("37526785632");
        application.setPersonalNumberOfPassport("12378612");
        application.setPersonalAddress("Брест");

        application.setCitizenLastName("Сергеев");
        application.setCitizenFirstName("Олег");
        application.setCitizenMiddleName("Олегович");
        application.setCitizenGender("Муж");
        application.setCitizenBirthDate("2005-11-12");
        application.setCitizenAddress("Брест");
        application.setCitizenNumberOfPassport("32785631");

        application.setDeath_placeOfDeath("Брест");
        application.setDeath_dateOfDeath(
                LocalDate.now()
                        .minusDays(10)
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );

        return application;
    }
}