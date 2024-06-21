package edu.miu.minimarket.service.impl;

import edu.miu.minimarket.entity.Role;
import edu.miu.minimarket.entity.User;
import edu.miu.minimarket.entity.dto.request.UserLoginRequest;
import edu.miu.minimarket.entity.dto.request.RefreshTokenRequest;
import edu.miu.minimarket.entity.dto.response.AuthResponse;
import edu.miu.minimarket.repository.UserRepository;
import edu.miu.minimarket.util.JwtUtil;
import edu.miu.minimarket.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(UserLoginRequest userLoginRequest) {
        Authentication result;
        try {
            result = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(result.getName());
        final String accessToken = jwtUtil.generateToken(userDetails);
        final String refreshToken = jwtUtil.generateRefreshToken(userLoginRequest.getEmail());

        // Fetch user details from the database
        User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toList());

        return new AuthResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstname(),
                user.getLastname(),
                roles,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        boolean isRefreshTokenValid = jwtUtil.validateToken(refreshTokenRequest.getRefreshToken());
        if (isRefreshTokenValid) {
            String email = jwtUtil.getSubject(refreshTokenRequest.getRefreshToken());

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new BadCredentialsException("User not found"));

            List<String> roles = user.getRoles().stream()
                    .map(Role::getRole)
                    .collect(Collectors.toList());

            final String accessToken = jwtUtil.doGenerateToken(email);
            return new AuthResponse(
                    accessToken,
                    refreshTokenRequest.getRefreshToken(),
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getFirstname(),
                    user.getLastname(),
                    roles,
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );
        }
        return new AuthResponse();
    }
}
