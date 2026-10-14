package db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "staff")
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffid;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "dateofbirth")
    private LocalDate dateofbirth;

    @Column(name = "passportnumber")
    private String passportNumber;

    @Column(name = "phonenumber")
    private String phoneNumber;
}
