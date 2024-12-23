package lk.ijse.gdse68.greenshadow.controller;


import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.dto.StaffDTO;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.StaffNotFoundException;
import lk.ijse.gdse68.greenshadow.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class StaffController {

    private final StaffService staffService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaffDTO> saveStaff(@Valid @RequestBody StaffDTO staffDTO) {
        log.info("Received request to save staff: {}", staffDTO.getFirstName());
        StaffDTO savedStaff = staffService.saveStaff(staffDTO);
        log.info("Staff saved successfully: {}", savedStaff.getStaffId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStaff);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping(path = "/{staffId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable("staffId") String staffId, @Valid @RequestBody StaffDTO staffDTO) {
        log.info("Received request to update staff: {}", staffId);
        StaffDTO updatedStaff = staffService.updateStaff(staffId, staffDTO);
        log.info("Staff updated successfully: {}", staffId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStaff);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @DeleteMapping(path = "/{staffId}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("staffId") String staffId) {
        log.info("Received request to delete staff: {}", staffId);
        staffService.deleteStaff(staffId);
        log.info("Staff deleted successfully: {}", staffId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaffDTO> searchStaff(@PathVariable("staffId") String staffId) {
        log.info("Received request to search staff: {}", staffId);
        StaffDTO staffDTO = staffService.searchStaff(staffId);
        log.info("Staff found: {}", staffId);
        return ResponseEntity.status(HttpStatus.OK).body(staffDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        log.info("Received request to get all staff");
        List<StaffDTO> allStaff = staffService.getAllStaff();
        log.info("Retrieved {} staff members", allStaff.size());
        return ResponseEntity.status(HttpStatus.OK).body(allStaff);
    }
}
