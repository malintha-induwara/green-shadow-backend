package lk.ijse.gdse68.greenshadow.service;

import lk.ijse.gdse68.greenshadow.dto.UserDTO;
import lk.ijse.gdse68.greenshadow.jwtmodels.JwtAuthResponse;
import lk.ijse.gdse68.greenshadow.jwtmodels.AuthRequest;

public interface AuthService {
    JwtAuthResponse signIn(AuthRequest signIn);
    JwtAuthResponse signUp(AuthRequest signUp);
    JwtAuthResponse refreshToken(String accessToken);
}
