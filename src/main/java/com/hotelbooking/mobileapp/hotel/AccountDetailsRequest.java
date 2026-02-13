package com.hotelbooking.mobileapp.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for Account Details form.
 * Stores: Full Name, Business Name, Phone or Email, Password
 */
public class AccountDetailsRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s.'-]+$", message = "Full name can only contain letters, spaces, dots, apostrophes, and hyphens")
    private String fullName;

    @NotBlank(message = "Business name is required")
    @Size(min = 2, max = 150, message = "Business name must be between 2 and 150 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,&'-]+$", message = "Business name can only contain letters, numbers, spaces, and common punctuation")
    private String businessName;

    @NotBlank(message = "Phone or email is required")
    @Size(max = 150, message = "Phone or email cannot exceed 150 characters")
    private String phoneOrEmail;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", 
             message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character (@$!%*?&)")
    private String password;

    public AccountDetailsRequest() {
    }

    public AccountDetailsRequest(String fullName, String businessName, String phoneOrEmail) {
        this.fullName = fullName;
        this.businessName = businessName;
        this.phoneOrEmail = phoneOrEmail;
    }

    // Getters and Setters
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

    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

