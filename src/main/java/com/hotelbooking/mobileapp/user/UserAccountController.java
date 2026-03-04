package com.hotelbooking.mobileapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    // Step 1: Send OTP to mobile
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String fullName = request.get("fullName");
        String mobile = request.get("mobile");

        try {
            userAccountService.sendOtp(fullName, mobile);
            return ResponseEntity.ok("OTP sent to " + mobile);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send OTP: " + e.getMessage());
        }
    }

    // Step 2: Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String mobile = request.get("mobile");
        String otp = request.get("otp");

        boolean verified = userAccountService.verifyOtp(mobile, otp);
        if (verified) {
            return ResponseEntity.ok("Mobile verified! You can now complete registration.");
        } else {
            return ResponseEntity.status(400).body("Invalid or expired OTP.");
        }
    }

    // Step 3: Complete Registration
    @PostMapping("/register")
    public ResponseEntity<String> completeRegistration(@RequestBody UserAccount userAccount) {
        try {
            userAccountService.completeRegistration(userAccount);
            return ResponseEntity.ok("Registration completed successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}