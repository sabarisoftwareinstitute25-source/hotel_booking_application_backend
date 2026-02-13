package com.hotelbooking.mobileapp.user;

public class SignupResponse {
    private String message;
    private UserAccount user;

    public SignupResponse() {
    }

    public SignupResponse(String message, UserAccount user) {
        this.message = message;
        this.user = user;
    }

    public SignupResponse(String message) {
        this.message = message;
        this.user = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}

