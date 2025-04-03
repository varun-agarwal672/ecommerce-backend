package com.application.ecommerce.auth;

import com.application.ecommerce.auth.dto.AuthRequest;
import com.application.ecommerce.auth.dto.AuthResponse;
import com.application.ecommerce.config.JwtUtil;
import com.application.ecommerce.model.User;
import com.application.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean signup(AuthRequest authRequest) {
        boolean isUserPresent = userRepository.existsByEmail(authRequest.getEmail());
        if(isUserPresent) {
            throw new RuntimeException("User already present with this email.");
        }
        User user = new User();
        user.setName(authRequest.getName());
        user.setEmail(authRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        userRepository.save(user);

        return true;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found with this email."));

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = JwtUtil.generateToken(user.getId());
        return new AuthResponse(user.getId(), token);
    }

    @Override
    public String googleLogin(OidcUser oidcUser) {
        String email = oidcUser.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with this email."));

        return JwtUtil.generateToken(user.getId());
    }

}
