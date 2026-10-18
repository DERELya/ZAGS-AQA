package db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Admin {

    private Integer staffId;

    private String surname;

    private String name;

    private String middleName;

    private LocalDate dateOfBirth;

    private String passportNumber;

    private String phoneNumber;
}
