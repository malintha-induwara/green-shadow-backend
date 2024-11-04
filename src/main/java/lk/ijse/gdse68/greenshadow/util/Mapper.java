package lk.ijse.gdse68.greenshadow.util;

import lk.ijse.gdse68.greenshadow.dto.*;
import lk.ijse.gdse68.greenshadow.entity.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class Mapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


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

        modelMapper.typeMap(CropDTO.class, Crop.class).addMappings(mapper -> mapper.skip(Crop::setImage));
        modelMapper.typeMap(FieldDTO.class, Field.class).addMappings(mapper -> mapper.skip(Field::setFieldImage1));
        modelMapper.typeMap(FieldDTO.class, Field.class).addMappings(mapper -> mapper.skip(Field::setFieldImage2));
        modelMapper.typeMap(Field.class, FieldDTO.class).addMappings(mapper -> mapper.skip(FieldDTO<String>::setFieldImage1));
        modelMapper.typeMap(Field.class, FieldDTO.class).addMappings(mapper -> mapper.skip(FieldDTO<String>::setFieldImage2));
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
}

