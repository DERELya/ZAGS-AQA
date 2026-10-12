package model.data;

public record WeddingData(
        String dateOfRegistration,
        String newLastName,
        String lastNameSpouse,
        String nameSpouse,
        String middleNameSpouse,
        String dateOfBirthSpouse,
        String passportSpouse
) {
}

