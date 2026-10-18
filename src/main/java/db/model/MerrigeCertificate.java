package db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="merrigecertificates")
public class MerrigeCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merrigecertificateid")
    private Integer merrigeCertificateId;

    @Column(name = "citizenid")
    private Integer citizenId;

    @Column(name = "dateofmerrige")
    private LocalDate dateOfMerrige;

    @Column(name="surnameofspouse")
    private String surnameOfSpouce;

    @Column(name="newsurnameofspouse")
    private String newSurnameOfSpouce;

    @Column(name="nameofspouse")
    private String nameOfSpouce;

    @Column(name="middlenameofspouse")
    private String middlenameOfSpouce;

    @Column(name="passportnumberofspouse")
    private String passportNumberOfSpouce;

    @Column(name="dateofbirthofspouse")
    private LocalDate dateOfBirthOfSpouce;
}
