package db.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Applicant {
    private Integer applicantId;

    private String surname;

    private String name;

    private String middleName;

    private String passportNumber;

    private String phoneNumber;

    private String registrationAddress;
    public Applicant() {
    }
}
