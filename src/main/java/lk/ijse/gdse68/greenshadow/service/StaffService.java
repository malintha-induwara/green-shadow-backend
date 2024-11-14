package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.StaffDTO;
import lk.ijse.gdse68.greenshadow.entity.Staff;

import java.util.List;

public interface StaffService {
    StaffDTO saveStaff(StaffDTO staffDTO);

    StaffDTO updateStaff(String staffId, StaffDTO staffDTO);

    void deleteStaff(String staffId);

    StaffDTO searchStaff(String staffId);

    List<StaffDTO> getAllStaff();
}
