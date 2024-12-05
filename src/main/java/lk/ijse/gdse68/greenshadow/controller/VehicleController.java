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
@CrossOrigin("*")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> saveVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Received request to save vehicle: {}", vehicleDTO);
        try {
            VehicleDTO savedVehicle = vehicleService.saveVehicle(vehicleDTO);
            log.info("Vehicle saved successfully: {}", savedVehicle.getVehicleCode());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
        } catch (Exception e) {
            log.error("Unexpected error while saving vehicle: {}", vehicleDTO.getVehicleCode(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/{vehicleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable("vehicleId") String vehicleId, @Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Received request to update vehicle: {}", vehicleId);
        try {
            VehicleDTO updatedVehicle = vehicleService.updateVehicle(vehicleId, vehicleDTO);
            log.info("Vehicle updated successfully: {}", vehicleId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedVehicle);
        } catch (VehicleNotFoundException e) {
            log.warn("Vehicle not found for update: {}", vehicleId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (StaffNotFoundException e) {
            log.warn("Staff not found for update: {}", vehicleId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Unexpected error while updating vehicle: {}", vehicleId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> searchVehicle(@PathVariable("vehicleId") String vehicleId) {
        log.info("Received request to search vehicle: {}", vehicleId);
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

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE','SCIENTIST')")
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

