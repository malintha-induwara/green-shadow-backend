package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);

    void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO);

    void deleteEquipment(String equipmentId);

    EquipmentDTO searchEquipment(String equipmentId);

    List<EquipmentDTO> getAllEquipment();
}
