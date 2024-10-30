package lk.ijse.gdse68.greenshadow.entity;

import jakarta.persistence.*;
import lk.ijse.gdse68.greenshadow.enums.Gender;
import lk.ijse.gdse68.greenshadow.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Staff {

    @Id
    private String staffId;

    private String firstName;
    private String lastName;
    private String designation;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate joinedDate;
    private LocalDate dob;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String contactNo;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;

    @ManyToMany(mappedBy = "staff")
    private List<Field> fields;

}

