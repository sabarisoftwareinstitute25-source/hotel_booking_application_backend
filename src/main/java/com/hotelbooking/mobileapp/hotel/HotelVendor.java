package com.hotelbooking.mobileapp.hotel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "normal_hotels")
public class HotelVendor {

    @Id
    @Column(nullable = false, length = 16)
    private String registrationId;

    @Column(name = "vendor_id", length = 16)
    private String vendorId;

    @Column(name = "hotel_id", nullable = false, length = 16)
    private String hotelId;

    // Property
    @NotBlank
    @Column(name = "property_type", nullable = false, length = 50)
    private String propertyType;

    @NotBlank
    @Column(name = "hotel_name", nullable = false, length = 50)
    private String hotelName;

    @Column(name = "hotel_type", length = 50)
    private String hotelType;

    @Column(name = "year_of_establishment", length = 4)
    private String yearOfEstablishment;

    @Column(name = "total_rooms", length = 10)
    private String totalRooms;

    // Contact
    @NotBlank
    @Column(name = "owner_name", nullable = false, length = 50)
    private String ownerName;

    @NotBlank
    @Column(name = "mobile_number", nullable = false, length = 20)
    private String mobileNumber;

    @Column(name = "alternate_contact", length = 20)
    private String alternateContact;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "landline_numbers", columnDefinition = "jsonb")
    private List<String> landlineNumbers = new ArrayList<>();

    @Email
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "website", length = 250)
    private String website;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "person_photo_info", columnDefinition = "jsonb")
    private List<String> personPhotoInfo = new ArrayList<>();

    // Address
    @NotBlank
    @Column(name = "address_line1", nullable = false, length = 100)
    private String addressLine1;

    @Column(name = "address_line2", length = 100)
    private String addressLine2;

    @NotBlank
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "district", length = 50)
    private String district;

    @NotBlank
    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @NotBlank
    @Column(name = "pin_code", nullable = false, length = 6)
    private String pinCode;

    @Column(name = "landmark", length = 250)
    private String landmark;

    // Rooms
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_details", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> roomDetails = new HashMap<>();

    @Column(name = "min_tariff", length = 20)
    private String minTariff;

    @Column(name = "max_tariff", length = 20)
    private String maxTariff;

    private Boolean extraBedAvailable;

    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "basic_amenities", columnDefinition = "jsonb")
    private List<String> basicAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "hotel_facilities", columnDefinition = "jsonb")
    private List<String> hotelFacilities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "food_services", columnDefinition = "jsonb")
    private List<String> foodServices = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_amenities", columnDefinition = "jsonb")
    private List<String> additionalAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "custom_amenities", columnDefinition = "jsonb")
    private List<String> customAmenities = new ArrayList<>();

    // Legal
    private String gstNumber;
    private String fssaiLicense;
    private String tradeLicense;
    private String panNumber;
    private String aadharNumber;

    // Bank
    @NotBlank
    private String accountHolderName;

    @NotBlank
    private String bankName;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String ifscCode;

    private String branch;

    @Column(name = "account_type", nullable = false)
    private String accountType; // SAVINGS / CURRENT

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles = new HashMap<>();

    private String signatureName;
    private String declarationName;
    private Instant declarationDate;

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    @Column(length = 20)
    private String registrationStatus = "PENDING";

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

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