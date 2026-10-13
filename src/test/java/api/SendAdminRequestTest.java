package api;

import api.dto.AdminResponseDto;
import api.steps.ZagsApiSteps;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

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

        AdminResponseDto response = ZagsApiSteps.sendAdminRequest(
                TestDataFactory.adminRequest()
        );

        assertNotNull(response, "Ответ сервера отсутствует");
        assertNotNull(response.getData(), "Данные администратора отсутствуют");

        assertAll(
                "Проверка ответа регистрации администратора",

                () -> assertNotNull(
                        response.getRequestId(),
                        "requestId отсутствует"
                ),

                () -> assertNotNull(
                        response.getData().getStaffId(),
                        "staffId отсутствует"
                ),

                () -> assertTrue(
                        response.getData().getStaffId() > 0,
                        "Некорректный staffId"
                )
        );
    }

}
