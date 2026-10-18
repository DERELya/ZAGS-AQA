package db.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "deathcertificates")
public class DeathCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="deathcertificateid")
    private Integer deathCertificateId;

    @Column(name = "citizenid")
    private Integer citizenId;

    @Column(name = "dateofdeath")
    private String dateOfDeath;

    @Column(name = "placeofdeath")
    private String placeOfDeath;

    public DeathCertificate() {
    }
}
