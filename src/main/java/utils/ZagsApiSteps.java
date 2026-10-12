package utils;

import api.dto.AdminRequestDto;
import api.dto.AppForDeathDto;
import api.dto.ApplicationRequestDto;
import api.dto.ApplicationResponseDto;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class ZagsApiSteps {

    @Step("Отправляем запрос на регистрацию администратора")
    public static Response sendAdminRequest(AdminRequestDto requestDto) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .body(requestDto)
                .when()
                .post("/sendAdminRequest")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract()
                .response();
    }

    @Step("Создаём администратора и получаем его ID")
    public static long createAdministrator(AdminRequestDto requestDto) {
        Object value = sendAdminRequest(requestDto)
                .jsonPath()
                .get("data.staffid");

        if (!(value instanceof Number staffId) || staffId.longValue() <= 0) {
            throw new IllegalStateException(
                    "API вернул некорректный data.staffid"
            );
        }

        return staffId.longValue();
    }

    @Step("Отправляем заявку на регистрацию смерти")
    public static ApplicationResponseDto<AppForDeathDto> sendDeathApplication(ApplicationRequestDto requestDto) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .body(requestDto)
                .when()
                .post("/sendUserRequest")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract()
                .as(new TypeRef<ApplicationResponseDto<AppForDeathDto>>() {
                });
    }

    @Step("Создаём заявку на регистрацию смерти и получаем её ID")
    public static long createDeathApplication(ApplicationRequestDto requestDto) {
        ApplicationResponseDto<AppForDeathDto> response =
                sendDeathApplication(requestDto);

        if (response.getData() == null
                || response.getData().getApplicationId() == null
                || response.getData().getApplicationId() <= 0) {
            throw new IllegalStateException(
                    "API не вернул корректный data.applicationid"
            );
        }

        return response.getData().getApplicationId();
    }

    @Step("Получаем заявку по ID")
    public static Response getApplicationStatus(Long applicationId) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .pathParam("applicationId", applicationId)
                .when()
                .get("/getApplStatus/{applicationId}")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract()
                .response();
    }

    @Step("Отправляем заявку и получаем ответ API без предварительной проверки статуса")
    public static Response sendUserRequest(ApplicationRequestDto requestDto) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .body(requestDto)
                .when()
                .post("/sendUserRequest")
                .then()
                .extract()
                .response();
    }
}
