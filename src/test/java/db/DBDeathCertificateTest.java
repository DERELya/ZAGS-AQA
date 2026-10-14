package db;

import db.model.Citizen;
import db.model.DeathCertificate;
import db.repository.CitizenRepository;
import db.repository.DeathCertificateRepository;
import db.utils.TestDBDataFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DBDeathCertificateTest {

    private Integer createdDeathCertificateId;
    private static Integer createdCitizenId;


    private final DeathCertificateRepository deathCertificateRepository =
            new DeathCertificateRepository();

    private static final CitizenRepository citizenRepository =
            new CitizenRepository();


    @Test
    void createDeathCertificateAndVerifyDataInDatabase() {
        DeathCertificate expected = TestDBDataFactory.createDeathCertificate(createdCitizenId);

        createdDeathCertificateId = deathCertificateRepository.save(expected);

        DeathCertificate actual = deathCertificateRepository.findById(createdDeathCertificateId);

        assertNotNull(actual, "Заявитель должен существовать в БД");
        assertAll(
                () -> assertEquals(expected.getCitizenId(), actual.getCitizenId()),
                () -> assertEquals(expected.getDeathcertificateid(), actual.getDeathcertificateid()),
                () -> assertEquals(expected.getDateOfDeath(), actual.getDateOfDeath()),
                () -> assertEquals(expected.getPlaceOfDeath(), actual.getPlaceOfDeath())
        );
    }



    @BeforeAll
    static void createCitizen() {
        Citizen citizen=TestDBDataFactory.createCitizen();
        createdCitizenId = citizenRepository.save(citizen);
    }
    @AfterAll
    static void cleanUpCitizen() {
        if (createdCitizenId != null) {
            citizenRepository.deleteById(createdCitizenId);
        }
    }

    @AfterEach
    void cleanUp() {
        if (createdDeathCertificateId != null) {
            deathCertificateRepository.deleteById(createdDeathCertificateId);
        }
    }
}
