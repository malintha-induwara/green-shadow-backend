package lk.ijse.gdse68.greenshadow.service;

import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDTO updateUser(String email,UserDTO userDTO);
    void deleteUser(String email);
    UserDTO searchUser(String email);
    UserDetailsService userDetailsService();
    List<UserDTO> getAllUsers();
    UserDTO saveUser(UserDTO userDTO);
}
