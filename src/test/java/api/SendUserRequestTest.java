package api;

import api.dto.AppForDeathDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;
import utils.ZagsApiSteps;

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

        ApplicationResponseDto<AppForDeathDto> response =
                ZagsApiSteps.sendDeathApplication(
                        TestDataFactory.deathApplication()
                );

        assertNotNull(response.getData(), "Данные заявки отсутствуют");

        AppForDeathDto data = response.getData();

        assertAll(
                "Проверка ответа при создании заявки на регистрацию смерти",

                () -> assertNotNull(
                        response.getRequestId(),
                        "requestId отсутствует"
                ),

                () -> assertNotNull(
                        data.getDeathCertificateId(),
                        "deathcertificateid отсутствует"
                ),

                () -> assertNotNull(
                        data.getApplicantId(),
                        "applicantid отсутствует"
                ),

                () -> assertNotNull(
                        data.getCitizenId(),
                        "citizenid отсутствует"
                ),

                () -> assertNotNull(
                        data.getApplicationId(),
                        "applicationid отсутствует"
                ),
                () -> assertTrue(data.getApplicationId() > 0, "Некорректный Applicationid"),
                () -> assertTrue(data.getCitizenId() > 0, "Некорректный CitizenId"),
                () -> assertTrue(data.getDeathCertificateId() > 0, "Некорректный DeathCertificateId"),
                () -> assertTrue(data.getApplicantId() > 0, "Некорректный ApplicantId")
        );

        Response responseApp = ZagsApiSteps.getApplicationStatus(data.getApplicationId());
        assertAll(
                () -> assertEquals("under consideration", responseApp.jsonPath().get("data.statusofapplication"), "Статус заявки не совпадает"),
                () -> assertEquals("Получение свидетельства о смерти", responseApp.jsonPath().get("data.kindofapplication"), "Тип заявки не совпадает")
        );
    }

    @Test
    @DisplayName("Отклонение заявки без фамилии заявителя")
    @Story("Валидация обязательных полей")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяем, что API отклоняет заявку, в которой personalLastName равен null.")
    void rejectApplicationWithoutApplicantLastName() {
        ApplicationRequestDto request =
                TestDataFactory.deathApplication();

        request.setPersonalLastName(null);

        Response response = ZagsApiSteps.sendUserRequest(request);

        assertEquals(400, response.statusCode(),
                "Ожидался HTTP статус 400 при отсутствии personalLastName. Ответ: " + response.asPrettyString()
        );
    }

    @Test
    @DisplayName("Отклонение заявки с неизвестным типом услуги")
    @Story("Валидация поля mode")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяем, что API отклоняет заявку с недопустимым значением mode.")
    void rejectApplicationWithInvalidMode() {
        ApplicationRequestDto request =
                TestDataFactory.deathApplication();

        request.setMode("qwerty");

        Response response = ZagsApiSteps.sendUserRequest(request);

        assertEquals(400, response.statusCode(), "Ожидался HTTP статус 400 для неизвестного mode. Ответ: " + response.asPrettyString()
        );
    }
}