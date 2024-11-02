package lk.ijse.gdse68.greenshadow.repository;

import lk.ijse.gdse68.greenshadow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
