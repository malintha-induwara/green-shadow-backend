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
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class VehicleController {

    private final VehicleService vehicleService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> saveVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Received request to save vehicle: {}", vehicleDTO.getVehicleCode());
        VehicleDTO savedVehicle = vehicleService.saveVehicle(vehicleDTO);
        log.info("Vehicle saved successfully: {}", savedVehicle.getVehicleCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping(path = "/{vehicleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable("vehicleId") String vehicleId,
                                                    @Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Received request to update vehicle: {}", vehicleId);
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(vehicleId, vehicleDTO);
        log.info("Vehicle updated successfully: {}", vehicleId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedVehicle);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @DeleteMapping(path = "/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleId") String vehicleId) {
        log.info("Received request to delete vehicle: {}", vehicleId);
        vehicleService.deleteVehicle(vehicleId);
        log.info("Vehicle deleted successfully: {}", vehicleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> searchVehicle(@PathVariable("vehicleId") String vehicleId) {
        log.info("Received request to search vehicle: {}", vehicleId);
        VehicleDTO vehicleDTO = vehicleService.searchVehicle(vehicleId);
        log.info("Vehicle found: {}", vehicleId);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        log.info("Received request to get all vehicles");
        List<VehicleDTO> allVehicles = vehicleService.getAllVehicles();
        log.info("Retrieved {} vehicles", allVehicles.size());
        return ResponseEntity.status(HttpStatus.OK).body(allVehicles);
    }
}
