package com.hotelbooking.mobileapp.hotel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "two_star_hotels")
public class TwoStarHotel {

    @Id
    @Column(nullable = false, length = 20)
    private String registrationId;

    @Column(name = "vendor_id", length = 20)
    private String vendorId;

    @Column(name = "hotel_id", nullable = false, length = 20)
    private String hotelId;

    // Step 1: Property Info
    @NotBlank
    @Column(nullable = false, length = 50)
    private String propertyType;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String hotelName;

    @Column(length = 50)
    private String hotelType;

    @Column(length = 4)
    private String yearOfEstablishment;

    @Column(length = 10)
    private String totalRooms;

    // Step 2: Contact
    @NotBlank
    @Column(nullable = false, length = 100)
    private String ownerName;

    @Column(length = 50)
    private String designation;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String mobileNumber;

    @Column(length = 20)
    private String alternateContact;

    @Email
    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String website;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "person_photo_info", columnDefinition = "jsonb")
    private List<String> personPhotoInfo = new ArrayList<>();

    // Step 3: Address
    @NotBlank
    @Column(nullable = false, length = 150)
    private String addressLine1;

    @Column(length = 150)
    private String addressLine2;

    @Column(nullable = false)
    private Boolean isPrimary = true;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String district;

    @Column(length = 50)
    private String state;

    @Column(length = 6)
    private String pinCode;

    // Step 4: Rooms
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    private Boolean extraBedAvailable;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_details", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> roomDetails = new HashMap<>();

    @Column(length = 20)
    private String minTariff;

    @Column(length = 20)
    private String maxTariff;

    // Step 5: Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_amenities", columnDefinition = "jsonb")
    private List<String> roomAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "hotel_facilities", columnDefinition = "jsonb")
    private List<String> hotelFacilities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "food_beverage", columnDefinition = "jsonb")
    private List<String> foodBeverage = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "guest_service", columnDefinition = "jsonb")
    private Map<String, Boolean> guestService = new HashMap<>();

    // Step 6: Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private Boolean petsAllowed;

    // Step 7: Legal & Bank
    @Column(length = 50)
    private String gstNumber;

    @Column(length = 50)
    private String fssaiLicense;

    @Column(length = 50)
    private String tradeLicense;

    @Column(length = 20)
    private String panNumber;

    @Column(length = 100)
    private String accountHolderName;

    @Column(length = 100)
    private String bankName;

    @Column(length = 30)
    private String accountNumber;

    @Column(length = 20)
    private String ifscCode;

    @Column(length = 50)
    private String branch;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles = new HashMap<>();

    private String declarationName;

    private LocalDate declarationDate;

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    @Column(length = 20)
    private String registrationStatus = "PENDING";

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    private LocalDate signedDate;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        if (registrationStatus == null) registrationStatus = "PENDING";
        if (declarationAccepted == null) declarationAccepted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}