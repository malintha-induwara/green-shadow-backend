package lk.ijse.gdse68.greenshadow.entity;

import jakarta.persistence.*;
import lk.ijse.gdse68.greenshadow.enums.EquipmentStatus;
import lk.ijse.gdse68.greenshadow.enums.EquipmentType;
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
public class Equipment {

    @Id
    @CustomGenerator(prefix ="EQUIPMENT-" )
    private String equipmentId;
    private String name;

    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    @ManyToOne
    @JoinColumn(name = "staffId")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "fieldCode")
    private Field field;

}

