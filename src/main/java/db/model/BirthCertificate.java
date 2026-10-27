package db.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "birthcertificates")
@Data
public class BirthCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "birthcertificateid")
    private Integer birthCertificateId;

    @Column(name = "citizenid")
    private Integer citizenId;

    @Column(name = "placeofbirth")
    private String placeOfBirth;

    @Column(name = "mother")
    private String mother;

    @Column(name = "father")
    private String father;

    @Column(name = "grandma")
    private String grandma;

    @Column(name = "grandpa")
    private String grandpa;
}