package com.hotelbooking.mobileapp.hotel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vendor_login")
public class Vendor {

    @Id
    @Column(name = "vendor_id", length = 14)
    private String vendorId;

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50)
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @NotBlank(message = "Business name is required")
    @Size(max = 50)
    @Column(name = "business_name", length = 50, nullable = false)
    private String businessName;

    @NotBlank(message = "Phone number is required")
    @Column(name = "phone", nullable = false, unique = true, length = 15)
    private String phone;

    @Email(message = "Invalid email format")
    @Column(name = "email", length = 50, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    @Column(name = "password", length = 100, nullable = false)
    private String password; // BCrypt hashed password

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();

        if (status == null) {
            status = "ACTIVE";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}