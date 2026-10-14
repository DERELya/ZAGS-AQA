package db.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "applicants")
@Data
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicantid")
    private Integer applicantId;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "passportnumber")
    private String passportNumber;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "registration_address")
    private String registrationAddress;
    public Applicant() {
    }
}
