package lk.ijse.gdse68.greenshadow.service.impl;

import lk.ijse.gdse68.greenshadow.dto.UserDTO;
import lk.ijse.gdse68.greenshadow.entity.User;
import lk.ijse.gdse68.greenshadow.enums.Role;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.UserAlreadyExistsExcetipion;
import lk.ijse.gdse68.greenshadow.jwtmodels.JwtAuthResponse;
import lk.ijse.gdse68.greenshadow.jwtmodels.AuthRequest;
import lk.ijse.gdse68.greenshadow.repository.UserRepository;
import lk.ijse.gdse68.greenshadow.service.AuthService;
import lk.ijse.gdse68.greenshadow.service.JWTService;
import lk.ijse.gdse68.greenshadow.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(AuthRequest signIn) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword());
        authenticationManager.authenticate(authenticationToken);
        User userByEmail = userRepository.findById(signIn.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String generatedToken = jwtService.generateToken(userByEmail);
        return JwtAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JwtAuthResponse signUp(AuthRequest signUp) {
        Optional<User> tempUser = userRepository.findById(signUp.getEmail());
        if (tempUser.isPresent()) {
            throw new UserAlreadyExistsExcetipion("User already exists");
        }
        try {
            signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
            User user = new User(signUp.getEmail(),signUp.getPassword(), Role.OTHER);
            User savedUser = userRepository.save(user);
            String generateToken = jwtService.generateToken(savedUser);
            return JwtAuthResponse.builder().token(generateToken).build();
        } catch (Exception e) {
            throw new DataPersistFailedException("Failed to save the user");
        }
    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        String extractedUserName = jwtService.extractUsername(accessToken);
        User user = userRepository.findById(extractedUserName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String refreshToken = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(refreshToken).build();
    }
}

