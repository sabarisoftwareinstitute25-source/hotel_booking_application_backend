package com.hotelbooking.mobileapp.otp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;  // Primary key — required!

    private String fullName;
    private String mobile;
    private String otp;
    private LocalDateTime expiryTime;
    private boolean verified = false;
}
