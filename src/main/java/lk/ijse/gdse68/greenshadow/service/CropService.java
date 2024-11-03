package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.CropDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CropService {

    void saveCrop(CropDTO<MultipartFile> cropDTO);

    void updateCrop(String cropId, CropDTO<MultipartFile> cropDTO);

    void deleteCrop(String cropId);

    CropDTO<String> searchCrop(String vehicleId);

    List<CropDTO<String>> getAllCrops();
}
