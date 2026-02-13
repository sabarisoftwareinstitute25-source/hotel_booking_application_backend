package com.hotelbooking.mobileapp.hotel;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Vendor entity for Account Details (Full Name, Business Name, Phone/Email).
 * This stores basic account information before full registration.
 */
@Entity
@Table(name = "vendor_login")
public class Vendor{

    @Id
    @Column(name = "vendor_id", nullable = false, length = 14)
    private String vendorId;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "business_name", nullable = false, length = 50)
    private String businessName;

    @Column(name = "phone",nullable = false, length = 20)
    private String phone;

    @Column(name = "email",nullable = false, length = 50)
    private String email;

    @Column(name = "password", length = 255)
    private String password; // Hashed password (BCrypt) - never returned in API responses

    @Column(name = "status", length = 20)
    private String status = "ACTIVE"; // ACTIVE, INACTIVE

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        updatedAt = Instant.now();
        if (status == null) {
            status = "ACTIVE";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    // Constructors
    public Vendor() {
    }

    public Vendor(String vendorId, String fullName, String businessName, String phone, String email) {
        this.vendorId = vendorId;
        this.fullName = fullName;
        this.businessName = businessName;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
