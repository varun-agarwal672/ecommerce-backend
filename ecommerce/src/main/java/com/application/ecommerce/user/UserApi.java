package com.application.ecommerce.user;

import com.application.ecommerce.user.dto.UserRequest;
import com.application.ecommerce.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public interface UserApi {

//    @GetMapping
//    ResponseEntity<UserResponse> getUser(@RequestBody UserRequest userRequest);

    @GetMapping
    ResponseEntity<UserResponse> getUserById(@RequestAttribute("userId") Long id);

    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest);

    @PutMapping
    ResponseEntity<UserResponse> updateUser(@RequestAttribute("userId") Long id, @RequestBody UserRequest userRequest);
}
