package com.hotelbooking.mobileapp.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetOtpRepository
        extends JpaRepository<PasswordResetOtp, String> {

    Optional<PasswordResetOtp>
    findTopByEmailOrderByExpiryTimeDesc(String email);
}