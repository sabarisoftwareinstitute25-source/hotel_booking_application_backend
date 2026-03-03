package com.hotelbooking.mobileapp.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MobileOtpRepository extends JpaRepository<MobileOtp, Long> {
    Optional<MobileOtp> findByMobileAndOtp(String mobile, String otp);
    Optional<MobileOtp> findByMobile(String mobile);
}

