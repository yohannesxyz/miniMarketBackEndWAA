package edu.miu.minimarket.controller;

import edu.miu.minimarket.entity.dto.request.UserLoginRequest;
import edu.miu.minimarket.entity.dto.response.AuthResponse;
import edu.miu.minimarket.entity.dto.request.RefreshTokenRequest;
import edu.miu.minimarket.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authenticate")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
        var loginResponse = authService.login(userLoginRequest);
        return new ResponseEntity<AuthResponse>(
                loginResponse, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

}
