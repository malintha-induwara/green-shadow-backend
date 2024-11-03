package lk.ijse.gdse68.greenshadow.service.impl;

import lk.ijse.gdse68.greenshadow.dto.StaffDTO;
import lk.ijse.gdse68.greenshadow.entity.Staff;
import lk.ijse.gdse68.greenshadow.exception.StaffNotFoundException;
import lk.ijse.gdse68.greenshadow.repository.StaffRepository;
import lk.ijse.gdse68.greenshadow.service.StaffService;
import lk.ijse.gdse68.greenshadow.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final Mapper mapper;

    private final StaffRepository staffRepository;


    @Override
    public void saveStaff(StaffDTO staffDTO) {
        try {
            staffRepository.save(mapper.convertToStaffEntity(staffDTO));
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the staff");
        }
    }

    @Override
    @Transactional
    public void updateStaff(String staffId, StaffDTO staffDTO) {
        Optional<Staff> tempStaff = staffRepository.findById(staffId);
        if (tempStaff.isPresent()) {
            tempStaff.get().setFirstName(staffDTO.getFirstName());
            tempStaff.get().setLastName(staffDTO.getLastName());
            tempStaff.get().setDesignation(staffDTO.getDesignation());
            tempStaff.get().setGender(staffDTO.getGender());
            tempStaff.get().setJoinedDate(staffDTO.getJoinedDate());
            tempStaff.get().setDob(staffDTO.getDob());
            tempStaff.get().setAddressLine01(staffDTO.getAddressLine01());
            tempStaff.get().setAddressLine02(staffDTO.getAddressLine02());
            tempStaff.get().setAddressLine03(staffDTO.getAddressLine03());
            tempStaff.get().setAddressLine04(staffDTO.getAddressLine04());
            tempStaff.get().setAddressLine05(staffDTO.getAddressLine05());
            tempStaff.get().setContactNo(staffDTO.getContactNo());
            tempStaff.get().setEmail(staffDTO.getEmail());
            tempStaff.get().setRole(staffDTO.getRole());
        } else {
            throw new StaffNotFoundException("Staff not found ");
        }

    }

    @Override
    public void deleteStaff(String staffId) {
        Optional<Staff> tempStaff = staffRepository.findById(staffId);
        if (tempStaff.isPresent()) {
            staffRepository.deleteById(staffId);
        } else {
            throw new StaffNotFoundException("Staff not found ");
        }
    }

    @Override
    public StaffDTO searchStaff(String staffId) {
        if (staffRepository.existsById(staffId)) {
            return mapper.convertToStaffDTO(staffRepository.getReferenceById(staffId));
        } else {
            throw new StaffNotFoundException("Staff not found ");
        }
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        List<Staff> staff = staffRepository.findAll();
        return mapper.convertToStaffDTOList(staff);
    }
}
