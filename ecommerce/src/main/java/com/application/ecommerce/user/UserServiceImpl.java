package com.application.ecommerce.user;

import com.application.ecommerce.model.User;
import com.application.ecommerce.user.dto.UserRequest;
import com.application.ecommerce.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        /*boolean isUserPresent = userRepository.existsByEmail(userRequest.getEmail());
        if(isUserPresent) throw new RuntimeException("User already exists with this email.");
*/
        User user = new User();
        user.setName(userRequest.getName());
        /*user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        */user.setAddress(userRequest.getAddress());
        user.setCity(userRequest.getCity());
        user.setState(userRequest.getState());
        user.setPostalCode(userRequest.getPostalCode());
        user.setCountry(userRequest.getCountry());

        User createdUser = userRepository.save(user);
        return new UserResponse(createdUser.getId(), createdUser.getName(), createdUser.getEmail(), createdUser.getAddress(), createdUser.getCity(), createdUser.getState(), createdUser.getPostalCode(), createdUser.getCountry());
    }

    @Override
    public UserResponse getUser(UserRequest userRequest) {
//        User user = userRepository.findByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword()).orElseThrow(() -> new RuntimeException("No user found with given email and password."));
//        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getCity(), user.getState(), user.getPostalCode(), user.getCountry());
        return null;
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user found with given user id."));
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getCity(), user.getState(), user.getPostalCode(), user.getCountry());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No user found with given email."));
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getCity(), user.getState(), user.getPostalCode(), user.getCountry());
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user found with given user id."));
        user.setName(userRequest.getName() != null ? userRequest.getName() : user.getName());
        user.setAddress(userRequest.getAddress() != null ? userRequest.getAddress() : user.getAddress());
        user.setCity(userRequest.getCity() != null ? userRequest.getCity() : user.getCity());
        user.setState(userRequest.getState() != null ? userRequest.getState() : user.getState());
        user.setPostalCode(userRequest.getPostalCode() != null ? userRequest.getPostalCode() : user.getPostalCode());
        user.setCountry(userRequest.getCountry() != null ? userRequest.getCountry() : user.getCountry());
        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getAddress(), updatedUser.getCity(), updatedUser.getState(), updatedUser.getPostalCode(), updatedUser.getCountry());
    }
}
