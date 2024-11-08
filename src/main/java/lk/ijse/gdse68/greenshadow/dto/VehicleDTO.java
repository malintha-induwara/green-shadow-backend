package lk.ijse.gdse68.greenshadow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleDTO implements Serializable {
    private String vehicleCode;
    @NotBlank(message = "License Plate Number cannot be empty")
    private String licensePlateNumber;
    @NotBlank(message = "Vehicle Category cannot be empty")
    private String vehicleCategory;
    @NotBlank(message = "Fuel Type cannot be empty")
    private String fuelType;
    @NotBlank(message = "Status cannot be empty")
    private String  status;
    private String remarks;
    private String staff;
}

