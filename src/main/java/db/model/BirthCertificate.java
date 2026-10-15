package db.model;

import lombok.Data;

@Data
public class BirthCertificate {
    private Integer birthCertificateId;
    private Integer citizenId;
    private String placeOfBirth;
    private String mother;
    private String father;
    private String grandma;
    private String grandpa;
}