package lk.ijse.gdse68.greenshadow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.ijse.gdse68.greenshadow.enums.EquipmentStatus;
import lk.ijse.gdse68.greenshadow.enums.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EquipmentDTO implements Serializable {
    private String equipmentId;
    @NotBlank(message = "Equipment Name is required")
    private String name;
    @NotNull(message = "Equipment Type is required")
    private EquipmentType equipmentType;
    @NotNull(message = "Equipment Status is required")
    private EquipmentStatus status;
    private String staff;
    private String field;
}

