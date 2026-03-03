package com.hotelbooking.mobileapp.user;

import jakarta.persistence.*;
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

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String fullName;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Size(max = 20)
    @Column(length = 20, unique = true)
    private String mobile;

    private String address;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String city;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String state;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String country;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(nullable = false)
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