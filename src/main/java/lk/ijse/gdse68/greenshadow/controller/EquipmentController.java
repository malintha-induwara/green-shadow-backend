package lk.ijse.gdse68.greenshadow.controller;

import lk.ijse.gdse68.greenshadow.dto.EquipmentDTO;
import lk.ijse.gdse68.greenshadow.exception.EquipmentNotFoundException;
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
@PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
@RequiredArgsConstructor
@Slf4j
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        log.info("Received request to save equipment: {}", equipmentDTO);

        if (equipmentDTO == null) {
            log.warn("Received null EquipmentDTO");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                equipmentService.saveEquipment(equipmentDTO);
                log.info("Equipment saved successfully: {}", equipmentDTO.getEquipmentId());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (Exception e) {
                log.error("Unexpected error while saving equipment: {}", equipmentDTO.getEquipmentId(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }


    @PutMapping(path = "/{equipmentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable("equipmentId") String equipmentId, @RequestBody EquipmentDTO equipmentDTO) {
        log.info("Received request to update equipment: {}", equipmentId);
        if (equipmentId == null) {
            log.warn("Received null equipmentId for update");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                equipmentService.updateEquipment(equipmentId, equipmentDTO);
                log.info("Equipment updated successfully: {}", equipmentId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (EquipmentNotFoundException e) {
                log.warn("Equipment not found for update: {}", equipmentId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while updating equipment: {}", equipmentId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }


    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentDTO> searchEquipment(@PathVariable("equipmentId") String equipmentId) {
        log.info("Received request to search equipment: {}", equipmentId);
        if (equipmentId == null) {
            log.warn("Received null equipmentId for search");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                EquipmentDTO equipmentDTO = equipmentService.searchEquipment(equipmentId);
                log.info("Equipment found: {}", equipmentId);
                return ResponseEntity.ok(equipmentDTO);
            } catch (EquipmentNotFoundException e) {
                log.warn("Equipment not found: {}", equipmentId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while searching for equipment: {}", equipmentId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
        log.info("Received request to get all equipments");
        try {
            List<EquipmentDTO> allEquipments = equipmentService.getAllEquipment();
            log.info("Retrieved {} equipments", allEquipments.size());
            return ResponseEntity.ok(allEquipments);
        } catch (Exception e) {
            log.error("Unexpected error while retrieving all equipments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping(path = "/{equipmentId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {
        log.info("Received request to delete equipment: {}", equipmentId);
        if (equipmentId == null) {
            log.warn("Received null equipmentId for deletion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                equipmentService.deleteEquipment(equipmentId);
                log.info("Equipment deleted successfully: {}", equipmentId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (EquipmentNotFoundException e) {
                log.warn("Equipment not found for deletion: {}", equipmentId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while deleting equipment: {}", equipmentId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}

