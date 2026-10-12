package api;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;
import utils.ZagsApiSteps;

import static org.junit.jupiter.api.Assertions.*;

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

        Response response = ZagsApiSteps.sendAdminRequest(
                TestDataFactory.adminRequest()
        );

        Object staffId = response.jsonPath().get("data.staffid");
        Object requestId = response.jsonPath().get("requestId");

        assertAll(
                "Проверка ответа регистрации администратора",

                () -> assertNotNull(requestId, "requestId отсутствует"),

                () -> assertTrue(
                        staffId instanceof Number
                                && ((Number) staffId).longValue() > 0,
                        "Некорректный data.staffid"
                )
        );
    }

}
