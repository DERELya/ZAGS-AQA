package model.data;

public record CitizenData(
        String lastName,
        String firstName,
        String middleName,
        String dateOfBirth,
        String passportNumber,
        String gender,
        String address
) {
}
