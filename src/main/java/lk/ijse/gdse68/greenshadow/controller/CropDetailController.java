package lk.ijse.gdse68.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.dto.CropDetailDTO;
import lk.ijse.gdse68.greenshadow.service.CropDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cropDetails")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CropDetailController {

    private final CropDetailService cropDetailService;

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDetailDTO<String>> saveCropDetail(@Valid @ModelAttribute CropDetailDTO<MultipartFile> cropDetailDTO) {
        log.info("Received request to save crop detail");
        CropDetailDTO<String> savedCropDetailLog = cropDetailService.saveCropDetail(cropDetailDTO);
        log.info("Crop detail saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCropDetailLog);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(path = "/{logCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDetailDTO<String>> updateCropDetail(
            @PathVariable("logCode") String logCode,
            @Valid @ModelAttribute CropDetailDTO<MultipartFile> cropDetailDTO) {
        log.info("Received request to update crop detail: {}", logCode);
        CropDetailDTO<String> updatedCropDetailLog = cropDetailService.updateCropDetail(logCode, cropDetailDTO);
        log.info("Crop detail updated successfully: {}", logCode);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCropDetailLog);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @DeleteMapping("/{logCode}")
    public ResponseEntity<Void> deleteCropDetail(@PathVariable("logCode") String logCode) {
        log.info("Received request to delete crop detail: {}", logCode);
        cropDetailService.deleteCropDetail(logCode);
        log.info("Crop detail deleted successfully: {}", logCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{logCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDetailDTO<String>> getCropDetail(@PathVariable("logCode") String logCode) {
        log.info("Received request to get crop detail: {}", logCode);
        CropDetailDTO<String> cropDetailDTO = cropDetailService.getCropDetail(logCode);
        log.info("Crop detail found: {}", logCode);
        return ResponseEntity.status(HttpStatus.OK).body(cropDetailDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDetailDTO<String>>> getAllCropDetails() {
        log.info("Received request to get all crop details");
        List<CropDetailDTO<String>> cropDetails = cropDetailService.getAllCropDetails();
        log.info("Retrieved {} crop details", cropDetails.size());
        return ResponseEntity.status(HttpStatus.OK).body(cropDetails);
    }
}