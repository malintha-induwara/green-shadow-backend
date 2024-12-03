package lk.ijse.gdse68.greenshadow.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.dto.UserDTO;
import lk.ijse.gdse68.greenshadow.exception.DataPersistFailedException;
import lk.ijse.gdse68.greenshadow.exception.UserAlreadyExistsExcetipion;
import lk.ijse.gdse68.greenshadow.jwtmodels.JwtAuthResponse;
import lk.ijse.gdse68.greenshadow.jwtmodels.AuthRequest;
import lk.ijse.gdse68.greenshadow.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "signUp", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody AuthRequest signUp) {
        log.info("Received sign-up request for email: {}", signUp.getEmail());
        try {
            JwtAuthResponse response = authService.signUp(signUp);
            log.info("User successfully signed up with email: {}", signUp.getEmail());
            return ResponseEntity.ok(response);
        } catch (UserAlreadyExistsExcetipion e) {
            log.warn("Sign-up failed: User with email {} already exists", signUp.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataPersistFailedException e) {
            log.error("Sign-up failed: Unable to persist user data for email: {}", signUp.getEmail(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Sign-up failed: Internal server error for email: {}", signUp.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "signIn", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signIn(@Valid @RequestBody AuthRequest signIn) {
        log.info("Received sign-in request for email: {}", signIn.getEmail());
        try {
            JwtAuthResponse response = authService.signIn(signIn);
            log.info("User successfully signed in with email: {}", signIn.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Sign-in failed for email: {}", signIn.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "refresh",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        log.info("Received token refresh request");
        try {
            JwtAuthResponse response = authService.refreshToken(refreshToken);
            log.info("Token successfully refreshed");
            return ResponseEntity.ok(response);
        } catch (ExpiredJwtException | SignatureException e) {
            log.warn("Token refresh failed: Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("Token refresh failed: Internal server error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

