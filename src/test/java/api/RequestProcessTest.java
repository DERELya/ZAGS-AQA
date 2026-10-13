package api;

import api.dto.AdministrationApplicantionDto;
import api.dto.ApplicationDto;
import api.dto.ApplicationResponseDto;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import api.steps.ZagsApiSteps;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import model.data.ActionForApplication;
import model.data.StatusOfApplication;
import model.data.TypeOfApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("API — обработка заявок")
public class RequestProcessTest {

    private static long staffId;
    private long applId;

    @Test
    @DisplayName("Позитивный сценарий одобрения заявки")
    @Story("Одобрение заявки на регистрацию смерти")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Создаёт администратора и заявку на регистрацию смерти, проверяет одобрение заявки через POST /requestProcess")
    void approveApplication() {

        AdministrationApplicantionDto body = new AdministrationApplicantionDto();
        body.setAction(ActionForApplication.APPROVE.getValue());
        body.setStaffid(staffId);
        body.setApplId(applId);
        Response rawResponse = ZagsApiSteps.processApplication(
                RequestSpecs.baseRequestSpec(),
                body
        );
        rawResponse.then()
                .spec(ResponseSpecs.successResponse());

        ApplicationResponseDto<ApplicationDto> response =
                rawResponse.as(
                        new TypeRef<ApplicationResponseDto<ApplicationDto>>() {
                        }
                );

        ApplicationDto application = response.getData();

        assertNotNull(response.getData(), "заявка пустая");

        assertAll(
                "Проверка одобренной заявки",

                () -> assertNotNull(
                        response.getRequestId(),
                        "requestId отсутствует"
                ),

                () -> assertNotNull(
                        application.getApplicationId(),
                        "applicationId отсутствует"
                ),

                () -> assertEquals(
                        applId,
                        application.getApplicationId(),
                        "Возвращён ID другой заявки"
                ),

                () -> assertNotNull(
                        application.getCitizenId(),
                        "citizenId отсутствует"
                ),

                () -> assertNotNull(
                        application.getApplicantId(),
                        "applicantId отсутствует"
                ),

                () -> assertNotNull(
                        application.getDateOfApplication(),
                        "dateofapplication отсутствует"
                ),

                () -> assertEquals(TypeOfApplication.DEATH.getValue(),
                        application.getKindOfApplication(),
                        "kindofapplication не соответствует возможным"
                ),

                () -> assertEquals(
                        StatusOfApplication.APPROVED.getValue(),
                        application.getStatusOfApplication(),
                        "Статус заявки в ответе не approved"
                ),

                () -> assertNotNull(
                        application.getChannel(),
                        "channel отсутствует"
                )
        );

        ApplicationResponseDto<ApplicationDto> persistedResponse =
                ZagsApiSteps.getApplicationStatus(applId);

        assertNotNull(
                persistedResponse,
                "Ответ GET /getApplStatus отсутствует"
        );
        assertNotNull(
                persistedResponse.getData(),
                "Заявка не найдена после одобрения"
        );

        ApplicationDto approvedApplication = persistedResponse.getData();

        assertAll(
                "Проверка одобренной заявки через GET /getApplStatus",

                () -> assertEquals(
                        StatusOfApplication.APPROVED.getValue(),
                        approvedApplication.getStatusOfApplication(),
                        "Статус approved не сохранился в системе"
                ),

                () -> assertEquals(
                        TypeOfApplication.DEATH.getValue(),
                        approvedApplication.getKindOfApplication(),
                        "Тип сохранённой заявки не совпадает"
                )
        );
    }

    @Test
    @DisplayName("Администрирование заявки с недопустимым действием")
    @Story("Валидация поля action")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяем, что API отклоняет заявку, в которой action недопустимый.")
    void administrationApplicationWithInvalidAction() {
        AdministrationApplicantionDto body = new AdministrationApplicantionDto();
        body.setAction("qweyui");
        body.setStaffid(staffId);
        body.setApplId(applId);
        Response response = ZagsApiSteps.processApplication(
                RequestSpecs.baseRequestSpec(),
                body
        );

        assertEquals(
                400,
                response.statusCode(),
                "Ожидался HTTP 400. Ответ: " + response.asPrettyString()
        );

        String errorBody = response.asString().toLowerCase(java.util.Locale.ROOT);

        assertFalse(
                errorBody.isBlank(),
                "Тело ошибки пустое"
        );

        assertTrue(
                errorBody.contains("action")
                        || errorBody.contains("invalid")
                        || errorBody.contains("недопуст")
                        || errorBody.contains("действ")
                        || errorBody.contains("неправильн")
                        || errorBody.contains("error")
                        || errorBody.contains("fail")
                        || errorBody.contains("ошиб"),
                "Ответ не содержит сообщение с ошибкой о валидации action: "
                        + response.asPrettyString()
        );
    }

    @Test
    @DisplayName("Администрирование заявки без авторизации")
    @Story("тестирование безопасности")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяем, что API отклоняет заявку, без авторизации")
    void administrationApplicationWithoutAuth() {

        AdministrationApplicantionDto body = new AdministrationApplicantionDto();
        body.setAction(ActionForApplication.APPROVE.getValue());
        body.setStaffid(staffId);
        body.setApplId(applId);

        Response response = ZagsApiSteps.processApplication(
                RequestSpecs.unauthenticatedRequestSpec(),
                body
        );

        assertEquals(
                401,
                response.statusCode(),
                "Ожидался HTTP 401. Ответ: " + response.asPrettyString()
        );

        String errorBody = response.asString()
                .toLowerCase(java.util.Locale.ROOT);

        String authChallenge = response.getHeader("WWW-Authenticate");

        assertTrue(
                authChallenge != null
                        || errorBody.contains("unauthorized")
                        || errorBody.contains("authentication")
                        || errorBody.contains("authorization")
                        || errorBody.contains("авторизац")
                        || errorBody.contains("аутентификац"),
                "Ответ не содержит сообщение об отказе в авторизации: "
                        + response.asPrettyString()
        );
    }

    @BeforeAll
    static void setUp() {
        staffId = ZagsApiSteps.createAdministrator(
                TestDataFactory.adminRequest()
        );
    }

    @BeforeEach
    public void createApplicationForTest() {
        applId = ZagsApiSteps.createDeathApplication(
                TestDataFactory.deathApplication()
        );
    }

}
