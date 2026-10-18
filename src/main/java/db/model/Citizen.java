package db.model;

import jakarta.persistence.*;
import lombok.Data;
import model.data.Gender;

@Data
public class Citizen {
    private Integer citizenId;

    private String surname;

    private String name;

    private String middleName;

    private String passportNumber;

    private String dateOfBirth;

    private Gender gender;

    private String image;

    private String registrationAddress;

    public Citizen() {
    }
}
