package com.shubham.user_backend.controller;

import com.shubham.user_backend.model.User;
import com.shubham.user_backend.controller.Login;
import com.shubham.user_backend.reposiroty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.shubham.user_backend.exception.UserNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Endpoint to create a new user
    @PostMapping("/user")
    public User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    // Endpoint to get all users (for debugging purposes)
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Login endpoint
    @PostMapping("/login")
    public String login(@RequestBody Login loginRequest) {
        // Fetch user by email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        // Check if user exists
        if (!userOptional.isPresent()) {
            return "User not found";
        }

        User user = userOptional.get();

        // Check if password matches (assumes passwords are stored in plain text, which is not recommended)
        // In a real-world scenario, you should hash and compare hashed passwords
        if (user.getPassword().equals(loginRequest.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id "+id+" has been deleted successfully.";
    }
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


}



