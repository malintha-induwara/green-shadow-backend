package lk.ijse.gdse68.greenshadow.controller;


import jakarta.validation.Valid;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/field")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class FieldController {

    private final FieldService fieldService;

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FieldDTO<String>> saveField(@Valid @ModelAttribute FieldDTO<MultipartFile> fieldDTO) {
        log.info("Received request to save field: {}", fieldDTO.getFieldName());
        FieldDTO<String> savedField = fieldService.saveField(fieldDTO);
        log.info("Field saved successfully: {}", fieldDTO.getFieldName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedField);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(path = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FieldDTO<String>> updateField(@PathVariable("fieldCode") String fieldCode,
                                                        @Valid @ModelAttribute FieldDTO<MultipartFile> fieldDTO) {
        log.info("Received request to update field: {}", fieldCode);
        FieldDTO<String> updatedField = fieldService.updateField(fieldCode, fieldDTO);
        log.info("Field updated successfully: {}", fieldCode);
        return ResponseEntity.status(HttpStatus.OK).body(updatedField);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @DeleteMapping(path = "/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode) {
        log.info("Received request to delete field: {}", fieldCode);
        fieldService.deleteField(fieldCode);
        log.info("Field deleted successfully: {}", fieldCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldDTO<String>> getField(@PathVariable("fieldCode") String fieldCode) {
        log.info("Received request to get field: {}", fieldCode);
        FieldDTO<String> fieldDTO = fieldService.searchField(fieldCode);
        log.info("Field found: {}", fieldCode);
        return ResponseEntity.status(HttpStatus.OK).body(fieldDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldDTO<String>>> getAllFields() {
        log.info("Received request to get all fields");
        List<FieldDTO<String>> allFields = fieldService.getAllFields();
        log.info("Retrieved {} fields", allFields.size());
        return ResponseEntity.status(HttpStatus.OK).body(allFields);
    }
}

