package lk.ijse.gdse68.greenshadow.service.impl;

import lk.ijse.gdse68.greenshadow.dto.FieldDTO;
import lk.ijse.gdse68.greenshadow.entity.Field;
import lk.ijse.gdse68.greenshadow.enums.ImageType;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.FieldNotFoundException;
import lk.ijse.gdse68.greenshadow.repository.FieldRepository;
import lk.ijse.gdse68.greenshadow.service.FieldService;
import lk.ijse.gdse68.greenshadow.util.ImageUtil;
import lk.ijse.gdse68.greenshadow.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final Mapper mapper;

    private final FieldRepository fieldRepository;

    private final ImageUtil imageUtil;


    @Override
    @Transactional
    public void saveField(FieldDTO<MultipartFile> fieldDTO) {
        try {
            String image1Name = imageUtil.saveImage(ImageType.FIELD, fieldDTO.getFieldImage1());
            String image2Name = imageUtil.saveImage(ImageType.FIELD, fieldDTO.getFieldImage2());

            Field field = mapper.convertToFieldEntity(fieldDTO);

            field.setFieldImage1(image1Name);
            field.setFieldImage2(image2Name);
            fieldRepository.save(field);
        } catch (Exception e) {
            throw new DataPersistFailedException("Failed to save the field");
        }
    }

    @Override
    @Transactional
    public void updateField(String fieldId, FieldDTO<MultipartFile> fieldDTO) {
        Optional<Field> tempField = fieldRepository.findById(fieldId);
        if (tempField.isPresent()) {

            tempField.get().setFieldName(fieldDTO.getFieldName());
            tempField.get().setExtentSize(fieldDTO.getExtentSize());

            if (!fieldDTO.getFieldImage1().isEmpty()) {
                String image1Name = imageUtil.updateImage(tempField.get().getFieldImage1(), ImageType.FIELD, fieldDTO.getFieldImage1());
                tempField.get().setFieldImage1(image1Name);
            }

            if (!fieldDTO.getFieldImage2().isEmpty()) {
                String image2Name = imageUtil.updateImage(tempField.get().getFieldImage2(), ImageType.FIELD, fieldDTO.getFieldImage2());
                tempField.get().setFieldImage2(image2Name);
            }
        }
    }

    @Override
    public void deleteField(String fieldId) {
        Optional<Field> tempField = fieldRepository.findById(fieldId);
        if (tempField.isPresent()) {
            imageUtil.deleteImage(tempField.get().getFieldImage1());
            imageUtil.deleteImage(tempField.get().getFieldImage2());
            fieldRepository.delete(tempField.get());
        } else {
            throw new FieldNotFoundException("Field not found");
        }

    }

    @Override
    public FieldDTO<String> searchField(String fieldId) {
        Optional<Field> tempField = fieldRepository.findById(fieldId);
        if (tempField.isPresent()) {
            FieldDTO<String> fieldDTO = mapper.convertToFieldDTO(tempField.get());
            fieldDTO.setFieldImage1(imageUtil.getImage(tempField.get().getFieldImage1()));
            fieldDTO.setFieldImage2(imageUtil.getImage(tempField.get().getFieldImage2()));
            return fieldDTO;
        } else {
            throw new FieldNotFoundException("Field not found");
        }

    }

    @Override
    public List<FieldDTO<String>> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        List<FieldDTO<String>> fieldDTOS = mapper.convertToFieldDTOList(fields);
        for (FieldDTO<String> fieldDTO : fieldDTOS) {
            fields.stream().filter(field ->
                            field.getFieldCode().equals(fieldDTO.getFieldCode()))
                    .findFirst()
                    .ifPresent(field -> {
                        fieldDTO.setFieldImage1(imageUtil.getImage(field.getFieldImage1()));
                        fieldDTO.setFieldImage2(imageUtil.getImage(field.getFieldImage2()));
                    });
        }
        return fieldDTOS;
    }
}

