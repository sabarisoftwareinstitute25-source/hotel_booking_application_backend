package com.hotelbooking.mobileapp.promotion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Promotion/Offer entity for exclusive deals.
 * Matches frontend offer cards: Long Stay Discount, Early Bird, etc.
 */
@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @Column(nullable = false, length = 20)
    private String id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String title; // e.g., "Long Stay Discount", "Early Bird"

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage; // e.g., 15.00 for 15%

    @Column(name = "discount_amount")
    private BigDecimal discountAmount; // Fixed discount amount

    @Size(max = 50)
    @Column(name = "promotion_type", length = 50)
    private String promotionType; // "LONG_STAY", "EARLY_BIRD", "SEASONAL", etc.

    @Column(name = "min_nights")
    private Integer minNights; // Minimum nights for promotion

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Size(max = 200)
    @Column(name = "image_url", length = 200)
    private String imageUrl; // URL for promotion image

    @Size(max = 50)
    @Column(name = "color", length = 50)
    private String color; // Card color (e.g., "purple", "blue", "orange")

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "expires_at")
    private Instant expiresAt; // When promotion expires

    public Promotion() {
    }

    public Promotion(String id, String title, String description, String promotionType, String color) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.promotionType = promotionType;
        this.color = color;
        this.isActive = true;
        this.createdAt = Instant.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getMinNights() {
        return minNights;
    }

    public void setMinNights(Integer minNights) {
        this.minNights = minNights;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
}

