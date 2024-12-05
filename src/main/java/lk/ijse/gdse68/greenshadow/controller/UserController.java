package lk.ijse.gdse68.greenshadow.controller;


import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.dto.UserDTO;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.UserAlreadyExistsExcetipion;
import lk.ijse.gdse68.greenshadow.exception.UserNotFoundException;
import lk.ijse.gdse68.greenshadow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Received request to save user: {}", userDTO);
        try {
            UserDTO savedUser = userService.saveUser(userDTO);
            log.info("User saved successfully: {}", savedUser.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (UserAlreadyExistsExcetipion e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataPersistFailedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Unexpected error while saving user: {}", userDTO.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") String userId, @Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") String userId) {
        try {
            UserDTO userDTO = userService.searchUser(userId);
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE','SCIENTIST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

