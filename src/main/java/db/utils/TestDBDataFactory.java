package db.utils;

import db.model.Admin;
import db.model.Applicant;
import db.model.Citizen;
import db.model.DeathCertificate;
import model.data.Gender;
import utils.HelperForDate;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class TestDBDataFactory {
    private TestDBDataFactory() {
    }

    private static final int PHONE_MIN = 100_000;
    private static final int PHONE_MAX = 999_999;

    private static final int PASSPORT_MIN = 10_000_000;
    private static final int PASSPORT_MAX = 99_999_999;

    private static final AtomicInteger PHONE_SEQUENCE =
            new AtomicInteger(
                    ThreadLocalRandom.current()
                            .nextInt(PHONE_MIN, PHONE_MAX + 1)
            );

    private static final AtomicInteger PASSPORT_SEQUENCE =
            new AtomicInteger(
                    ThreadLocalRandom.current()
                            .nextInt(PASSPORT_MIN, PASSPORT_MAX + 1)
            );

    public static Applicant createApplicant() {


        Applicant applicant = new Applicant();

        applicant.setSurname("Сергеев");
        applicant.setName("Олег");
        applicant.setMiddleName("Олегович");
        applicant.setPhoneNumber(nextPhone());
        applicant.setPassportNumber(nextPassport());
        applicant.setRegistrationAddress("Брест");

        return applicant;
    }

    public static Citizen createCitizen() {


        Citizen citizen = new Citizen();

        citizen.setSurname("Сергеев");
        citizen.setName("Олег");
        citizen.setMiddleName("Олегович");
        citizen.setGender(Gender.MALE.getValue());
        citizen.setDateOfBirth(HelperForDate.toIsoDate(LocalDate.of(2000, 2, 15)));
        citizen.setPassportNumber(nextPassport());
        citizen.setRegistrationAddress("Брест");

        return citizen;
    }

    public static DeathCertificate createDeathCertificate(Integer citizenId) {

        DeathCertificate deathCertificate = new DeathCertificate();

        deathCertificate.setCitizenId(citizenId);
        deathCertificate.setPlaceOfDeath("Брест");
        deathCertificate.setDateOfDeath(HelperForDate.toIsoDate(LocalDate.now().minusDays(2)));

        return deathCertificate;
    }

    public static Admin createAdmin() {

        Admin admin = new Admin();

        admin.setSurname("Петров");
        admin.setName("Виктор");
        admin.setMiddleName("Сергеевич");
        admin.setPhoneNumber(nextPhone());
        admin.setPassportNumber(nextPassport());
        admin.setDateofbirth(LocalDate.of(2000,3,19));

        return admin;
    }

    private static String nextPhone() {
        int suffix = PHONE_SEQUENCE.getAndUpdate(
                current -> current >= PHONE_MAX
                        ? PHONE_MIN
                        : current + 1
        );

        return "37529" + suffix;
    }

    private static String nextPassport() {
        int number = PASSPORT_SEQUENCE.getAndUpdate(
                current -> current >= PASSPORT_MAX
                        ? PASSPORT_MIN
                        : current + 1
        );

        return String.valueOf(number);
    }

}
