package api.steps;

import api.dto.*;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class ZagsApiSteps {
    private ZagsApiSteps() {
    }

    @Step("Отправляем запрос на регистрацию администратора")
    public static AdminResponseDto sendAdminRequest(AdminRequestDto requestDto) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .body(requestDto)
                .when()
                .post("/sendAdminRequest")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract()
                .as(AdminResponseDto.class);
    }

    @Step("Создаём администратора и получаем его ID")
    public static long createAdministrator(AdminRequestDto requestDto) {
        AdminResponseDto response = sendAdminRequest(requestDto);
        if (response.getData() == null
                || response.getData().getStaffId() == null
                || response.getData().getStaffId() <= 0) {
            throw new IllegalStateException(
                    "API не вернул корректный data.staffid"
            );
        }

        return response.getData().getStaffId();
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

    @Step("Получаем статус заявки по ID {applicationId}")
    public static ApplicationResponseDto<ApplicationDto> getApplicationStatus(Long applicationId) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .pathParam("applicationId", applicationId)
                .when()
                .get("/getApplStatus/{applicationId}")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract()
                .as(new TypeRef<ApplicationResponseDto<ApplicationDto>>() {});
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

    @Step("Отправляем запрос на обработку заявки")
    public static Response processApplication(
            RequestSpecification spec,
            AdministrationApplicantionDto body
    ) {
        return given()
                .spec(spec)
                .body(body)
                .when()
                .post("/requestProcess")
                .then()
                .extract()
                .response();
    }

    @Step("Получаем заявки: страница {page}, лимит {limit}")
    public static ApplicationsResponseDto getApplications(int page,int limit) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .queryParam("page", page)
                .queryParam("limit", limit)
                .when()
                .get("/getApplications")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract()
                .as(ApplicationsResponseDto.class);
    }
}
