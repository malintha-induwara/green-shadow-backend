package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void updateUser(String email,UserDTO userDTO);
    void deleteUser(String email);
    UserDTO searchUser(String email);
    UserDetailsService userDetailsService();
}
