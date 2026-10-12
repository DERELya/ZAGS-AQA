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
        PaginationSnapshot snapshot = getStablePaginationSnapshot();

        List<ApplicationDto> firstApplications =
                snapshot.firstPage().getData();

        List<ApplicationDto> secondApplications =
                snapshot.secondPage().getData();

        int total = snapshot.total();

        Set<Long> firstIds =
                getApplicationIds(firstApplications, "первой");

        Set<Long> secondIds =
                getApplicationIds(secondApplications, "второй");

        assertAll(
                "Проверка размеров страниц",

                () -> assertEquals(
                        Math.min(PAGINATION_LIMIT, total),
                        firstApplications.size(),
                        "Размер первой страницы не соответствует total и limit"
                ),

                () -> assertEquals(
                        Math.min(
                                PAGINATION_LIMIT,
                                Math.max(0, total - PAGINATION_LIMIT)
                        ),
                        secondApplications.size(),
                        "Размер второй страницы не соответствует total и limit"
                ),

                () -> assertEquals(
                        firstApplications.size(),
                        firstIds.size(),
                        "На первой странице есть повторяющиеся applicationid"
                ),

                () -> assertEquals(
                        secondApplications.size(),
                        secondIds.size(),
                        "На второй странице есть повторяющиеся applicationid"
                )
        );

        Set<Long> intersection = new HashSet<>(firstIds);
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

    private Set<Long> getApplicationIds(List<ApplicationDto> applications, String pageDescription) {
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

    @Step("Получаем стабильные данные двух страниц заявок")
    private PaginationSnapshot getStablePaginationSnapshot() {
        int lastFirstTotal = -1;
        int lastSecondTotal = -1;
        Set<Long> lastIntersection = Set.of();

        for (int attempt = 1; attempt <= MAX_PAGINATION_ATTEMPTS; attempt++) {
            ApplicationsResponseDto firstPage =
                    ZagsApiSteps.getApplications(1, PAGINATION_LIMIT);

            ApplicationsResponseDto secondPage =
                    ZagsApiSteps.getApplications(2, PAGINATION_LIMIT);

            assertNotNull(firstPage, "Ответ первой страницы отсутствует");
            assertNotNull(secondPage, "Ответ второй страницы отсутствует");

            assertNotNull(firstPage.getData(), "Данные первой страницы отсутствуют");
            assertNotNull(secondPage.getData(), "Данные второй страницы отсутствуют");

            int firstTotal = parseTotal(firstPage.getTotal());
            int secondTotal = parseTotal(secondPage.getTotal());

            lastFirstTotal = firstTotal;
            lastSecondTotal = secondTotal;

            if (firstTotal != secondTotal) {
                continue;
            }

            Set<Long> firstIds =
                    getApplicationIds(firstPage.getData(), "первой");

            Set<Long> secondIds =
                    getApplicationIds(secondPage.getData(), "второй");

            Set<Long> intersection = new HashSet<>(firstIds);
            intersection.retainAll(secondIds);
            lastIntersection = intersection;

            ApplicationsResponseDto confirmedFirstPage =
                    ZagsApiSteps.getApplications(1, PAGINATION_LIMIT);

            assertNotNull(
                    confirmedFirstPage,
                    "Ответ контрольного запроса первой страницы отсутствует"
            );
            assertNotNull(
                    confirmedFirstPage.getData(),
                    "Данные контрольной первой страницы отсутствуют"
            );

            int confirmedTotal = parseTotal(confirmedFirstPage.getTotal());

            Set<Long> confirmedFirstIds =
                    getApplicationIds(
                            confirmedFirstPage.getData(),
                            "контрольной первой"
                    );

            boolean firstPageStable =
                    firstTotal == confirmedTotal
                            && firstIds.equals(confirmedFirstIds);

            if (firstPageStable && intersection.isEmpty()) {
                return new PaginationSnapshot(
                        firstPage,
                        secondPage,
                        firstTotal
                );
            }
        }

        throw new AssertionError(
                "Не удалось получить стабильные страницы за "
                        + MAX_PAGINATION_ATTEMPTS
                        + " попытки. total первой страницы: "
                        + lastFirstTotal
                        + ", total второй страницы: "
                        + lastSecondTotal
                        + ", пересечение ID: "
                        + lastIntersection
        );
    }
}