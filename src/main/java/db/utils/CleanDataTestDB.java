package db.utils;

import db.dao.ApplicantDAO;
import db.dao.ApplicationDAO;
import db.dao.CitizenDAO;
import db.hibernate.dao.DeathCertificateDAO;
import io.qameta.allure.Step;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public final  class CleanDataTestDB {
    private static final ApplicantDAO applicantDAO = new ApplicantDAO();
    private static final ApplicationDAO applicationDAO = new ApplicationDAO();
    private static final CitizenDAO citizenDAO = new CitizenDAO();

    private CleanDataTestDB() {}

    @Step("Удаление тестовых данных")
    public static void cleanUp(
            Integer applicantId,
            Integer citizenId,
            String certificateName,
            Executable deleteCertificate
            ) {

        assertAll(
                "Очистка тестовых данных",

                () -> assertDoesNotThrow(deleteCertificate,"Не удалось удалить " + certificateName),

                () -> {
                    if (citizenId != null) {
                        assertDoesNotThrow(
                                () -> applicationDAO.deleteByCitizenId(citizenId),
                                "Не удалось удалить заявку"
                        );
                    }
                },

                () -> {
                    if (applicantId != null) {
                        assertDoesNotThrow(
                                () -> applicantDAO.deleteById(applicantId),
                                "Не удалось удалить заявителя"
                        );
                    }
                },

                () -> {
                    if (citizenId != null) {
                        assertDoesNotThrow(
                                () -> citizenDAO.deleteById(citizenId),
                                "Не удалось удалить гражданина"
                        );
                    }
                }
        );
    }

}
