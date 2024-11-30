package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractUsername(String token);
    String generateToken(User userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
