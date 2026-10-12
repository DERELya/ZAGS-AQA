package api;

import api.dto.ApplicationDto;
import api.dto.ApplicationsResponseDto;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("API — получение заявок")
public class GetApplicationsTest {

    private static final int PAGE = 1;
    private static final int LIMIT = 200;
    private static final int PAGINATION_LIMIT = 5;

    @Test
    @DisplayName("Получение первой страницы заявок")
    @Story("Получение списка заявок с пагинацией")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяет HTTP 200, корректность total,количество записей и обязательные поля заявок."
    )
    void getApplications() {
        ApplicationsResponseDto response =
                getApplicationsPage(PAGE, LIMIT);

        int total = parseTotal(response.getTotal());

        assertNotNull(response.getData(), "Список заявок отсутствует");

        List<ApplicationDto> applications = response.getData();
        int actualSize = applications.size();

        assertAll(
                "Проверка пагинации первой страницы",
                () -> assertTrue(
                        actualSize <= LIMIT,
                        "Количество заявок превышает limit"
                ),
                () -> assertTrue(
                        total >= actualSize,
                        "total меньше количества возвращённых заявок"
                ),
                () -> assertEquals(
                        Math.min(LIMIT, total),
                        actualSize,
                        "Количество заявок не соответствует total и limit"
                )
        );

        for (ApplicationDto application : applications) {
            assertNotNull(application, "В списке обнаружена пустая заявка");

            assertAll(
                    "Проверка заявки ID: " + application.getApplicationId(),

                    () -> assertNotNull(
                            application.getApplicationId(),
                            "applicationid отсутствует"
                    ),
                    () -> assertTrue(
                            application.getApplicationId() != null
                                    && application.getApplicationId() > 0,
                            "Некорректный applicationid"
                    ),
                    () -> assertNotNull(
                            application.getCitizenId(),
                            "citizenid отсутствует"
                    ),
                    () -> assertTrue(
                            application.getCitizenId() != null
                                    && application.getCitizenId() > 0,
                            "Некорректный citizenid"
                    ),
                    () -> assertNotNull(
                            application.getApplicantId(),
                            "applicantid отсутствует"
                    ),
                    () -> assertTrue(
                            application.getApplicantId() != null
                                    && application.getApplicantId() > 0,
                            "Некорректный applicantid"
                    ),
                    () -> assertNotNull(
                            application.getDateOfApplication(),
                            "dateofapplication отсутствует"
                    ),
                    () -> assertNotNull(
                            application.getKindOfApplication(),
                            "kindofapplication отсутствует"
                    ),
                    () -> assertNotNull(
                            application.getStatusOfApplication(),
                            "statusofapplication отсутствует"
                    ),
                    () -> assertNotNull(
                            application.getChannel(),
                            "channel отсутствует"
                    )
            );
        }
    }

    @Test
    @DisplayName("Пагинация не возвращает одинаковые заявки на соседних страницах")
    @Story("Получение списка заявок с пагинацией")
    @Severity(SeverityLevel.NORMAL)
    @Description("Запрашивает две страницы с небольшим лимитом и проверяем отсутствие дубликатов applicationid")
    void getApplicationsPagination() {
        ApplicationsResponseDto firstPage =
                getApplicationsPage(1, PAGINATION_LIMIT);

        ApplicationsResponseDto secondPage =
                getApplicationsPage(2, PAGINATION_LIMIT);

        assertNotNull(firstPage, "Ответ первой страницы отсутствует");
        assertNotNull(secondPage, "Ответ второй страницы отсутствует");

        int firstTotal = parseTotal(firstPage.getTotal());
        int secondTotal = parseTotal(secondPage.getTotal());

        assertNotNull(firstPage.getData(), "Данные первой страницы отсутствуют");
        assertNotNull(secondPage.getData(), "Данные второй страницы отсутствуют");

        List<ApplicationDto> firstApplications = firstPage.getData();
        List<ApplicationDto> secondApplications = secondPage.getData();

        assertAll(
                "Проверка размеров страниц",

                () -> assertTrue(
                        firstApplications.size() <= PAGINATION_LIMIT,
                        "Первая страница превышает limit"
                ),
                () -> assertTrue(
                        secondApplications.size() <= PAGINATION_LIMIT,
                        "Вторая страница превышает limit"
                ),
                () -> assertTrue(
                        firstTotal >= firstApplications.size(),
                        "total первой страницы меньше размера списка"
                ),
                () -> assertTrue(
                        secondTotal >= secondApplications.size(),
                        "total второй страницы меньше размера списка"
                ),
                () -> assertEquals(
                        Math.min(PAGINATION_LIMIT, firstTotal),
                        firstApplications.size(),
                        "Размер первой страницы не соответствует total и limit"
                )
        );

        if (firstTotal > PAGINATION_LIMIT) {
            assertFalse(
                    secondApplications.isEmpty(),
                    "Вторая страница пуста, хотя total больше limit"
            );
        }

        Set<Integer> firstIds = getApplicationIds(firstApplications, "первой");
        Set<Integer> secondIds = getApplicationIds(secondApplications, "второй");

        assertEquals(
                firstApplications.size(),
                firstIds.size(),
                "На первой странице есть повторяющиеся applicationid"
        );

        assertEquals(
                secondApplications.size(),
                secondIds.size(),
                "На второй странице есть повторяющиеся applicationid"
        );

        Set<Integer> intersection = new HashSet<>(firstIds);
        intersection.retainAll(secondIds);

        assertTrue(
                intersection.isEmpty(),
                "Страницы содержат одинаковые applicationid: " + intersection
        );
    }

    @Step("Получаем список заявок: страница {page}, лимит {limit}")
    private ApplicationsResponseDto getApplicationsPage(int page, int limit) {
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

    @Step("Проверяем, что поле total содержит неотрицательное целое число")
    private int parseTotal(String totalValue) {
        assertNotNull(totalValue, "total отсутствует");
        assertFalse(totalValue.isBlank(), "total пустой");

        int total = assertDoesNotThrow(
                () -> Integer.parseInt(totalValue),
                "Поле total должно быть целым числом, получено: " + totalValue
        );

        assertTrue(total >= 0, "total не может быть отрицательным");

        return total;
    }

    private Set<Integer> getApplicationIds(List<ApplicationDto> applications, String pageDescription) {
        return applications.stream()
                .map(application -> {
                    assertNotNull(
                            application,
                            "Пустой элемент на " + pageDescription + " странице"
                    );
                    assertNotNull(
                            application.getApplicationId(),
                            "applicationid отсутствует на "
                                    + pageDescription + " странице"
                    );
                    return application.getApplicationId();
                })
                .collect(Collectors.toSet());
    }
}