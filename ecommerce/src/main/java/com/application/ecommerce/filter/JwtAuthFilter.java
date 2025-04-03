package com.application.ecommerce.filter;

import com.application.ecommerce.config.JwtUtil;
import com.application.ecommerce.user.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilter {

    private final UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /*System.out.println(request.getRequestURI()+"===============================");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        System.out.println("=============END===============");*/
//        final String authorizationHeader = request.getHeader("Authorization");
        /*String AUTH_ENDPOINT = "auth";
        if(!request.getRequestURI().contains(AUTH_ENDPOINT)) {*/
            try {
                if(request.getCookies() != null && request.getCookies().length > 0) {
                    Optional<String> tokenOpt = Arrays.stream(request.getCookies())
                            .filter(cookie -> "auth_token".equals(cookie.getName()))
                            .map(Cookie::getValue)
                            .findFirst();
                    if (tokenOpt.isPresent()) {
                        String token = tokenOpt.get();
                        Long userId = JwtUtil.extractUserId(token);
                        if (JwtUtil.isTokenValid(token, userId)) {
                            userService.getUserById(userId);
                            request.setAttribute("userId", userId);
                            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            /*if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userId = JwtUtil.extractUserId(token);
            }*/
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
//        }

        filterChain.doFilter(request, response);
    }
}
