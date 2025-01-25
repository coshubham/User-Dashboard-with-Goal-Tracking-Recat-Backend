package com.shubham.user_backend.reposiroty;

import com.shubham.user_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Add a custom query method to find user by email
    Optional<User> findByEmail(String email);
}
