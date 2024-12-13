package lk.ijse.gdse68.greenshadow.service.impl;

import lk.ijse.gdse68.greenshadow.dto.UserDTO;
import lk.ijse.gdse68.greenshadow.entity.User;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.UserAlreadyExistsException;
import lk.ijse.gdse68.greenshadow.exception.UserNotFoundException;
import lk.ijse.gdse68.greenshadow.repository.UserRepository;
import lk.ijse.gdse68.greenshadow.service.UserService;
import lk.ijse.gdse68.greenshadow.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        Optional<User> tempUser = userRepository.findById(userDTO.getEmail());
        if (tempUser.isPresent()) {
            throw new UserAlreadyExistsException(userDTO.getEmail());
        }
        try {
            User user = mapper.convertToUserEntity(userDTO);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return mapper.convertToUserDTO(userRepository.save(user));
        } catch (Exception e) {
            throw new DataPersistFailedException("Failed to save the user");
        }
    }

    @Override
    @Transactional
    public UserDTO updateUser(String email, UserDTO userDTO) {
        Optional<User> tempUser = userRepository.findById(email);
        if (tempUser.isPresent()) {
            if (!userDTO.getPassword().equals("null")) {
                tempUser.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            tempUser.get().setRole(userDTO.getRole());
            return mapper.convertToUserDTO(userRepository.save(tempUser.get()));
        } else {
            throw new UserNotFoundException(email);
        }
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> tempUser = userRepository.findById(email);
        if (tempUser.isPresent()) {
            userRepository.delete(tempUser.get());
        } else {
            throw new UserNotFoundException(email);
        }
    }

    @Override
    public UserDTO searchUser(String email) {
        if (userRepository.existsById(email)) {
            return mapper.convertToUserDTO(userRepository.getReferenceById(email));
        } else {
            throw new UserNotFoundException(email);
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email ->
                userRepository.findById(email)
                        .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapper.convertToUserDTOList(users);
    }

}

