package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {

    void saveVehicle(VehicleDTO vehicleDTO);

    void updateVehicle(String vehicleId, VehicleDTO vehicleDTO);

    void deleteVehicle(String vehicleId);

    VehicleDTO searchVehicle(String vehicleId);

    List<VehicleDTO> getAllVehicles();
}
