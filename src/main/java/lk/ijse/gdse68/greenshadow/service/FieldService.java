package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.FieldDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FieldService {
    FieldDTO<String> saveField(FieldDTO<MultipartFile> fieldDTO);

    FieldDTO<String> updateField(String fieldId, FieldDTO<MultipartFile> fieldDTO);

    void deleteField(String fieldId);

    FieldDTO<String> searchField(String fieldId);

    List<FieldDTO<String>> getAllFields();
}
