package lk.ijse.gdse68.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.dto.EquipmentDTO;
import lk.ijse.gdse68.greenshadow.exception.EquipmentNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.FieldNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.StaffNotFoundException;
import lk.ijse.gdse68.greenshadow.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/equipment")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentDTO> saveEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        log.info("Received request to save equipment: {}", equipmentDTO.getName());
        EquipmentDTO savedEquipment = equipmentService.saveEquipment(equipmentDTO);
        log.info("Equipment saved successfully: {}", equipmentDTO.getEquipmentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipment);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping(path = "/{equipmentId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentDTO> updateEquipment(@PathVariable("equipmentId") String equipmentId, @Valid @RequestBody EquipmentDTO equipmentDTO) {
        log.info("Received request to update equipment: {}", equipmentId);
        EquipmentDTO updatedEquipment = equipmentService.updateEquipment(equipmentId, equipmentDTO);
        log.info("Equipment updated successfully: {}", equipmentId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEquipment);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @DeleteMapping(path = "/{equipmentId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {
        log.info("Received request to delete equipment: {}", equipmentId);
        equipmentService.deleteEquipment(equipmentId);
        log.info("Equipment deleted successfully: {}", equipmentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentDTO> searchEquipment(@PathVariable("equipmentId") String equipmentId) {
        log.info("Received request to search equipment: {}", equipmentId);
        EquipmentDTO equipmentDTO = equipmentService.searchEquipment(equipmentId);
        log.info("Equipment found: {}", equipmentId);
        return ResponseEntity.status(HttpStatus.OK).body(equipmentDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
        log.info("Received request to get all equipments");
        List<EquipmentDTO> allEquipments = equipmentService.getAllEquipment();
        log.info("Retrieved {} equipments", allEquipments.size());
        return ResponseEntity.status(HttpStatus.OK).body(allEquipments);
    }
}

