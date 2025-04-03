package com.application.ecommerce.user;

import com.application.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
