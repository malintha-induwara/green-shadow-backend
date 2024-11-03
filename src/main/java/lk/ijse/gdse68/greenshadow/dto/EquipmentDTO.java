package lk.ijse.gdse68.greenshadow.dto;

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
    private String name;
    private EquipmentType equipmentType;
    private EquipmentStatus status;
    private String staff;
    private String field;
}

