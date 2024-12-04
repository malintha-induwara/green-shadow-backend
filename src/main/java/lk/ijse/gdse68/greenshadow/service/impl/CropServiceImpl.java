package lk.ijse.gdse68.greenshadow.service.impl;

import lk.ijse.gdse68.greenshadow.dto.CropDTO;
import lk.ijse.gdse68.greenshadow.entity.Crop;
import lk.ijse.gdse68.greenshadow.entity.Field;
import lk.ijse.gdse68.greenshadow.enums.ImageType;
import lk.ijse.gdse68.greenshadow.exception.CropNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.FieldNotFoundException;
import lk.ijse.gdse68.greenshadow.repository.CropRepository;
import lk.ijse.gdse68.greenshadow.repository.FieldRepository;
import lk.ijse.gdse68.greenshadow.service.CropService;
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
public class CropServiceImpl implements CropService {

    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;
    private final ImageUtil imageUtil;
    private final Mapper mapper;

    @Override
    @Transactional
    public CropDTO<String> saveCrop(CropDTO<MultipartFile> cropDTO) {

        String imageName = imageUtil.saveImage(ImageType.CROP, cropDTO.getImage());
        Crop tempCrop = mapper.convertToCropEntity(cropDTO);
        tempCrop.setImage(imageName);

        if (!cropDTO.getField().equals("null")) {
            Optional<Field> tempField = fieldRepository.findById(cropDTO.getField());
            if (tempField.isPresent()) {
                tempCrop.setField(tempField.get());
            } else {
                throw new FieldNotFoundException("Field not found");
            }
        }

        try {
            Crop saveCrop = cropRepository.save(tempCrop);
            CropDTO<String> cropDTOS = mapper.convertToCropDTO(saveCrop);
            cropDTOS.setImage(imageUtil.getImage(saveCrop.getImage()));
            return cropDTOS;
        } catch (Exception e) {
            throw new DataPersistFailedException("Failed to save the crop");
        }
    }

    @Override
    @Transactional
    public CropDTO<String> updateCrop(String cropId, CropDTO<MultipartFile> cropDTO) {

        Optional<Crop> tempCrop = cropRepository.findById(cropId);
        if (tempCrop.isPresent()) {

            String imageName = tempCrop.get().getImage();

            if (!cropDTO.getImage().isEmpty()) {
                imageName = imageUtil.updateImage(tempCrop.get().getImage(), ImageType.CROP, cropDTO.getImage());
            }

            if (!cropDTO.getField().equals("null")) {
                Optional<Field> tempField = fieldRepository.findById(cropDTO.getField());
                if (tempField.isPresent()) {
                    tempCrop.get().setField(tempField.get());
                } else {
                    throw new FieldNotFoundException("Field not found");
                }
            } else {
                tempCrop.get().setField(null);
            }

            tempCrop.get().setCropCommonName(cropDTO.getCropCommonName());
            tempCrop.get().setCropScientificName(cropDTO.getCropScientificName());
            tempCrop.get().setImage(imageName);
            tempCrop.get().setCategory(cropDTO.getCategory());
            tempCrop.get().setCropSeason(cropDTO.getCropSeason());

            CropDTO<String> updatedCropDTO = mapper.convertToCropDTO(tempCrop.get());
            updatedCropDTO.setImage(imageUtil.getImage(tempCrop.get().getImage()));
            return updatedCropDTO;
        } else {
            throw new CropNotFoundException("Crop not found");
        }

    }

    @Override
    @Transactional
    public void deleteCrop(String cropId) {
        Optional<Crop> tempCrop = cropRepository.findById(cropId);
        if (tempCrop.isEmpty()) {
            throw new CropNotFoundException("Crop not found");
        }

        try {
            imageUtil.deleteImage(tempCrop.get().getImage());
            cropRepository.deleteById(cropId);
        } catch (Exception e) {
            throw new DataPersistFailedException("Failed to delete the crop");
        }
    }

    @Override
    public CropDTO<String> searchCrop(String cropId) {
        Optional<Crop> tempCrop = cropRepository.findById(cropId);
        if (tempCrop.isPresent()) {
            CropDTO<String> cropDTO = mapper.convertToCropDTO(tempCrop.get());
            cropDTO.setImage(imageUtil.getImage(tempCrop.get().getImage()));
            return cropDTO;
        } else {
            throw new CropNotFoundException("Crop not found");
        }
    }

    @Override
    public List<CropDTO<String>> getAllCrops() {
        List<Crop> crops = cropRepository.findAll();
        List<CropDTO<String>> cropDTOS = mapper.convertToCropDTOList(crops);
        for (CropDTO<String> cropDTO : cropDTOS) {
            crops.stream().filter(crop ->
                            crop.getCropCode().equals(cropDTO.getCropCode()))
                    .findFirst()
                    .ifPresent(crop -> cropDTO.setImage(imageUtil.getImage(crop.getImage())));
        }
        return cropDTOS;
    }
}

