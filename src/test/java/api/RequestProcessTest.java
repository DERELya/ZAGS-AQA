package api;

import api.dto.AdministrationApplicantionDto;
import api.dto.ApplicationDto;
import api.dto.ApplicationResponseDto;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;
import utils.ZagsApiSteps;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("API — обработка заявок")
public class RequestProcessTest {

    private static long staffId;
    private static long applId;

    @Test
    @DisplayName("Позитивный сценарий одобрения заявки")
    @Story("Одобрение заявки на регистрацию смерти")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Создаёт администратора и заявку на регистрацию смерти, проверяет одобрение заявки через POST /requestProcess")
    void approveApplication() {

        AdministrationApplicantionDto body = new AdministrationApplicantionDto();
        body.setAction("approved");
        body.setStaffid(staffId);
        body.setApplId(applId);
        ApplicationResponseDto<ApplicationDto> response =
                given()
                        .spec(RequestSpecs.baseRequestSpec())
                        .body(body)
                        .when()
                        .post("/requestProcess")
                        .then()
                        .spec(ResponseSpecs.successResponse())
                        .extract()
                        .as(new TypeRef<ApplicationResponseDto<ApplicationDto>>() {
                        });

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
                        application.getApplicationId().longValue(),
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

                () -> assertNotNull(
                        application.getKindOfApplication(),
                        "kindofapplication отсутствует"
                ),

                () -> assertEquals(
                        "approved",
                        application.getStatusOfApplication(),
                        "Статус заявки не approved"
                ),

                () -> assertNotNull(
                        application.getChannel(),
                        "channel отсутствует"
                )
        );
    }

    @Test
    @DisplayName("Администрирование заявки с недопустимым действием")
    @Story("Валидация поля action")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяем, что API отклоняет заявку, в которой action недопустимый.")
    void administrationApplicationWithoutInvalidAction() {
        AdministrationApplicantionDto body = new AdministrationApplicantionDto();
        body.setAction("qweyui");
        body.setStaffid(staffId);
        body.setApplId(applId);
        given()
                .spec(RequestSpecs.baseRequestSpec())
                .body(body)
                .when()
                .post("/requestProcess")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(400);


    }

    @Test
    @DisplayName("Администрирование заявки без авторизации")
    @Story("тестирование безопасности")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяем, что API отклоняет заявку, без авторизации")
    void administrationApplicationWithoutAuth() {
        AdministrationApplicantionDto body = new AdministrationApplicantionDto();
        body.setAction("approved");
        body.setStaffid(staffId);
        body.setApplId(applId);
        given()
                .spec(RequestSpecs.unauthenticatedRequestSpec())
                .body(body)
                .when()
                .post("/requestProcess")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(401);
    }

    @BeforeAll
    static void setUp() {
        staffId = ZagsApiSteps.createAdministrator(
                TestDataFactory.adminRequest()
        );

        applId = ZagsApiSteps.createDeathApplication(
                TestDataFactory.deathApplication()
        );

    }

}
