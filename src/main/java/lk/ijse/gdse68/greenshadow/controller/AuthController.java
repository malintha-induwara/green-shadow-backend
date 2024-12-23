package lk.ijse.gdse68.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse68.greenshadow.jwtmodels.AuthRequest;
import lk.ijse.gdse68.greenshadow.jwtmodels.JwtAuthResponse;
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

    @PostMapping(value = "signUp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody AuthRequest signUp) {
        log.info("Received sign-up request for email: {}", signUp.getEmail());
        JwtAuthResponse response = authService.signUp(signUp);
        log.info("User successfully signed up with email: {}", signUp.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "signIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signIn(@Valid @RequestBody AuthRequest signIn) {
        log.info("Received sign-in request for email: {}", signIn.getEmail());
        JwtAuthResponse response = authService.signIn(signIn);
        log.info("User successfully signed in with email: {}", signIn.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        log.info("Received token refresh request");
        JwtAuthResponse response = authService.refreshToken(refreshToken);
        log.info("Token successfully refreshed");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

