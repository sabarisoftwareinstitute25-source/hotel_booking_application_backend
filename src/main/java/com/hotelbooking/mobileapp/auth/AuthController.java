package com.hotelbooking.mobileapp.auth;

import com.hotelbooking.mobileapp.user.UserAccountService;
import com.hotelbooking.mobileapp.user.VerifyOtpResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private JavaMailSender mailSender;

    // ========================
    // EMAIL OTP ENDPOINTS
    // ========================

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP verified successfully");
        return ResponseEntity.ok(response);
    }

    // ========================
    // PHONE OTP ENDPOINTS
    // ========================

    @PostMapping("/verify-phone-otp")
    public ResponseEntity<VerifyOtpResponse> verifyPhoneOtp(@Valid @RequestBody PhoneVerifyOtpRequest request) {
        try {
            String phone = request.getPhone();
            String otp = request.getOtp();

            if (phone == null || otp == null) {
                return ResponseEntity.badRequest()
                        .body(new VerifyOtpResponse(false, "Phone and OTP are required", false));
            }

            com.hotelbooking.mobileapp.auth.Otp verifiedOtp = otpService.verifyOtpInternal(phone, otp);

            if (verifiedOtp != null) {
                String name = verifiedOtp.getName();
                return ResponseEntity.ok(new VerifyOtpResponse(true, "OTP verified successfully", true, name));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new VerifyOtpResponse(false, "Invalid or expired OTP", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new VerifyOtpResponse(false, "Failed to verify OTP: " + e.getMessage(), false));
        }
    }

    // ========================
    // PHONE OTP REQUEST CLASSES
    // ========================

    public static class EmailVerifyOtpRequest {
        @NotBlank
        private String email;
        @NotBlank
        private String otp;

        // getters & setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
    }

    public static class PhoneVerifyOtpRequest {
        @NotBlank
        private String phone;
        @NotBlank
        private String otp;

        // getters & setters
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        Map<String, String> response = new HashMap<>();

        try {
            // Call your service to send OTP to email
            authService.sendForgotPasswordOtp(request.getEmail());

            response.put("message", "OTP sent successfully to " + request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to send OTP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Request body class
    public static class ForgotPasswordRequest {
        @NotBlank
        private String email;

        // getter and setter
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        Map<String, String> response = new HashMap<>();

        try {
            // Call your AuthService to update password
            authService.resetPassword(request.getEmail(), request.getNewPassword());

            response.put("message", "Password reset successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to reset password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Request body class
    public static class ResetPasswordRequest {
        @NotBlank
        private String email;

        @NotBlank
        private String newPassword;

        // getters & setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}