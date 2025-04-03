package com.application.ecommerce.auth;

import com.application.ecommerce.auth.dto.AuthRequest;
import com.application.ecommerce.auth.dto.AuthResponse;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public interface AuthService {
    boolean signup(AuthRequest authRequest);

    AuthResponse login(AuthRequest authRequest);

    String googleLogin(OidcUser oidcUser);
}
