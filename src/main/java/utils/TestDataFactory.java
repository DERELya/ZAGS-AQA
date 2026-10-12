package utils;

import api.dto.AdminRequestDto;
import api.dto.ApplicationRequestDto;
import model.data.Gender;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public final class TestDataFactory {

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

    private TestDataFactory() {
    }

    public static AdminRequestDto adminRequest() {
        AdminRequestDto admin = new AdminRequestDto();

        admin.setPersonalLastName("Сергеев");
        admin.setPersonalFirstName("Виктор");
        admin.setPersonalMiddleName("Олегович");
        admin.setPersonalPhoneNumber(nextPhone());
        admin.setPersonalNumberOfPassport(nextPassport());
        admin.setDateOfBirth(
                HelperForDate.toIsoDate(LocalDate.of(2005, 11, 12))
        );

        return admin;
    }

    public static ApplicationRequestDto deathApplication() {
        ApplicationRequestDto application = new ApplicationRequestDto();

        application.setMode("death");

        application.setPersonalLastName("Сергеев");
        application.setPersonalFirstName("Олег");
        application.setPersonalMiddleName("Олегович");
        application.setPersonalPhoneNumber(nextPhone());
        application.setPersonalNumberOfPassport(nextPassport());
        application.setPersonalAddress("Брест");

        application.setCitizenLastName("Сергеев");
        application.setCitizenFirstName("Олег");
        application.setCitizenMiddleName("Олегович");
        application.setCitizenGender(Gender.MALE);
        application.setCitizenBirthDate(
                HelperForDate.toIsoDate(LocalDate.of(2005, 11, 12))
        );
        application.setCitizenAddress("Брест");
        application.setCitizenNumberOfPassport(nextPassport());

        application.setDeathPlaceOfDeath("Брест");
        application.setDeathDateOfDeath(
                HelperForDate.toIsoDate(LocalDate.now().minusDays(10))
        );

        return application;
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
