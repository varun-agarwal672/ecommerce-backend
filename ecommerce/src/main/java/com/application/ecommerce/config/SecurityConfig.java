package com.application.ecommerce.config;

import com.application.ecommerce.auth.dto.AuthResponse;
import com.application.ecommerce.filter.JwtAuthFilter;
import com.application.ecommerce.model.User;
import com.application.ecommerce.service.CustomOidcUserServiceImpl;
import com.application.ecommerce.user.UserRepository;
import com.application.ecommerce.userdetails.CustomUserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final CustomOidcUserServiceImpl oidcUserService;
    private final UserRepository userRepository;
    @Value(value = "${spring.login.redirect-uri}")
    private String redirectUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/signup", "/auth/login").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .formLogin(form -> form
//                        .loginPage("auth/login")
                        .loginProcessingUrl("/auth/login") // API endpoint for login
//                        .defaultSuccessUrl("http://localhost:3000/home")
                        .successHandler(this::loginSuccessHandler) // Custom success handler
                        .failureHandler(this::loginFailureHandler) // Custom failure handler
                        )
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/oauth/login")
                        .loginPage("/oauth2/authorization/google")
                        .successHandler(this::googleLoginSuccessHandler))
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler(((request, response, authentication) -> logoutSuccessHandler(request, response))))
                .build();
    }

    //TODO : Remove cookie auth_token because state is managed.

    private void loginFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Invalid username or password\"}");
    }

    private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String token = JwtUtil.generateToken(Long.valueOf(user.getUsername()));
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Login Successful");
    }

    public void logoutSuccessHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("auth_token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);  // Expire immediately
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Logged out successfully");
    }

    public void googleLoginSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        // Check if user exists in DB, if not, create a new one
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPassword(passwordEncoder().encode(oidcUser.getSubject() + email)); // Random password
            user = Optional.of(userRepository.save(newUser));
        }

        // Generate JWT Token
        String jwtToken = JwtUtil.generateToken(user.get().getId());

        System.out.println("jwtToken" + jwtToken);

        // Set JWT as HttpOnly Cookie
        Cookie cookie = new Cookie("auth_token", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);  // 1 day expiry
        cookie.setAttribute("SameSite", "None");  // Required for cross-origin cookies
        response.addCookie(cookie);

        // Redirect to frontend home page after successful login
        response.sendRedirect(redirectUri);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*","https://ecommerce-frontend-ruddy-ten.vercel.app"));  // You can set specific origins
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);  // Allow cookies to be included in CORS requests

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
