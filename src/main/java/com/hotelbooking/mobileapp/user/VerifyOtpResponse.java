package com.hotelbooking.mobileapp.user;

/**
 * Response for OTP verification.
 */
public class VerifyOtpResponse {
    private boolean success;
    private String message;
    private boolean verified;
    private String name; // User's name from OTP record
    private boolean userExists; // Whether user account already exists
    private String userId; // User ID if exists

    public VerifyOtpResponse() {
    }

    public VerifyOtpResponse(boolean success, String message, boolean verified) {
        this.success = success;
        this.message = message;
        this.verified = verified;
        this.userExists = false;
    }

    public VerifyOtpResponse(boolean success, String message, boolean verified, String name) {
        this.success = success;
        this.message = message;
        this.verified = verified;
        this.name = name;
        this.userExists = false;
    }

    public VerifyOtpResponse(boolean success, String message, boolean verified, String name, boolean userExists, String userId) {
        this.success = success;
        this.message = message;
        this.verified = verified;
        this.name = name;
        this.userExists = userExists;
        this.userId = userId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUserExists() {
        return userExists;
    }

    public void setUserExists(boolean userExists) {
        this.userExists = userExists;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

