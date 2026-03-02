package com.hotelbooking.mobileapp.hotel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "four_star_hotels")
public class FourStarHotel {

    @Id
    @Column(nullable = false, length = 20)
    private String registrationId;

    @Column(name = "vendor_id", length = 20)
    private String vendorId;


    @Column(name = "property_type", nullable = false, length = 50)
    private String propertyType;

    @Column(name = "hotel_name", nullable = false, length = 100)
    private String hotelName;

    @Column(name = "hotel_type", length = 50)
    private String hotelType;

    @Column(name = "year_of_establishment", length = 4)
    private String yearOfEstablishment;

    @Column(name = "total_rooms", length = 10)
    private String totalRooms;

    // Contact
    @Column(name = "owner_name", nullable = false, length = 100)
    private String ownerName;

    @Column(nullable = false, length = 100)
    private String designation;

    @Column(name = "mobile_number", nullable = false, length = 20)
    private String mobileNumber;

    @Column(name = "alternate_contact", length = 20)
    private String alternateContact;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String website;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "person_photo_info", columnDefinition = "jsonb")
    private List<String> personPhotoInfo = new ArrayList<>();

    // Address
    @Column(name = "address_line1", nullable = false, length = 250)
    private String addressLine1;

    @Column(name = "address_line2", length = 250)
    private String addressLine2;

    @Column(nullable = false)
    private Boolean isPrimary = true;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String district;

    @Column(length = 100)
    private String state;

    @Column(name = "pin_code", length = 10)
    private String pinCode;

    // Room Configuration
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    private Boolean extraBedAvailable;
    private Boolean seasonalPricing;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_details", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> roomDetails;

    // Amenities
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
    private List<String> guestService = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "wellness", columnDefinition = "jsonb")
    private List<String> wellness = new ArrayList<>();

    // Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private Boolean earlyCheckInLateCheckOut;
    private Boolean petsAllowed;

    // Legal
    @Column(length = 50)
    private String gstNumber;

    @Column(length = 50)
    private String fssaiLicense;

    @Column(length = 50)
    private String tradeLicense;

    @Column(length = 50)
    private String panNumber;

    private Boolean fireSafety;
    private Boolean starCertification;

    // Bank
    @Column(length = 100)
    private String accountHolderName;

    @Column(length = 100)
    private String bankName;

    @Column(length = 30)
    private String accountNumber;

    @Column(length = 20)
    private String ifscCode;

    @Column(length = 100)
    private String branch;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles;

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    private LocalDate declarationDate;

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
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}