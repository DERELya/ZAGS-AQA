package db;

import db.model.Admin;
import db.repository.AdminRepository;
import db.utils.TestDBDataFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DBAdminTest {
    private Integer createdAdminId;


    private final AdminRepository adminRepository = new AdminRepository();

    @Test
    void createAdminAndVerifyDataInDatabase() {
        Admin expected = TestDBDataFactory.createAdmin();

        createdAdminId = adminRepository.save(expected);

        Admin actual = adminRepository.findById(createdAdminId);

        assertNotNull(actual, "Администратор должен существовать в БД");
        assertAll(
                () -> assertEquals(expected.getSurname(), actual.getSurname()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expected.getPassportNumber(), actual.getPassportNumber()),
                () -> assertEquals(expected.getDateofbirth(), actual.getDateofbirth()),
                () -> assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber())
        );
    }

    @AfterEach
    void cleanUp() {
        if (createdAdminId != null) {
            adminRepository.deleteById(createdAdminId);
        }
    }
}
