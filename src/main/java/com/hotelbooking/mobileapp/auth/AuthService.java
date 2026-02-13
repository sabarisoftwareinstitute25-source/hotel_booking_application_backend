package com.hotelbooking.mobileapp.auth;

import com.hotelbooking.mobileapp.user.UserAccount;
import com.hotelbooking.mobileapp.user.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserAccountRepository userRepo;

    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // STEP 1: Forgot Password
    public String forgotPassword(String email) {

        UserAccount user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email);

        tokenRepo.save(token);

        emailService.sendResetEmail(email, token.getToken());

        return "Reset link sent to email.";
    }

    // STEP 2: Reset Password
    public String resetPassword(String tokenValue, String newPassword) {

        PasswordResetToken token = tokenRepo.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (token.isUsed()) {
            throw new RuntimeException("Token already used");
        }

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        UserAccount user = userRepo.findByEmail(token.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        token.setUsed(true);
        tokenRepo.save(token);

        return "Password successfully reset.";
    }
}