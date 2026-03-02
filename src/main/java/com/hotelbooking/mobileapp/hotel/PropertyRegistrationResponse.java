package com.hotelbooking.mobileapp.hotel;

/**
 * Response DTO for property registration.
 */
public class PropertyRegistrationResponse {
    
    private boolean success;
    private String message;
    private String propertyId;

    public PropertyRegistrationResponse() {
    }

    public PropertyRegistrationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
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


    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
}

