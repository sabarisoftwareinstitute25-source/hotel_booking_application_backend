package com.hotelbooking.mobileapp.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @Column(nullable = false, length = 50)
    private String userId;

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50)
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Email(message = "Invalid email format")
    @Column(name = "email", length = 50, unique = true)
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Column(name = "mobile", nullable = false, unique = true, length = 15)
    private String mobile;

    @NotBlank(message = "Address is required")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean termsAccepted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    private boolean active;

    // ===== Automatically set createdAt =====

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

}