package com.hotelbooking.mobileapp.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @Column(nullable = false, length = 50)
    private String id;

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
    private String phone;

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

    @Transient
    private String confirmPassword;

    @Column(nullable = false)
    private Boolean termsAccepted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // ===== Constructors =====

    public UserAccount() {
    }

    public UserAccount(String id, String fullName, String email, String address,
                       String city, String state, String country,
                       String password, Boolean termsAccepted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.password = password;
        this.termsAccepted = termsAccepted;
    }

    // ===== Automatically set createdAt =====

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    // ===== Getters and Setters =====

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(Boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}