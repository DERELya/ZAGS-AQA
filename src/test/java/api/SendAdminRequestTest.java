package api;

import io.qameta.allure.*;
import model.dto.AdminRequestDto;
import model.specs.RequestSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("ZAGS")
@Feature("API — регистрация администратора")
public class SendAdminRequestTest {

    @Test
    @DisplayName("Позитивный сценарий регистрации администратора")
    @Story("Отправка заявки на регистрацию администратора")
    @Severity(SeverityLevel.CRITICAL)
    @Description(
            "Проверяет отправку заявки на регистрацию администратора через POST /sendAdminRequest"
    )
    void sendAdminRequest() {

        AdminRequestDto request = createAdminRequest();

        given()
                .spec(RequestSpecs.baseRequestSpec())
                .body(request)

                .when()
                .post("/sendAdminRequest")

                .then()
                .statusCode(200)
                .body("data.staffid", notNullValue())
                .body("data.staffid", greaterThan(0))
                .body("data.staffid", instanceOf(Number.class))
                .body("requestId", notNullValue());
    }

    @Step("Подготавливаем данные администратора")
    private AdminRequestDto createAdminRequest() {
        AdminRequestDto request = new AdminRequestDto();

        request.setPersonalLastName("Сергеев");
        request.setPersonalFirstName("Олег");
        request.setPersonalMiddleName("Олегович");
        request.setPersonalPhoneNumber("37526785632");
        request.setPersonalNumberOfPassport("12378312");
        request.setDateOfBirth("2002-11-12");

        return request;
    }
}
