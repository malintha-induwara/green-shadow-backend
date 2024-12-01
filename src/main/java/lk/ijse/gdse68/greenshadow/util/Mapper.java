package lk.ijse.gdse68.greenshadow.util;

import lk.ijse.gdse68.greenshadow.dto.*;
import lk.ijse.gdse68.greenshadow.entity.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Mapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


        //Converters
        Converter<List<Field>, List<String>> fieldToIdConverter = ctx ->
                ctx.getSource() == null ? new ArrayList<>() :
                        ctx.getSource().stream()
                                .filter(Objects::nonNull)
                                .map(Field::getFieldCode)
                                .collect(Collectors.toList());

        Converter<List<Crop>, List<String>> cropToIdConverter = ctx ->
                ctx.getSource() == null ? new ArrayList<>() :
                        ctx.getSource().stream()
                                .filter(Objects::nonNull)
                                .map(Crop::getCropCode)
                                .collect(Collectors.toList());

        Converter<List<Staff>, List<String>> staffToIdConverter = ctx ->
                ctx.getSource() == null ? new ArrayList<>() :
                        ctx.getSource().stream()
                                .filter(Objects::nonNull)
                                .map(Staff::getStaffId)
                                .collect(Collectors.toList());

        Converter<List<Vehicle>, List<String>> vehicleToIdConverter = ctx ->
                ctx.getSource() == null ? new ArrayList<>() :
                        ctx.getSource().stream()
                                .filter(Objects::nonNull)
                                .map(Vehicle::getVehicleCode)
                                .collect(Collectors.toList());

        PropertyMap<Crop, CropDTO<String>> cropMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setCropCode(source.getCropCode());
                map().setCropCommonName(source.getCropCommonName());
                map().setCropScientificName(source.getCropScientificName());
                map().setCategory(source.getCategory());
                map().setCropSeason(source.getCropSeason());
                map().setField(source.getField().getFieldCode());
            }
        };

        PropertyMap<Equipment,EquipmentDTO> equipmentMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setEquipmentId(source.getEquipmentId());
                map().setName(source.getName());
                map().setStatus(source.getStatus());
                map().setEquipmentType(source.getEquipmentType());
                map().setField(source.getField().getFieldCode());
                map().setStaff(source.getStaff().getStaffId());
            }
        };


        modelMapper.addMappings(cropMap);
        modelMapper.addMappings(equipmentMap);

        modelMapper.createTypeMap(CropDetail.class, CropDetailDTO.class)
                .addMappings(mapper -> {
                    mapper.map(CropDetail::getLogCode, CropDetailDTO<String>::setLogCode);
                    mapper.map(CropDetail::getLogDate, CropDetailDTO<String>::setLogDate);
                    mapper.map(CropDetail::getLogDetail, CropDetailDTO<String>::setLogDetail);
                    mapper.skip(CropDetail::getObservedImage, CropDetailDTO<String>::setObservedImage);
                    mapper.using(fieldToIdConverter).map(CropDetail::getFields, CropDetailDTO<String>::setFieldCodes);
                    mapper.using(cropToIdConverter).map(CropDetail::getCrops, CropDetailDTO<String>::setCropCodes);
                    mapper.using(staffToIdConverter).map(CropDetail::getStaff, CropDetailDTO<String>::setStaffIds);
                });


        modelMapper.createTypeMap(Field.class, FieldDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Field::getFieldCode, FieldDTO<String>::setFieldCode);
                    mapper.map(Field::getFieldName, FieldDTO<String>::setFieldName);
                    mapper.map(Field::getFieldLocation, FieldDTO<String>::setFieldLocation);
                    mapper.map(Field::getExtentSize, FieldDTO<String>::setExtentSize);
                    mapper.skip(Field::getFieldImage1, FieldDTO<String>::setFieldImage1);
                    mapper.skip(Field::getFieldImage2, FieldDTO<String>::setFieldImage2);
                    mapper.using(cropToIdConverter).map(Field::getCrops, FieldDTO<String>::setCrops);
                    mapper.using(staffToIdConverter).map(Field::getStaff, FieldDTO<String>::setStaff);
                });

        modelMapper.createTypeMap(Staff.class, StaffDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Staff::getStaffId, StaffDTO::setStaffId);
                    mapper.map(Staff::getFirstName, StaffDTO::setFirstName);
                    mapper.map(Staff::getLastName, StaffDTO::setLastName);
                    mapper.map(Staff::getDesignation, StaffDTO::setDesignation);
                    mapper.map(Staff::getGender, StaffDTO::setGender);
                    mapper.map(Staff::getJoinedDate, StaffDTO::setJoinedDate);
                    mapper.map(Staff::getDob, StaffDTO::setDob);
                    mapper.map(Staff::getAddressLine01, StaffDTO::setAddressLine01);
                    mapper.map(Staff::getAddressLine02, StaffDTO::setAddressLine02);
                    mapper.map(Staff::getAddressLine03, StaffDTO::setAddressLine03);
                    mapper.map(Staff::getAddressLine04, StaffDTO::setAddressLine04);
                    mapper.map(Staff::getAddressLine05, StaffDTO::setAddressLine05);
                    mapper.map(Staff::getContactNo, StaffDTO::setContactNo);
                    mapper.map(Staff::getRole, StaffDTO::setRole);
                    mapper.using(vehicleToIdConverter).map(Staff::getVehicles, StaffDTO::setVehicles);
                    mapper.using(fieldToIdConverter).map(Staff::getFields, StaffDTO::setFields);
                });




        modelMapper.typeMap(CropDTO.class, Crop.class).addMappings(mapper -> mapper.skip(Crop::setImage));
        modelMapper.typeMap(FieldDTO.class, Field.class).addMappings(mapper -> mapper.skip(Field::setFieldImage1));
        modelMapper.typeMap(FieldDTO.class, Field.class).addMappings(mapper -> mapper.skip(Field::setFieldImage2));
        modelMapper.typeMap(CropDetailDTO.class, CropDetail.class).addMappings(mapper -> mapper.skip(CropDetail::setObservedImage));
        modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setPassword));
    }


    public Vehicle convertToVehicleEntity(VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, Vehicle.class);
    }

    public VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    public List<VehicleDTO> convertToVehicleDTOList(List<Vehicle> vehicles) {return modelMapper.map(vehicles, new TypeToken<List<VehicleDTO>>() {}.getType());}

    public Crop convertToCropEntity(CropDTO<MultipartFile> cropDTO) { return modelMapper.map(cropDTO, Crop.class);}

    public CropDTO<String> convertToCropDTO(Crop crop) { return modelMapper.map(crop, new TypeToken<CropDTO<String>>() {}.getType());}

    public List<CropDTO<String>> convertToCropDTOList(List<Crop> crops) { return modelMapper.map(crops, new TypeToken<List<CropDTO<String>>>() {}.getType());}

    public Field convertToFieldEntity(FieldDTO<MultipartFile> fieldDTO) { return modelMapper.map(fieldDTO, Field.class);}

    public FieldDTO<String> convertToFieldDTO(Field field) { return modelMapper.map(field, new TypeToken<FieldDTO<String>>() {}.getType());}

    public List<FieldDTO<String>> convertToFieldDTOList(List<Field> fields) { return modelMapper.map(fields, new TypeToken<List<FieldDTO<String>>>() {}.getType());}

    public Staff convertToStaffEntity(StaffDTO staffDTO) { return modelMapper.map(staffDTO, Staff.class);}

    public StaffDTO convertToStaffDTO(Staff referenceById) { return modelMapper.map(referenceById, StaffDTO.class);}

    public List<StaffDTO> convertToStaffDTOList(List<Staff> staff) {return modelMapper.map(staff, new TypeToken<List<StaffDTO>>() {}.getType()); }

    public Equipment convertToEquipmentEntity(EquipmentDTO equipmentDTO) { return modelMapper.map(equipmentDTO, Equipment.class);}

    public EquipmentDTO convertToEquipmentDTO(Equipment equipmentId) {return modelMapper.map(equipmentId, EquipmentDTO.class);}

    public List<EquipmentDTO> convertToEquipmentDTOList(List<Equipment> equipments) {return modelMapper.map(equipments, new TypeToken<List<EquipmentDTO>>() {}.getType());}

    public User convertToUserEntity(UserDTO userDTO) {return modelMapper.map(userDTO, User.class);}

    public UserDTO convertToUserDTO(User referenceById) {return modelMapper.map(referenceById, UserDTO.class);}

    public List<UserDTO> convertToUserDTOList(List<User> users) {return modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());}

    public CropDetail convertToCropDetailEntity(CropDetailDTO<MultipartFile> cropDetailDTO) {return modelMapper.map(cropDetailDTO, CropDetail.class);}

    public CropDetailDTO<String> convertToCropDetailDTO(CropDetail cropDetail) {return modelMapper.map(cropDetail, new TypeToken<CropDetailDTO<String>>() {}.getType());}

    public List<CropDetailDTO<String>> convertToCropDetailDTOList(List<CropDetail> cropDetails) {return modelMapper.map(cropDetails, new TypeToken<List<CropDetailDTO<String>>>() {}.getType());}
}

