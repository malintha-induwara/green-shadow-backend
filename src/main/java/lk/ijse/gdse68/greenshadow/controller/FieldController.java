package lk.ijse.gdse68.greenshadow.controller;


import lk.ijse.gdse68.greenshadow.dto.FieldDTO;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.FieldNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.InvalidImageTypeException;
import lk.ijse.gdse68.greenshadow.service.FieldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/field")
@RequiredArgsConstructor
@Slf4j
public class FieldController {


    private final FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(@RequestPart("fieldName") String fieldName,
                                          @RequestPart("latitude") String latitude,
                                          @RequestPart("longitude") String longitude,
                                          @RequestPart("extentSize") String extentSize,
                                          @RequestPart("fieldImage1") MultipartFile fieldImage1,
                                          @RequestPart("fieldImage2") MultipartFile fieldImage2) {
        log.info("Received request to save field: {}", fieldName);
        try {
            FieldDTO<MultipartFile> fieldDTO = new FieldDTO<>();
            Point fieldLocation = new Point(Double.parseDouble(latitude), Double.parseDouble(longitude));

            fieldDTO.setFieldName(fieldName);
            fieldDTO.setFieldLocation(fieldLocation);
            fieldDTO.setExtentSize(Double.parseDouble(extentSize));
            fieldDTO.setFieldImage1(fieldImage1);
            fieldDTO.setFieldImage2(fieldImage2);

            fieldService.saveField(fieldDTO);
            log.info("Field saved successfully: {}", fieldName);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataPersistFailedException e) {
            log.error("Failed to save field: {}", fieldName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (InvalidImageTypeException e) {
            log.warn("Invalid image type for field: {}", fieldName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Unexpected error while saving field: {}", fieldName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("fieldCode") String fieldCode,
                                            @RequestPart("fieldName") String fieldName,
                                            @RequestPart("latitude") String latitude,
                                            @RequestPart("longitude") String longitude,
                                            @RequestPart("extentSize") String extentSize,
                                            @RequestPart("fieldImage1") MultipartFile fieldImage1,
                                            @RequestPart("fieldImage2") MultipartFile fieldImage2) {
        log.info("Received request to update field: {}", fieldCode);

        if (fieldCode == null) {
            log.warn("Received null fieldCode for update");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                Point fieldLocation = new Point(Double.parseDouble(latitude), Double.parseDouble(longitude));
                FieldDTO<MultipartFile> fieldDTO = new FieldDTO<>();
                fieldDTO.setFieldCode(fieldCode);
                fieldDTO.setFieldName(fieldName);
                fieldDTO.setFieldLocation(fieldLocation);
                fieldDTO.setExtentSize(Double.parseDouble(extentSize));
                fieldDTO.setFieldImage1(fieldImage1);
                fieldDTO.setFieldImage2(fieldImage2);

                fieldService.updateField(fieldCode, fieldDTO);
                log.info("Field updated successfully: {}", fieldCode);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (FieldNotFoundException e) {
                log.warn("Field not found for update: {}", fieldCode);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while updating field: {}", fieldCode, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldDTO<String>> getField(@PathVariable("fieldCode") String fieldCode) {
        log.info("Received request to get field: {}", fieldCode);

        if (fieldCode == null) {
            log.warn("Received null fieldCode for search");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                FieldDTO<String> fieldDTO = fieldService.searchField(fieldCode);
                log.info("Field found: {}", fieldCode);
                return ResponseEntity.ok(fieldDTO);
            } catch (FieldNotFoundException e) {
                log.warn("Field not found: {}", fieldCode);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while searching for field: {}", fieldCode, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldDTO<String>>> getAllFields() {
        log.info("Received request to get all fields");

        try {
            List<FieldDTO<String>> allFields = fieldService.getAllFields();
            log.info("Retrieved {} fields", allFields.size());
            return ResponseEntity.ok(allFields);
        } catch (Exception e) {
            log.error("Unexpected error while retrieving all fields", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode) {
        log.info("Received request to delete field: {}", fieldCode);

        if (fieldCode == null) {
            log.warn("Received null fieldCode for deletion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                fieldService.deleteField(fieldCode);
                log.info("Field deleted successfully: {}", fieldCode);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (FieldNotFoundException e) {
                log.warn("Field not found for deletion: {}", fieldCode);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                log.error("Unexpected error while deleting field: {}", fieldCode, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }


}

