package com.hotelbooking.mobileapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class OtpTestController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/send-otp")
    public String sendOtp(@RequestParam String phone, @RequestParam String fullName) {
        userAccountService.sendOtp(fullName, phone);
        return "OTP sent to " + phone;
    }
}