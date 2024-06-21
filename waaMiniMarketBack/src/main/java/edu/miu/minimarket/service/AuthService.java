package edu.miu.minimarket.service;

import edu.miu.minimarket.entity.dto.request.UserLoginRequest;
import edu.miu.minimarket.entity.dto.request.UserRegistrationRequest;
import edu.miu.minimarket.entity.dto.response.AuthResponse;
import edu.miu.minimarket.entity.dto.request.RefreshTokenRequest;
import edu.miu.minimarket.entity.dto.response.UserResponse;

public interface AuthService {
    AuthResponse login(UserLoginRequest userLoginRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
