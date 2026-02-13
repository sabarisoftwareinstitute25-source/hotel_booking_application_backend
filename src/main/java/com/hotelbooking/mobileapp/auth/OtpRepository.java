package com.hotelbooking.mobileapp.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {

    /**
     * Find the most recent valid OTP for a phone number.
     */
    @Query("SELECT o FROM Otp o WHERE o.phone = :phone AND o.used = false AND o.expiresAt > :now ORDER BY o.createdAt DESC")
    Optional<Otp> findLatestValidOtpByPhone(@Param("phone") String phone, @Param("now") Instant now);

    /**
     * Find OTP by phone and code (not used).
     */
    @Query("SELECT o FROM Otp o WHERE o.phone = :phone AND o.code = :code AND o.used = false")
    Optional<Otp> findByPhoneAndCodeAndUsedFalse(@Param("phone") String phone, @Param("code") String code);
    
    /**
     * Find OTP by phone and code (ignore used status - for debugging).
     */
    @Query("SELECT o FROM Otp o WHERE o.phone = :phone AND o.code = :code ORDER BY o.createdAt DESC")
    Optional<Otp> findByPhoneAndCodeIgnoreUsed(@Param("phone") String phone, @Param("code") String code);
    
    /**
     * Find latest OTP by phone (ignore expiry and used status - for debugging).
     */
    @Query("SELECT o FROM Otp o WHERE o.phone = :phone ORDER BY o.createdAt DESC")
    Optional<Otp> findLatestByPhoneIgnoreStatus(@Param("phone") String phone);

    /**
     * Find existing OTP by phone (for updating instead of creating new).
     */
    @Query("SELECT o FROM Otp o WHERE o.phone = :phone ORDER BY o.createdAt DESC")
    Optional<Otp> findLatestByPhone(@Param("phone") String phone);

    /**
     * Mark all OTPs for a phone as used (when verification succeeds).
     */
    @Modifying
    @Transactional
    @Query("UPDATE Otp o SET o.used = true WHERE o.phone = :phone AND o.used = false")
    void markAllAsUsedByPhone(@Param("phone") String phone);

    /**
     * Delete expired OTPs (cleanup).
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Otp o WHERE o.expiresAt < :now")
    int deleteExpiredOtps(@Param("now") Instant now);
}

