package com.hotelbooking.mobileapp.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request for OTP verification (Step 2: Verify OTP).
 */
public class VerifyOtpRequest {
    
    @NotBlank(message = "Phone number is required")
    @Size(max = 20)
    private String phone;
    
    @NotBlank(message = "OTP is required")
    @Pattern(regexp = "^\\d{6}$", message = "OTP must be 6 digits")
    private String otp;

    public VerifyOtpRequest() {
    }

    public VerifyOtpRequest(String phone, String otp) {
        this.phone = phone;
        this.otp = otp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

