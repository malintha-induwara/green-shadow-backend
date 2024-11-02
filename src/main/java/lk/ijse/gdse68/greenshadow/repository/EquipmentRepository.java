package lk.ijse.gdse68.greenshadow.repository;

import lk.ijse.gdse68.greenshadow.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
}
