package com.hotelbooking.mobileapp.auth;

import com.hotelbooking.mobileapp.user.UserAccount;
import jakarta.persistence.*;
import java.time.Instant;

/**
 * OTP entity for storing verification codes.
 * Linked to UserAccount if user exists, otherwise identified by phone.
 */
@Entity
@Table(name = "otps", indexes = {
    @Index(name = "idx_otp_phone", columnList = "phone"),
    @Index(name = "idx_otp_expires_at", columnList = "expires_at"),
    @Index(name = "idx_otp_user", columnList = "user_id")
})
public class Otp {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id; // Matches UserAccount ID if user exists, otherwise phone-based ID

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "name", length = 100)
    private String name; // Store name temporarily during signup

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount user; // Link to UserAccount if exists

    @Column(name = "code", nullable = false, length = 6)
    private String code;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "used", nullable = false)
    private boolean used = false;

    @Column(name = "attempts", nullable = false)
    private int attempts = 0;

    public Otp() {
    }

    public Otp(String phone, String code, Instant expiresAt) {
        this.phone = phone;
        this.code = code;
        this.expiresAt = expiresAt;
        this.createdAt = Instant.now();
        this.used = false;
        this.attempts = 0;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !used && !isExpired() && attempts < 5;
    }

    public void incrementAttempts() {
        this.attempts++;
    }

    public void markAsUsed() {
        this.used = true;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    /**
     * Reset OTP for reuse (update code and reset attempts).
     */
    public void resetOtp(String newCode, Instant newExpiresAt) {
        this.code = newCode;
        this.expiresAt = newExpiresAt;
        this.attempts = 0;
        this.used = false;
        this.createdAt = Instant.now();
    }
}

