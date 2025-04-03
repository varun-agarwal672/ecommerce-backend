package com.application.ecommerce.auth;

import com.application.ecommerce.auth.dto.AuthRequest;
import com.application.ecommerce.auth.dto.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi{

    private final AuthService authService;

    @Override
    public ResponseEntity<String> signup(AuthRequest authRequest) {
        return authService.signup(authRequest) ? ResponseEntity.ok("Signup Successful") : ResponseEntity.badRequest().build();
    }

    /*@Override
    public ResponseEntity<String> login(AuthRequest authRequest, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(authRequest);
        ResponseCookie jwtCookie = ResponseCookie.from("auth_token", authResponse.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofSeconds(3600))
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        return ResponseEntity.ok("Login Successful");
    }*/

    @Override
    public ResponseEntity<String> googleLogin(OidcUser oidcUser) {
        return ResponseEntity.ok(authService.googleLogin(oidcUser));
    }

    /*@Override
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Create a new cookie with the same name but empty value and expired
        Cookie cookie = new Cookie("auth_token", null);  // Pass null to indicate no value
        cookie.setHttpOnly(true);  // Make it HttpOnly to protect against client-side access
        cookie.setSecure(true);    // Ensure it’s sent over HTTPS
        cookie.setPath("/");       // Set the path to match the original cookie path
        cookie.setMaxAge(0);       // Set age to 0, which removes the cookie immediately
        cookie.setAttribute("SameSite", "None");  // Optional: set SameSite attribute if used originally

        // Add the cookie to the response to instruct the browser to remove it
        response.addCookie(cookie);

        // Optionally, redirect to the login page
        return ResponseEntity.ok("Logged out successfully");
    }*/
}
