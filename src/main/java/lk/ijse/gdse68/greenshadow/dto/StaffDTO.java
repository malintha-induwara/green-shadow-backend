package lk.ijse.gdse68.greenshadow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "First Name is required")
    @Size(min = 3, max = 50, message = "Fist Name must be between 3 and 50 characters")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    @Size(min = 3, max = 50, message = "Last Name must be between 3 and 50 characters")
    private String lastName;
    @NotBlank(message = "Designation is required")
    @Size(min = 3, max = 150, message = "Designation must be between 3 and 150 characters")
    private String designation;
    @NotNull(message = "gender is required")
    private Gender gender;
    @NotNull(message = "Joined Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinedDate;
    @NotNull(message = "Date of Birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @NotBlank(message = "Address Line 01 is required")
    @Size(min = 3, max = 50, message = "Address must be between 3 and 50 characters")
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    @NotBlank(message = "Contact No is required")
    @Size(min = 10, max = 10, message = "Contact No must be 10 characters")
    private String contactNo;
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email should be valid")
    private String email;
    @NotNull(message = "Role is required")
    private Role role;
    private List<String> vehicles;
    private List<String> fields;
}

