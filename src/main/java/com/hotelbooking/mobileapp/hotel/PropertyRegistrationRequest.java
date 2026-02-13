package com.hotelbooking.mobileapp.hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for property registration (hotel or villa).
 * Matches frontend registration form.
 */
public class PropertyRegistrationRequest {

    @NotBlank(message = "Property name is required")
    @Size(max = 150, message = "Property name must not exceed 150 characters")
    private String propertyName;

    @NotBlank(message = "Property type is required")
    @Pattern(regexp = "Hotel|Villa|Service Apartment|Homestay|Resort", 
             message = "Property type must be: Hotel, Villa, Service Apartment, Homestay, or Resort")
    private String propertyType; // Hotel, Villa, Service Apartment, Homestay, Resort

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @NotNull(message = "Star rating is required")
    @Min(value = 1, message = "Star rating must be at least 1")
    @jakarta.validation.constraints.Max(value = 10, message = "Star rating must be at most 10")
    private Integer starRating;

    @NotBlank(message = "Owner name is required")
    @Size(max = 100, message = "Owner name must not exceed 100 characters")
    private String ownerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String ownerEmail;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String ownerPhone;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    public PropertyRegistrationRequest() {
    }

    public PropertyRegistrationRequest(String propertyName, String propertyType, String address, 
                                      String city, String country, Integer starRating,
                                      String ownerName, String ownerEmail, String ownerPhone) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.address = address;
        this.city = city;
        this.country = country;
        this.starRating = starRating;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPhone = ownerPhone;
    }

    // Getters and Setters
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

