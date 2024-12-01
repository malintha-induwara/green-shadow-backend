package lk.ijse.gdse68.greenshadow.controller;

import lk.ijse.gdse68.greenshadow.dto.CropDTO;
import lk.ijse.gdse68.greenshadow.exception.CropNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.FieldNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.InvalidImageTypeException;
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
@PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CropController {

    private final CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDTO<String>> saveCrop(@RequestPart("cropCommonName") String cropCommonName,
                                                    @RequestPart("cropScientificName") String cropScientificName,
                                                    @RequestPart("image") MultipartFile image,
                                                    @RequestPart("category") String category,
                                                    @RequestPart("cropSeason") String cropSeason,
                                                    @RequestPart(value = "field", required = false) String field) {
        log.info("Received request to save crop: {}", cropCommonName);
        try {
            CropDTO<MultipartFile> cropDTO = new CropDTO<>(null, cropCommonName, cropScientificName, image, category, cropSeason, field);
            CropDTO<String> savedCrop = cropService.saveCrop(cropDTO);
            log.info("Crop saved successfully: {}", cropCommonName);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCrop);
        } catch (FieldNotFoundException e) {
            log.warn("Field not found for crop: {}", field);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataPersistFailedException e) {
            log.error("Failed to save crop: {}", cropCommonName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (InvalidImageTypeException e) {
            log.warn("Invalid image type for crop: {}", cropCommonName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Unexpected error while saving crop: {}", cropCommonName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/{cropCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDTO<String>> updateCrop(@PathVariable("cropCode") String cropCode,
                                                      @RequestPart("cropCommonName") String cropCommonName,
                                                      @RequestPart("cropScientificName") String cropScientificName,
                                                      @RequestPart("image") MultipartFile image,
                                                      @RequestPart("category") String category,
                                                      @RequestPart("cropSeason") String cropSeason,
                                                      @RequestPart(value = "field", required = false) String field) {
        log.info("Received request to update crop: {}", cropCode);
        try {
            CropDTO<MultipartFile> cropDTO = new CropDTO<>(cropCode, cropCommonName, cropScientificName, image, category, cropSeason, field);
            CropDTO<String> updatedCropDTO = cropService.updateCrop(cropCode, cropDTO);
            log.info("Crop updated successfully: {}", cropCode);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCropDTO);
        } catch (CropNotFoundException e) {
            log.warn("Crop not found for update: {}", cropCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Unexpected error while updating crop: {}", cropCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDTO<String>> searchCrop(@PathVariable("cropId") String cropId) {
        log.info("Received request to search crop: {}", cropId);
        try {
            CropDTO<String> cropDTO = cropService.searchCrop(cropId);
            log.info("Crop found: {}", cropId);
            return ResponseEntity.ok(cropDTO);
        } catch (CropNotFoundException e) {
            log.warn("Crop not found: {}", cropId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Unexpected error while searching for crop: {}", cropId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDTO<String>>> getAllCrops() {
        log.info("Received request to get all crops");

        try {
            List<CropDTO<String>> allCrops = cropService.getAllCrops();
            log.info("Retrieved {} crops", allCrops.size());
            return ResponseEntity.ok(allCrops);
        } catch (Exception e) {
            log.error("Unexpected error while retrieving all crops", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropId") String cropId) {
        log.info("Received request to delete crop: {}", cropId);
        try {
            cropService.deleteCrop(cropId);
            log.info("Crop deleted successfully: {}", cropId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CropNotFoundException e) {
            log.warn("Crop not found for deletion: {}", cropId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Unexpected error while deleting crop: {}", cropId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

