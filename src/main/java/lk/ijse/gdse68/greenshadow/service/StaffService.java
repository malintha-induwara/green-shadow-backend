package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO);

    void updateStaff(String staffId, StaffDTO staffDTO);

    void deleteStaff(String staffId);

    StaffDTO searchStaff(String staffId);

    List<StaffDTO> getAllStaff();
}
