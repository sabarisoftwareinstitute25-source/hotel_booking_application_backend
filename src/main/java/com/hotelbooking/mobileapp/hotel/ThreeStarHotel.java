package com.hotelbooking.mobileapp.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "three_star_hotels")
public class ThreeStarHotel {

    @Id
    @Column(nullable = false, length = 14)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    // Property Type
    @Column(nullable = false, length = 50)
    private String propertyType = "Hotel";

    private String hotelCategory = "Three-Star Hotel";

    // Step 1: Hotel Info
    @NotBlank(message = "Hotel name is required")
    @Size(max = 50, message = "Hotel name must not exceed 50 characters")
    @Column(length = 50)
    private String hotelName;

    @Column(name = "hotel_type")
    private String hotelType;

    @Column(name = "year_of_establishment" , length = 4)
    private Integer yearOfEstablishment;

    @Positive(message = "Total rooms must be positive")
    @Column(name = "total_rooms")
    private Integer totalRooms;

    private String registrationNumber;

    // Step 2: Contact
    @NotBlank(message = "Owner name is required")
    @Size(min = 3, max = 50)
    private String ownerName;

    @Column(length = 50)
    private String designation;

    @NotBlank(message = "Mobile number is required")
    @Column(nullable = false, length = 15)
    private String mobileNumber;

    private String alternateContact;

    @Email(message = "Invalid email format")
    @Column(name = "email", length = 50)
    private String email;

    @URL(message = "Invalid URL")
    private String website;

    private String profilePhoto;

    // Address
    @NotBlank(message = "Address Line 1 is required")
    @Column(name = "address_line1", nullable = false)
    private String addressLine1;

    @Column(length = 150)
    private String addressLine2;

    private Boolean isPrimary = true;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Pin code is required")
    @Size(min = 3, max = 10, message = "Invalid pin code")
    @Column(name = "pin_code", nullable = false)
    private String pinCode;

    // Room Configuration
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    //====Standard=====
    @Positive
    private Integer standardNumberOfRooms;
    private Integer standardMaxOccupancy;
    private Boolean standardAc;
    private String standardBedType;
    private Double standardPricePerDay;

    //======Deluxe========
    @Positive
    private Integer deluxeNumberOfRooms;
    private Integer deluxeMaxOccupancy;
    private Boolean deluxeAc;
    private String deluxeBedType;
    private Double deluxePricePerDay;

    //======Suite======
    @Positive
    private Integer suiteNumberOfRooms;
    private Integer suiteMaxOccupancy;
    private Boolean suiteAc;
    private String suiteBedType;
    private Double suitePricePerDay;

    private Boolean extraBedAvailable;

    private Boolean seasonalPricing;

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

    // Check-in Policies
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private String earlyCheckInLateCheckOut;

    private Boolean petsAllowed;

    // Legal
    private String gstNumber;
    private String fssaiLicenseNumber;
    private String tradeLicenseNumber;
    private String panNumber;
    private Boolean fireSafety;

    // Bank Details
    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotNull(message = "Account number is required")
    private Integer accountNumber;

    @NotBlank(message = "IFSC code is required")
    private String ifscCode;

    private String branch;

    @NotBlank(message = "Account type is required")
    private String accountType;

    // Document Required
    private String gstCertificate;
    private String panCard;
    private String tradeLicense;
    private String fssaiCertificate;
    private String fireSafetyCertificate;
    private String cancelledCheque;
    private String hotelRoomPhotos;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles = new HashMap<>();

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String signatureImage;

    private String uploadSignature;

    private String signatoryName;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    protected void generateId() {

        if (this.hotelId == null || this.hotelId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(ThreeStarHotelRepository.class)
                            .findAllHotelIds();

            this.hotelId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIH3", 4, existingIds);
        }

        // Auto set property type if null
        if (this.propertyType == null || this.propertyType.isBlank()) {
            this.propertyType = "Hotel";
        }

        // Auto set hotel category if null
        if (this.hotelCategory == null || this.hotelCategory.isBlank()) {
            this.hotelCategory = "Three-Star Hotel";
        }

        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }


    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}