package lk.ijse.gdse68.greenshadow.service.impl;

import lk.ijse.gdse68.greenshadow.dto.CropDetailDTO;
import lk.ijse.gdse68.greenshadow.entity.Crop;
import lk.ijse.gdse68.greenshadow.entity.CropDetail;
import lk.ijse.gdse68.greenshadow.entity.Field;
import lk.ijse.gdse68.greenshadow.entity.Staff;
import lk.ijse.gdse68.greenshadow.enums.ImageType;
import lk.ijse.gdse68.greenshadow.exception.CropDetailNotFoundException;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.repository.CropDetailRepository;
import lk.ijse.gdse68.greenshadow.repository.CropRepository;
import lk.ijse.gdse68.greenshadow.repository.FieldRepository;
import lk.ijse.gdse68.greenshadow.repository.StaffRepository;
import lk.ijse.gdse68.greenshadow.service.CropDetailService;
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
public class CropDetailServiceImpl implements CropDetailService {


    private final CropDetailRepository cropDetailRepository;
    private final FieldRepository fieldRepository;
    private final CropRepository cropRepository;
    private final StaffRepository staffRepository;
    private final ImageUtil imageUtil;
    private final Mapper mapper;

    @Override
    @Transactional
    public CropDetailDTO<String> saveCropDetail(CropDetailDTO<MultipartFile> cropDetailDTO) {
        String imageName = imageUtil.saveImage(ImageType.CROP_DETAIL, cropDetailDTO.getObservedImage());
        CropDetail cropDetail = mapper.convertToCropDetailEntity(cropDetailDTO);
        cropDetail.setObservedImage(imageName);

        if (cropDetailDTO.getFieldCodes()!=null) {
            List<Field> fields = getFieldsFromCodes(cropDetailDTO.getFieldCodes());
            cropDetail.setFields(fields);
        }

        if (cropDetailDTO.getCropCodes()!=null) {
            List<Crop> crops = getCropsFromCodes(cropDetailDTO.getCropCodes());
            cropDetail.setCrops(crops);
        }

        if (cropDetailDTO.getStaffIds()!=null) {
            List<Staff> staff = getStaffFromIds(cropDetailDTO.getStaffIds());
            cropDetail.setStaff(staff);
        }


        try {
            CropDetail savedCropLog = cropDetailRepository.save(cropDetail);
            CropDetailDTO<String> savedCropLogDTO = mapper.convertToCropDetailDTO(savedCropLog);
            savedCropLogDTO.setObservedImage(imageUtil.getImage(savedCropLog.getObservedImage()));
            return savedCropLogDTO;
        } catch (Exception e) {
            throw new DataPersistFailedException("Failed to save the crop detail");
        }
    }


    @Override
    @Transactional
    public CropDetailDTO<String> updateCropDetail(String logCode, CropDetailDTO<MultipartFile> cropDetailDTO) {
        Optional<CropDetail> tempCropDetail = cropDetailRepository.findById(logCode);
        if (tempCropDetail.isPresent()) {
            String imageName = tempCropDetail.get().getObservedImage();

            if (!cropDetailDTO.getObservedImage().isEmpty()) {
                imageName = imageUtil.updateImage(tempCropDetail.get().getObservedImage(), ImageType.CROP_DETAIL, cropDetailDTO.getObservedImage());
            }


            if (cropDetailDTO.getStaffIds() != null) {
                List<Staff> staff = getStaffFromIds(cropDetailDTO.getStaffIds());
                tempCropDetail.get().setStaff(staff);
            }else {
                tempCropDetail.get().setStaff(null);
            }

            if (cropDetailDTO.getFieldCodes() != null) {
                List<Field> fields = getFieldsFromCodes(cropDetailDTO.getFieldCodes());
                tempCropDetail.get().setFields(fields);
            }else {
                tempCropDetail.get().setFields(null);
            }

            if (cropDetailDTO.getCropCodes() != null) {
                List<Crop> crops = getCropsFromCodes(cropDetailDTO.getCropCodes());
                tempCropDetail.get().setCrops(crops);
            }else {
                tempCropDetail.get().setCrops(null);
            }

            tempCropDetail.get().setLogDate(cropDetailDTO.getLogDate());
            tempCropDetail.get().setLogDetail(cropDetailDTO.getLogDetail());
            tempCropDetail.get().setObservedImage(imageName);

            CropDetailDTO<String> updatedCropLogDTO = mapper.convertToCropDetailDTO(tempCropDetail.get());
            updatedCropLogDTO.setObservedImage(imageUtil.getImage(tempCropDetail.get().getObservedImage()));
            return updatedCropLogDTO;
        } else {
            throw new CropDetailNotFoundException("Crop detail not found");
        }

    }

    @Override
    public CropDetailDTO<String> getCropDetail(String logCode) {
        Optional<CropDetail> tempCropDetail = cropDetailRepository.findById(logCode);
        if (tempCropDetail.isPresent()) {
            CropDetailDTO<String> cropDetailDTO = mapper.convertToCropDetailDTO(tempCropDetail.get());
            cropDetailDTO.setObservedImage(imageUtil.getImage(tempCropDetail.get().getObservedImage()));
            return cropDetailDTO;
        } else {
            throw new CropDetailNotFoundException("Crop detail not found");
        }
    }

    @Override
    public List<CropDetailDTO<String>> getAllCropDetails() {
        List<CropDetail> cropDetails = cropDetailRepository.findAll();
        List<CropDetailDTO<String>> cropDetailDTOs = mapper.convertToCropDetailDTOList(cropDetails);
        for (CropDetailDTO<String> cropDetailDTO : cropDetailDTOs) {
            cropDetails.stream()
                    .filter(cd -> cd.getLogCode().equals(cropDetailDTO.getLogCode()))
                    .findFirst()
                    .ifPresent(cd -> cropDetailDTO.setObservedImage(imageUtil.getImage(cd.getObservedImage())));
        }
        return cropDetailDTOs;
    }

    @Override
    @Transactional
    public void deleteCropDetail(String logCode) {
        Optional<CropDetail> tempCropDetail = cropDetailRepository.findById(logCode);
        if (tempCropDetail.isPresent()) {
            imageUtil.deleteImage(tempCropDetail.get().getObservedImage());
            cropDetailRepository.deleteById(logCode);
        } else {
            throw new CropDetailNotFoundException("Crop detail not found");
        }

    }

    private List<Field> getFieldsFromCodes(List<String> fieldCodes) {
        return fieldRepository.findAllById(fieldCodes);
    }

    private List<Crop> getCropsFromCodes(List<String> cropCodes) {
        return cropRepository.findAllById(cropCodes);
    }

    private List<Staff> getStaffFromIds(List<String> staffIds) {
        return staffRepository.findAllById(staffIds);
    }
}

