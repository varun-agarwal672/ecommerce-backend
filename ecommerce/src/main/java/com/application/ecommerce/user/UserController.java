package com.application.ecommerce.user;

import com.application.ecommerce.model.User;
import com.application.ecommerce.user.dto.UserRequest;
import com.application.ecommerce.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi{

    private final UserService userService;

//    @Override
//    public ResponseEntity<UserResponse> getUser(UserRequest userRequest) {
//        return ResponseEntity.ok(userService.getUser(userRequest));
//    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long id, UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }
}
