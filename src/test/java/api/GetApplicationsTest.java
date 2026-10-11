package api;

import io.qameta.allure.*;
import model.dto.ApplicationDto;
import model.dto.ApplicationsResponseDto;
import model.specs.RequestSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("API — получение заявок")
public class GetApplicationsTest {
    private static final int PAGE = 1;
    private static final int LIMIT = 200;

    @Test
    @DisplayName("Получение 200 заявок на первой странице")
    @Story("Получение списка заявок с пагинацией")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяет получение первой страницы заявок через GET /getApplications:")
    void getApplications() {


        ApplicationsResponseDto response =
                getApplicationsPage(PAGE, LIMIT);

        assertNotNull(response.getTotal(), "total отсутствует");
        assertFalse(
                response.getTotal().isBlank(),
                "total пустой"
        );

        assertNotNull(response.getData(), "Список заявок отсутствует");
        assertFalse(response.getData().isEmpty(), "Список заявок пустой");
        assertEquals(
                LIMIT,
                response.getData().size(),
                "Количество заявок не равно ожидаемому"
        );

        for (ApplicationDto application : response.getData()) {

            assertAll(
                    "Проверка заявки ID: " + application.getApplicationId(),

                    () -> assertNotNull(application.getApplicationId(), "applicationid отсутствует"),
                    () -> assertTrue(application.getApplicantId() > 0, "Некорректный applicationid"),
                    () -> assertNotNull(application.getCitizenId(), "citizenid отсутствует"),
                    () -> assertTrue(application.getCitizenId() > 0, "Некорректный citizenid"),
                    () -> assertNotNull(application.getApplicantId(), "applicantid отсутствует"),
                    () -> assertTrue(application.getApplicantId() > 0, "Некорректный applicantid"),
                    () -> assertNotNull(application.getDateOfApplication(), "dateofapplication отсутствует"),
                    () -> assertNotNull(application.getKindOfApplication(), "kindofapplication отсутствует"),
                    () -> assertNotNull(application.getStatusOfApplication(), "statusofapplication отсутствует"),
                    () -> assertNotNull(application.getChannel(), "channel отсутствует")
            );
        }
    }

    @Step("Получаем список заявок: страница {page}, лимит {limit}")
    private ApplicationsResponseDto getApplicationsPage(
            int page,
            int limit
    ) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .queryParam("page", page)
                .queryParam("limit", limit)
                .when()
                .get("/getApplications")
                .then()
                .statusCode(200)
                .extract()
                .as(ApplicationsResponseDto.class);
    }
}
