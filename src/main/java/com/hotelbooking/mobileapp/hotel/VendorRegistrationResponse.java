package com.hotelbooking.mobileapp.hotel;

/**
 * Response DTO for vendor registration.
 * Matches frontend expectation.
 */
public class VendorRegistrationResponse {
    private boolean success;
    private String message;
    private String registrationId;
    private Hotel hotel; // Optional

    public VendorRegistrationResponse() {
    }

    public VendorRegistrationResponse(boolean success, String message, String registrationId, Hotel hotel) {
        this.success = success;
        this.message = message;
        this.registrationId = registrationId;
        this.hotel = hotel;
    }

    // Getters and Setters
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

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}

