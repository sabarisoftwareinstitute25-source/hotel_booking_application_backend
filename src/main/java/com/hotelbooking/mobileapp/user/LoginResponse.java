package com.hotelbooking.mobileapp.user;

public class LoginResponse {
    private String token;
    private UserAccount user;

    public LoginResponse() {
    }

    public LoginResponse(String token, UserAccount user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}

