package lk.ijse.gdse68.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.dto.CropDTO;
import lk.ijse.gdse68.greenshadow.service.CropService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/crop")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CropController {

    private final CropService cropService;

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDTO<String>> saveCrop(@Valid @ModelAttribute CropDTO<MultipartFile> cropDTO) {
        log.info("Received request to save crop: {}", cropDTO.getCropCommonName());
        CropDTO<String> savedCrop = cropService.saveCrop(cropDTO);
        log.info("Crop saved successfully: {}", cropDTO.getCropCommonName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCrop);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(path = "/{cropCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDTO<String>> updateCrop(@PathVariable("cropCode") String cropCode, @Valid @ModelAttribute CropDTO<MultipartFile> cropDTO) {
        log.info("Received request to update crop: {}", cropCode);
        CropDTO<String> updatedCropDTO = cropService.updateCrop(cropCode, cropDTO);
        log.info("Crop updated successfully: {}", cropCode);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCropDTO);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @DeleteMapping(path = "/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropId") String cropId) {
        log.info("Received request to delete crop: {}", cropId);
        cropService.deleteCrop(cropId);
        log.info("Crop deleted successfully: {}", cropId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDTO<String>> searchCrop(@PathVariable("cropId") String cropId) {
        log.info("Received request to search crop: {}", cropId);
        CropDTO<String> cropDTO = cropService.searchCrop(cropId);
        log.info("Crop found: {}", cropId);
        return ResponseEntity.status(HttpStatus.OK).body(cropDTO);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE','SCIENTIST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDTO<String>>> getAllCrops() {
        log.info("Received request to get all crops");
        List<CropDTO<String>> allCrops = cropService.getAllCrops();
        log.info("Retrieved {} crops", allCrops.size());
        return ResponseEntity.status(HttpStatus.OK).body(allCrops);
    }
}

