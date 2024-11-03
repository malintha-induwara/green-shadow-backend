package lk.ijse.gdse68.greenshadow.dto;

import lk.ijse.gdse68.greenshadow.enums.VehicleStatus;
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
    private String licensePlateNumber;
    private String vehicleCategory;
    private String fuelType;
    private VehicleStatus status;
    private String remarks;
    private String staff;
}

