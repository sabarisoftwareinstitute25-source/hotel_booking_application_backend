package com.hotelbooking.mobileapp.user;

/**
 * Response for sending OTP.
 */
public class SendOtpResponse {
    private boolean success;
    private String message;
    private String phone;

    public SendOtpResponse() {
    }

    public SendOtpResponse(boolean success, String message, String phone) {
        this.success = success;
        this.message = message;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

