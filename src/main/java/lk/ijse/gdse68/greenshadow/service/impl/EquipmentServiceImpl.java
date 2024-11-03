package lk.ijse.gdse68.greenshadow.service.impl;

import lk.ijse.gdse68.greenshadow.dto.EquipmentDTO;
import lk.ijse.gdse68.greenshadow.entity.Equipment;
import lk.ijse.gdse68.greenshadow.entity.Field;
import lk.ijse.gdse68.greenshadow.entity.Staff;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.EquipmentNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.FieldNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.StaffNotFoundException;
import lk.ijse.gdse68.greenshadow.repository.EquipmentRepository;
import lk.ijse.gdse68.greenshadow.repository.FieldRepository;
import lk.ijse.gdse68.greenshadow.repository.StaffRepository;
import lk.ijse.gdse68.greenshadow.service.EquipmentService;
import lk.ijse.gdse68.greenshadow.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    private final StaffRepository staffRepository;

    private final FieldRepository fieldRepository;

    private final Mapper mapper;


    @Override
    @Transactional
    public void saveEquipment(EquipmentDTO equipmentDTO) {

        Equipment tempEquipment = mapper.convertToEquipmentEntity(equipmentDTO);

        if (equipmentDTO.getStaff() != null) {
            Optional<Staff> tempStaff = staffRepository.findById(equipmentDTO.getStaff());
            if (tempStaff.isPresent()) {
                tempEquipment.setStaff(tempStaff.get());
            } else {
                throw new StaffNotFoundException("Staff not found");
            }
        }

        if (equipmentDTO.getField() != null) {
            Optional<Field> tempField = fieldRepository.findById(equipmentDTO.getField());
            if (tempField.isPresent()) {
                tempEquipment.setField(tempField.get());
            } else {
                throw new FieldNotFoundException("Field not found");
            }
        }

        try {
            equipmentRepository.save(tempEquipment);
        } catch (Exception e) {
            throw new DataPersistFailedException("Failed to save the equipment");
        }
    }

    @Override
    @Transactional
    public void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO) {
        Optional<Equipment> tempEquipment = equipmentRepository.findById(equipmentId);
        if (tempEquipment.isPresent()){

            tempEquipment.get().setEquipmentType(equipmentDTO.getEquipmentType());
            tempEquipment.get().setName(equipmentDTO.getName());
            tempEquipment.get().setStatus(equipmentDTO.getStatus());

            if (equipmentDTO.getStaff() != null) {
                Optional<Staff> tempStaff = staffRepository.findById(equipmentDTO.getStaff());
                if (tempStaff.isPresent()) {
                    tempEquipment.get().setStaff(tempStaff.get());
                } else {
                    throw new StaffNotFoundException("Staff not found");
                }
            } else {
                tempEquipment.get().setStaff(null);
            }

            if (equipmentDTO.getField() != null) {
                Optional<Field> tempField = fieldRepository.findById(equipmentDTO.getField());
                if (tempField.isPresent()) {
                    tempEquipment.get().setField(tempField.get());
                } else {
                    throw new FieldNotFoundException("Field not found");
                }
            } else {
                tempEquipment.get().setField(null);
            }
        }

    }

    @Override
    public void deleteEquipment(String equipmentId) {
        if (equipmentRepository.existsById(equipmentId)) {
            equipmentRepository.deleteById(equipmentId);
        } else {
            throw new EquipmentNotFoundException("Equipment not found");
        }
    }

    @Override
    public EquipmentDTO searchEquipment(String equipmentId) {
        if (equipmentRepository.existsById(equipmentId)) {
            return mapper.convertToEquipmentDTO(equipmentRepository.getReferenceById(equipmentId));
        }else {
          throw new EquipmentNotFoundException("Equipment not found");
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        List<Equipment> equipments = equipmentRepository.findAll();
        return mapper.convertToEquipmentDTOList(equipments);
    }
}

