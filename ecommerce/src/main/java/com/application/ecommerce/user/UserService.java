package com.application.ecommerce.user;

import com.application.ecommerce.user.dto.UserRequest;
import com.application.ecommerce.user.dto.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse getUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    UserResponse getUserByEmail(String email);

    UserResponse updateUser(Long id, UserRequest userRequest);
}
