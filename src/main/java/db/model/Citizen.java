package db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "citizens")
@Data
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "citizenid")
    private int citizenid;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "passportnumber")
    private String passportNumber;

    @Column(name="dateofbirth")
    private String dateOfBirth;

    @Column(name="gender")
    private String gender;

    @Column(name="image")
    private String image;

    @Column(name = "registration_address")
    private String registrationAddress;

    public Citizen() {
    }
}
