package lk.ijse.gdse68.greenshadow.controller;


import lk.ijse.gdse68.greenshadow.dto.StaffDTO;
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
@PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
@RequiredArgsConstructor
@Slf4j
public class StaffController {

    private final StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO) {
        log.info("Received request to save staff: {}", staffDTO);

        if (staffDTO == null) {
            log.warn("Received null StaffDTO");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                staffService.saveStaff(staffDTO);
                log.info("Staff saved successfully: {}", staffDTO.getStaffId());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (Exception e) {
                log.error("Unexpected error while saving staff: {}", staffDTO.getStaffId(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PutMapping(path = "/{staffId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable("staffId") String staffId, @RequestBody StaffDTO staffDTO) {
        log.info("Received request to update staff: {}", staffId);
        if (staffId == null) {
            log.warn("Received null staffId for update");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                staffService.updateStaff(staffId, staffDTO);
                log.info("Staff updated successfully: {}", staffId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (StaffNotFoundException e) {
                log.warn("Staff not found for update: {}", staffId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while updating staff: {}", staffId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping(value = "/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaffDTO> searchStaff(@PathVariable("staffId") String staffId) {
        log.info("Received request to search staff: {}", staffId);
        if (staffId == null) {
            log.warn("Received null staffId for search");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                StaffDTO staffDTO = staffService.searchStaff(staffId);
                log.info("Staff found: {}", staffId);
                return ResponseEntity.ok(staffDTO);
            } catch (StaffNotFoundException e) {
                log.warn("Staff not found: {}", staffId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while searching for staff: {}", staffId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        log.info("Received request to get all staff");
        try {
            List<StaffDTO> allStaff = staffService.getAllStaff();
            log.info("Retrieved {} staff members", allStaff.size());
            return ResponseEntity.ok(allStaff);
        } catch (Exception e) {
            log.error("Unexpected error while retrieving all staff", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{staffId}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("staffId") String staffId) {
        log.info("Received request to delete staff: {}", staffId);
        if (staffId == null) {
            log.warn("Received null staffId for deletion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                staffService.deleteStaff(staffId);
                log.info("Staff deleted successfully: {}", staffId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (StaffNotFoundException e) {
                log.warn("Staff not found for deletion: {}", staffId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while deleting staff: {}", staffId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}

