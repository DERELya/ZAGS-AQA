package api;

import api.dto.ApplicationDto;
import api.dto.ApplicationsResponseDto;
import api.steps.ZagsApiSteps;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZAGS")
@Feature("API — получение заявок")
public class GetApplicationsTest {

    private static final int PAGE = 1;
    private static final int LIMIT = 200;
    private static final int PAGINATION_LIMIT = 5;
    private static final int MAX_PAGINATION_ATTEMPTS = 3;

    private record PaginationSnapshot(
            ApplicationsResponseDto firstPage,
            ApplicationsResponseDto secondPage,
            int total
    ) {
    }

    @Test
    @DisplayName("Получение первой страницы заявок")
    @Story("Получение списка заявок с пагинацией")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяет HTTP 200, корректность total,количество записей и обязательные поля заявок."
    )
    void getApplications() {
        ApplicationsResponseDto response =
                ZagsApiSteps.getApplications(PAGE, LIMIT);

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
    @Description("Проверяет размеры двух соседних страниц и отсутствие дубликатов ID, повторяет чтение, если данные стенда изменились во время проверки")
    void getApplicationsPagination() {
        ApplicationsResponseDto firstPage = null;
        ApplicationsResponseDto secondPage = null;

        int firstTotal = -1;
        int secondTotal = -1;

        for (int attempt = 1; attempt <= MAX_PAGINATION_ATTEMPTS; attempt++) {
            firstPage = ZagsApiSteps.getApplications(1, PAGINATION_LIMIT);
            secondPage = ZagsApiSteps.getApplications(2, PAGINATION_LIMIT);

            assertNotNull(firstPage, "Ответ первой страницы отсутствует");
            assertNotNull(secondPage, "Ответ второй страницы отсутствует");
            assertNotNull(firstPage.getData(), "Данные первой страницы отсутствуют");
            assertNotNull(secondPage.getData(), "Данные второй страницы отсутствуют");

            firstTotal = parseTotal(firstPage.getTotal());
            secondTotal = parseTotal(secondPage.getTotal());

            if (firstTotal == secondTotal) {
                break;
            }
        }

        assertEquals(
                firstTotal,
                secondTotal,
                "Данные стенда изменялись между запросами страниц. "
                        + "Не удалось получить согласованный результат за "
                        + MAX_PAGINATION_ATTEMPTS + " попытки"
        );

        final int stableFirstTotal = firstTotal;
        final int stableSecondTotal = secondTotal;

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
                        stableFirstTotal >= firstApplications.size(),
                        "total меньше размера первой страницы"
                ),

                () -> assertTrue(
                        stableSecondTotal >= secondApplications.size(),
                        "total меньше размера второй страницы"
                ),

                () -> assertEquals(
                        Math.min(PAGINATION_LIMIT, stableFirstTotal),
                        firstApplications.size(),
                        "Размер первой страницы не соответствует total и limit"
                ),

                () -> assertEquals(
                        Math.min(
                                PAGINATION_LIMIT,
                                Math.max(0, stableSecondTotal - PAGINATION_LIMIT)
                        ),
                        secondApplications.size(),
                        "Размер второй страницы не соответствует total и limit"
                )
        );

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