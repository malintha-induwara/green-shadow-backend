package lk.ijse.gdse68.greenshadow.util;

import lk.ijse.gdse68.greenshadow.dto.VehicleDTO;
import lk.ijse.gdse68.greenshadow.entity.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Vehicle convertToVehicleEntity(VehicleDTO vehicleDTO){
        return modelMapper.map(vehicleDTO, Vehicle.class);
    }

    public VehicleDTO convertToVehicleDTO(Vehicle vehicle){
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

}

