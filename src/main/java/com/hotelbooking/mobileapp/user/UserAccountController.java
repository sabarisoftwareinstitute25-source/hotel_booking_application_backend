package com.hotelbooking.mobileapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    @Autowired
    private UserAccountRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // =========================
    // 1️⃣ Register User
    // =========================
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserAccount user) {

        if (!user.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body("Passwords do not match");
        }

        if (userRepo.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Email already registered");
        }

        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(null);

        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // =========================
    // 2️⃣ Get All Users
    // =========================
    @GetMapping
    public List<UserAccount> getAllUsers() {
        return userRepo.findAll();
    }

    // =========================
    // 3️⃣ Get User By ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {

        return userRepo.findById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // 4️⃣ Update User
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @RequestBody UserAccount updatedUser) {

        return userRepo.findById(id)
                .map(user -> {

                    user.setFullName(updatedUser.getFullName());
                    user.setPhone(updatedUser.getPhone());
                    user.setAddress(updatedUser.getAddress());
                    user.setCity(updatedUser.getCity());
                    user.setState(updatedUser.getState());
                    user.setCountry(updatedUser.getCountry());

                    userRepo.save(user);

                    return ResponseEntity.ok("User updated successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // 5️⃣ Delete User
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {

        if (!userRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepo.deleteById(id);

        return ResponseEntity.ok("User deleted successfully");
    }
}