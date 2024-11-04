package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.UserDTO;

public interface UserService {
    void saveUser(UserDTO userDTO);
    void updateUser(String email,UserDTO userDTO);
    void deleteUser(String email);
    UserDTO searchUser(String email);
}
