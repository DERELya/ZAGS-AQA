package api;

import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import model.dto.*;
import model.specs.RequestSpecs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @Description(
            "Создаёт администратора и заявку на регистрацию смерти, проверяет одобрение заявки через POST /requestProcess"
    )
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
                        .statusCode(200)
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

    @BeforeAll
    static void setUp() {
        AdminRequestDto admin = new AdminRequestDto();

        admin.setPersonalLastName("Сергеев");
        admin.setPersonalFirstName("Виктор");
        admin.setPersonalMiddleName("Олегович");
        admin.setPersonalPhoneNumber("37526785632");
        admin.setPersonalNumberOfPassport("12378612");
        admin.setDateOfBirth("2005-11-12");

        ApplicationRequestDto application = new ApplicationRequestDto();
        application.setMode("death");
        application.setPersonalLastName("Сергеев");

        application.setPersonalFirstName("Олег");
        application.setPersonalMiddleName("Олегович");
        application.setPersonalPhoneNumber("37526785632");
        application.setPersonalNumberOfPassport("12378612");
        application.setPersonalAddress("Брест");
        application.setCitizenLastName("Сергеев");
        application.setCitizenFirstName("Олег");
        application.setCitizenMiddleName("Олегович");
        application.setCitizenGender("Муж");
        application.setCitizenBirthDate("2005-11-12");
        application.setCitizenAddress("Брест");
        application.setCitizenNumberOfPassport("32785631");
        application.setDeath_placeOfDeath("Брест");
        application.setDeath_dateOfDeath(LocalDate.now().minusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));


        staffId = createAdministrator(admin);
        applId = createApplication(application);

    }

    @Step("Создаем администратора и сохраняем его ID")
    public static long createAdministrator(AdminRequestDto requestDto) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .body(requestDto)
                .when()
                .post("/sendAdminRequest")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getLong("data.staffid");
    }

    @Step("Создаем заявку и сохраняем ее ID")
    public static long createApplication(ApplicationRequestDto requestDto) {
        return given()
                .spec(RequestSpecs.baseRequestSpec())
                .body(requestDto)
                .when()
                .post("/sendUserRequest")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getLong("data.applicationid");
    }
}
