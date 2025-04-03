package com.application.ecommerce.auth;

import com.application.ecommerce.auth.dto.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RequestMapping(value = "/auth")
public interface AuthApi {

    @PostMapping(value = "/signup")
    ResponseEntity<String> signup(@RequestBody AuthRequest authRequest);

    /*@PostMapping(value = "/login")
    ResponseEntity<String> login(@RequestBody AuthRequest authRequest, HttpServletResponse response);
*/
    @GetMapping(value = "/google/success")
    ResponseEntity<String> googleLogin(OidcUser oidcUser);

    /*@PostMapping(value = "/logout")
    ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response);*/
}
