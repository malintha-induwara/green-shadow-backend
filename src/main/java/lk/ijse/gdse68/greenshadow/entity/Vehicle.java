package lk.ijse.gdse68.greenshadow.entity;

import jakarta.persistence.*;
import lk.ijse.gdse68.greenshadow.enums.VehicleStatus;
import lk.ijse.gdse68.greenshadow.util.CustomGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Vehicle {

    @Id
    @CustomGenerator(prefix ="CAR-" )
    private String vehicleCode;
    private String licensePlateNumber;
    private String vehicleCategory;
    private String fuelType;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "staffId", referencedColumnName = "staffId")
    private Staff staff;
}

