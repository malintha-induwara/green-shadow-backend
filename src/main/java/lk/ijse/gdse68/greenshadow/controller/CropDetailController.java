package lk.ijse.gdse68.greenshadow.controller;

import lk.ijse.gdse68.greenshadow.dto.CropDetailDTO;
import lk.ijse.gdse68.greenshadow.exception.CropDetailNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.CropNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.FieldNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.StaffNotFoundException;
import lk.ijse.gdse68.greenshadow.service.CropDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cropDetails")
@RequiredArgsConstructor
@Slf4j
public class CropDetailController {

    private final CropDetailService cropDetailService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCropDetail(
            @RequestPart("logDetail") String logDetail,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart("fieldCodes") String fieldCodes,
            @RequestPart("cropCodes") String cropCodes,
            @RequestPart("staffIds") String staffIds) {

        log.info("Received request to save crop detail");

        try {
            List<String> fieldCodesList = Arrays.asList(fieldCodes.split(","));
            List<String> cropCodesList = Arrays.asList(cropCodes.split(","));
            List<String> staffIdsList = Arrays.asList(staffIds.split(","));

            CropDetailDTO<MultipartFile> cropDetailDTO = new CropDetailDTO<>();
            cropDetailDTO.setLogDetail(logDetail);
            cropDetailDTO.setObservedImage(observedImage);
            cropDetailDTO.setFieldCodes(fieldCodesList);
            cropDetailDTO.setCropCodes(cropCodesList);
            cropDetailDTO.setStaffIds(staffIdsList);

            cropDetailService.saveCropDetail(cropDetailDTO);
            log.info("Crop detail saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("Failed to save crop detail", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/{logCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCropDetail(
            @PathVariable("logCode") String logCode,
            @RequestPart("logDetail") String logDetail,
            @RequestPart(value = "observedImage", required = false) MultipartFile observedImage,
            @RequestPart("fieldCodes") String fieldCodes,
            @RequestPart("cropCodes") String cropCodes,
            @RequestPart("staffIds") String staffIds) {

        log.info("Received request to update crop detail: {}", logCode);

        try {
            List<String> fieldCodesList = Arrays.asList(fieldCodes.split(","));
            List<String> cropCodesList = Arrays.asList(cropCodes.split(","));
            List<String> staffIdsList = Arrays.asList(staffIds.split(","));

            CropDetailDTO<MultipartFile> cropDetailDTO = new CropDetailDTO<>();
            cropDetailDTO.setLogCode(logCode);
            cropDetailDTO.setLogDetail(logDetail);
            cropDetailDTO.setObservedImage(observedImage);
            cropDetailDTO.setFieldCodes(fieldCodesList);
            cropDetailDTO.setCropCodes(cropCodesList);
            cropDetailDTO.setStaffIds(staffIdsList);

            cropDetailService.updateCropDetail(logCode, cropDetailDTO);
            log.info("Crop detail updated successfully: {}", logCode);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        } catch (Exception e) {
            log.error("Failed to delete crop detail: {}", logCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}