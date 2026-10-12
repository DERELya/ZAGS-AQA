package model.data;

public record ApplicantData(
        String lastName,
        String firstName,
        String middleName,
        String phoneNumber,
        String passportNumber,
        String address
) {
}
