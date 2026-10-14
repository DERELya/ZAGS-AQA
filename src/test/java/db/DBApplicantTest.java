package db;

import db.model.Applicant;
import db.repository.ApplicantRepository;
import db.utils.TestDBDataFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DBApplicantTest {
    private Integer createdApplicantId;


    private final ApplicantRepository applicantRepository =
            new ApplicantRepository();


    @Test
    void createApplicantAndVerifyDataInDatabase() {
        Applicant expected = TestDBDataFactory.createApplicant();

        createdApplicantId = applicantRepository.save(expected);

        Applicant actual = applicantRepository.findById(createdApplicantId);

        assertNotNull(actual, "Заявитель должен существовать в БД");

        assertAll(
                () -> assertEquals(expected.getSurname(), actual.getSurname()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expected.getPassportNumber(), actual.getPassportNumber()),
                () -> assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber()),
                () -> assertEquals(expected.getRegistrationAddress(),actual.getRegistrationAddress())
        );
    }


    @AfterEach
    void cleanUp() {
        if (createdApplicantId != null) {
            applicantRepository.deleteById(createdApplicantId);
        }
    }

}
