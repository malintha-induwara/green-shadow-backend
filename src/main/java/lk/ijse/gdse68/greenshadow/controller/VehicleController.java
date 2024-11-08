package lk.ijse.gdse68.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.dto.VehicleDTO;
import lk.ijse.gdse68.greenshadow.exception.StaffNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.VehicleNotFoundException;
import lk.ijse.gdse68.greenshadow.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Received request to save vehicle: {}", vehicleDTO);

        if (vehicleDTO == null) {
            log.warn("Received null VehicleDTO");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                vehicleService.saveVehicle(vehicleDTO);
                log.info("Vehicle saved successfully: {}", vehicleDTO.getVehicleCode());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (Exception e) {
                log.error("Unexpected error while saving vehicle: {}", vehicleDTO.getVehicleCode(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PutMapping(path = "/{vehicleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable("vehicleId") String vehicleId,@Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Received request to update vehicle: {}", vehicleId);
        if (vehicleId == null) {
            log.warn("Received null vehicleId for update");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                vehicleService.updateVehicle(vehicleId, vehicleDTO);
                log.info("Vehicle updated successfully: {}", vehicleId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (VehicleNotFoundException e) {
                log.warn("Vehicle not found for update: {}", vehicleId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }catch (StaffNotFoundException e){
                log.warn("Staff not found for update: {}", vehicleId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while updating vehicle: {}", vehicleId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> searchVehicle(@PathVariable("vehicleId") String vehicleId) {
        log.info("Received request to search vehicle: {}", vehicleId);
        if (vehicleId == null) {
            log.warn("Received null vehicleId for search");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                VehicleDTO vehicleDTO = vehicleService.searchVehicle(vehicleId);
                log.info("Vehicle found: {}", vehicleId);
                return ResponseEntity.ok(vehicleDTO);
            } catch (VehicleNotFoundException e) {
                log.warn("Vehicle not found: {}", vehicleId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while searching for vehicle: {}", vehicleId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        log.info("Received request to get all vehicles");
        try {
            List<VehicleDTO> allVehicles = vehicleService.getAllVehicles();
            log.info("Retrieved {} vehicles", allVehicles.size());
            return ResponseEntity.ok(allVehicles);
        } catch (Exception e) {
            log.error("Unexpected error while retrieving all vehicles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleId") String vehicleId) {
        log.info("Received request to delete vehicle: {}", vehicleId);
        if (vehicleId == null) {
            log.warn("Received null vehicleId for deletion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                vehicleService.deleteVehicle(vehicleId);
                log.info("Vehicle deleted successfully: {}", vehicleId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (VehicleNotFoundException e) {
                log.warn("Vehicle not found for deletion: {}", vehicleId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while deleting vehicle: {}", vehicleId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}

