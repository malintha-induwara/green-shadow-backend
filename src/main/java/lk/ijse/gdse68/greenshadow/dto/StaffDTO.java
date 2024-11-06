package lk.ijse.gdse68.greenshadow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.gdse68.greenshadow.enums.Gender;
import lk.ijse.gdse68.greenshadow.enums.Role;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffDTO implements Serializable {
    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinedDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String contactNo;
    private String email;
    private Role role;
    private List<String> vehicles;
    private List<String> fields;
}

