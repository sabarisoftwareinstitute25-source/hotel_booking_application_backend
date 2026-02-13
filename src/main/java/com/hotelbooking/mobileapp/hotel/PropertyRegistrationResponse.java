package com.hotelbooking.mobileapp.hotel;

/**
 * Response DTO for property registration.
 */
public class PropertyRegistrationResponse {
    
    private boolean success;
    private String message;
    private Hotel hotel; // The registered hotel/property
    private String propertyId;

    public PropertyRegistrationResponse() {
    }

    public PropertyRegistrationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public PropertyRegistrationResponse(boolean success, String message, Hotel hotel) {
        this.success = success;
        this.message = message;
        this.hotel = hotel;
        this.propertyId = hotel != null ? hotel.getId() : null;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        this.propertyId = hotel != null ? hotel.getId() : null;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
}

