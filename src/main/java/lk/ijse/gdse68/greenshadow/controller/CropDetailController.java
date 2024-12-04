package lk.ijse.gdse68.greenshadow.controller;

import lk.ijse.gdse68.greenshadow.dto.CropDetailDTO;
import lk.ijse.gdse68.greenshadow.exception.*;
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
@PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CropDetailController {

    private final CropDetailService cropDetailService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDetailDTO<String>> saveCropDetail(
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetail") String logDetail,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart(value = "fieldCodes", required = false) String fieldCodes,
            @RequestPart(value = "cropCodes", required = false) String cropCodes,
            @RequestPart(value = "staffIds", required = false) String staffIds) {
        log.info("Received request to save crop detail");
        try {
            CropDetailDTO<MultipartFile> cropDetailDTO = new CropDetailDTO<>();
            if (staffIds != null) {
                List<String> staffIdsList = Arrays.asList(staffIds.split(","));
                cropDetailDTO.setStaffIds(staffIdsList);
            }

            if (fieldCodes != null) {
                List<String> fieldCodesList = Arrays.asList(fieldCodes.split(","));
                cropDetailDTO.setFieldCodes(fieldCodesList);
            }

            if (cropCodes != null) {
                List<String> cropCodesList = Arrays.asList(cropCodes.split(","));
                cropDetailDTO.setCropCodes(cropCodesList);
            }

            cropDetailDTO.setLogDate(LocalDate.parse(logDate));
            cropDetailDTO.setLogDetail(logDetail);
            cropDetailDTO.setObservedImage(observedImage);

            CropDetailDTO<String> savedCropDetailLog = cropDetailService.saveCropDetail(cropDetailDTO);
            log.info("Crop detail saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCropDetailLog);
        } catch (Exception e) {
            log.error("Failed to save crop detail", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/{logCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDetailDTO<String>> updateCropDetail(
            @PathVariable("logCode") String logCode,
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetail") String logDetail,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart(value = "fieldCodes", required = false) String fieldCodes,
            @RequestPart(value = "cropCodes", required = false) String cropCodes,
            @RequestPart(value = "staffIds", required = false) String staffIds) {
        log.info("Received request to update crop detail: {}", logCode);
        try {
            CropDetailDTO<MultipartFile> cropDetailDTO = new CropDetailDTO<>();
            if (staffIds != null) {
                List<String> staffIdsList = Arrays.asList(staffIds.split(","));
                cropDetailDTO.setStaffIds(staffIdsList);
            }

            if (fieldCodes != null) {
                List<String> fieldCodesList = Arrays.asList(fieldCodes.split(","));
                cropDetailDTO.setFieldCodes(fieldCodesList);
            }

            if (cropCodes != null) {
                List<String> cropCodesList = Arrays.asList(cropCodes.split(","));
                cropDetailDTO.setCropCodes(cropCodesList);
            }

            cropDetailDTO.setLogDate(LocalDate.parse(logDate));
            cropDetailDTO.setLogCode(logCode);
            cropDetailDTO.setLogDetail(logDetail);
            cropDetailDTO.setObservedImage(observedImage);

            CropDetailDTO<String> updatedCropDetailLog = cropDetailService.updateCropDetail(logCode, cropDetailDTO);
            log.info("Crop detail updated successfully: {}", logCode);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCropDetailLog);
        } catch (CropDetailNotFoundException e) {
            log.warn("Crop detail not found: {}", logCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (FieldNotFoundException | CropNotFoundException | StaffNotFoundException e) {
            log.warn("Related entity not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Failed to update crop detail: {}", logCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{logCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropDetailDTO<String>> getCropDetail(@PathVariable("logCode") String logCode) {
        log.info("Received request to get crop detail: {}", logCode);
        try {
            CropDetailDTO<String> cropDetailDTO = cropDetailService.getCropDetail(logCode);
            return ResponseEntity.ok(cropDetailDTO);
        } catch (CropDetailNotFoundException e) {
            log.warn("Crop detail not found: {}", logCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Failed to get crop detail: {}", logCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDetailDTO<String>>> getAllCropDetails() {
        log.info("Received request to get all crop details");
        try {
            List<CropDetailDTO<String>> cropDetails = cropDetailService.getAllCropDetails();
            return ResponseEntity.ok(cropDetails);
        } catch (Exception e) {
            log.error("Failed to get all crop details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{logCode}")
    public ResponseEntity<Void> deleteCropDetail(@PathVariable("logCode") String logCode) {
        log.info("Received request to delete crop detail: {}", logCode);
        try {
            cropDetailService.deleteCropDetail(logCode);
            log.info("Crop detail deleted successfully: {}", logCode);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CropDetailNotFoundException e) {
            log.warn("Crop detail not found: {}", logCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataPersistFailedException e){
            log.warn("Failed to delete crop detail: {}", logCode);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (Exception e) {
            log.error("Failed to delete crop detail: {}", logCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}