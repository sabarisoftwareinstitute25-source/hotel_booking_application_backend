package com.hotelbooking.mobileapp.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "four_star_hotels")
public class FourStarHotel {

    @Id
    @Column(nullable = false, length = 20)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    @Column(nullable = false, length = 50)
    private String propertyType = "Hotel";

    @NotBlank(message = "Hotel name is required")
    @Size(max = 50, message = "Hotel name must not exceed 50 characters")
    @Column(length = 50)
    private String hotelName;

    @Column(name = "hotel_type")
    private String hotelType;

    @Column(name = "year_of_establishment", length = 4)
    private Integer yearOfEstablishment;

    @Positive(message = "Total rooms must be positive")
    @Column(name = "total_rooms")
    private Integer totalRooms;

    // Contact
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

    @Column(name = "address_line2", length = 250)
    private String addressLine2;

    @Column(nullable = false)
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

    // Superior Room Details
    @Positive
    private Integer superiorNumberOfRooms;
    private Integer superiorMaxOccupancy;
    private Double superiorPricePerDay;
    private Boolean superiorAc;
    private String superiorBedSize;

    // Deluxe Room Details
    @Positive
    private Integer deluxeNumberOfRooms;
    private Integer deluxeMaxOccupancy;
    private Double deluxePricePerDay;
    private String deluxeBedType;
    private Boolean deluxeAc;

    // Executive Room Details
    @Positive
    private Integer executiveNumberOfRooms;
    private Integer executiveMaxOccupancy;
    private Double executivePricePerDay;
    private String executiveBedType;
    private Boolean executiveAc;

    // Suite Room Details
    @Positive
    private Integer suiteNumberOfRooms;
    private Integer suiteMaxOccupancy;
    private Double suitePricePerDay;
    private String suiteBedType;
    private Boolean suiteAc;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "wellness", columnDefinition = "jsonb")
    private List<String> wellness = new ArrayList<>();

    // Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "early_checkIn_late_checkOut", columnDefinition = "jsonb")
    private List<String> earlyCheckInLateCheckOut = new ArrayList<>();

    private Boolean petsAllowed;

    // Legal
    @Size(max = 30, message = "Invalid GST number length")
    private String gstNumber;

    @Size(max = 30, message = "Invalid FSSAI license number length")
    private String fssaiLicenseNumber;

    @Size(max = 30, message = "Invalid trade license number")
    private String tradeLicenseNumber;

    @Column(length = 10)
    private String panNumber;

    private Boolean fireSafety;
    private Boolean starCertification;

    // Bank
    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Account number is required")
    private Integer accountNumber;

    @NotBlank(message = "IFSC code is required")
    private String ifscCode;

    private String branch;

    @NotBlank(message = "Account type is required")
    private String accountType;

    // Documents Required
    private String gstCertificate;
    private String panCard;
    private String tradeLicense;
    private String fssaiCertificate;
    private String fireSafetyCertificate;
    private String starClassificationCertificate;
    private String cancelledCheque;
    private String hotelRoomPhotos;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles;

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String signatureImage;

    private String uploadSignature;

    private String signatoryName;

    private LocalDate Date;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    protected void generateId() {

        if (this.hotelId == null || this.hotelId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(FourStarHotelRepository.class)
                            .findAllHotelIds();

            this.hotelId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIH4", 4, existingIds);
        }

        // Auto set property type if null
        if (this.propertyType == null || this.propertyType.isBlank()) {
            this.propertyType = "Hotel";
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