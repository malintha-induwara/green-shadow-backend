package lk.ijse.gdse68.greenshadow.service.impl;

import lk.ijse.gdse68.greenshadow.dto.UserDTO;
import lk.ijse.gdse68.greenshadow.entity.User;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.UserNotFoundException;
import lk.ijse.gdse68.greenshadow.repository.UserRepository;
import lk.ijse.gdse68.greenshadow.service.UserService;
import lk.ijse.gdse68.greenshadow.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    @Override
    public void saveUser(UserDTO userDTO) {
        try {
            userRepository.save(mapper.convertToUserEntity(userDTO));
        } catch (Exception e) {
            throw new DataPersistFailedException("Failed to save the user");
        }
    }

    @Override
    @Transactional
    public void updateUser(String email,UserDTO userDTO) {
        Optional<User> tempUser = userRepository.findById(email);
        if (tempUser.isPresent()) {
            tempUser.get().setPassword(userDTO.getPassword());
            tempUser.get().setRole(userDTO.getRole());
        } else {
            throw new DataPersistFailedException("User not found ");
        }
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> tempUser = userRepository.findById(email);
        if (tempUser.isPresent()) {
            userRepository.delete(tempUser.get());
        } else {
            throw new DataPersistFailedException("User not found ");
        }
    }

    @Override
    public UserDTO searchUser(String email) {
        if (userRepository.existsById(email)) {
            return mapper.convertToUserDTO(userRepository.getReferenceById(email));
        } else {
         throw new UserNotFoundException("User not found");
        }
    }
}

