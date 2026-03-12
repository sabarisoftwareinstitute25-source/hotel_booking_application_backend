package com.hotelbooking.mobileapp.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "normal_hotels")
public class HotelVendor {

    @Id
    @Column(nullable = false, length = 14)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    // Property
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
    private String profilePhoto;

    @NotBlank(message = "Owner name is required")
    @Size(min = 3, max = 50)
    private String ownerName;

    @NotBlank(message = "Mobile number is required")
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    private String alternateContact;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "landline_numbers", columnDefinition = "jsonb")
    private List<String> landlineNumbers;

    @Email(message = "Invalid email format")
    @Column(name = "email", length = 50)
    private String email;

    @URL(message = "Invalid URL")
    private String website;

    // Address
    @NotBlank(message = "Address Line 1 is required")
    @Column(name = "address_line1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

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

    @Column(name = "landmark", length = 250)
    private String landmark;

    // Rooms
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    // Single Room Details
    @Positive
    private Integer singleNumberOfRooms;
    private Integer singleMaxOccupancy;
    private Double singlePricePerNight;
    private String singleAcOrNonAc;
    private Boolean singleExtraBedAvailable;

    // Double Room Details
    @Positive
    private Integer doubleNumberOfRooms;
    private Integer doubleMaxOccupancy;
    private Double doublePricePerNight;
    private String doubleAcOrNonAc;
    private Boolean doubleExtraBedAvailable;

    // Deluxe Room Details
    @Positive
    private Integer deluxeNumberOfRooms;
    private Integer deluxeMaxOccupancy;
    private Double deluxePricePerNight;
    private String deluxeAcOrNonAc;
    private Boolean deluxeExtraBedAvailable;

    // Suite Room Detail
    @Positive
    private Integer suiteNumberOfRooms;
    private Integer suiteMaxOccupancy;
    private Double suitePricePerNight;
    private String suiteAcOrNonAc;
    private Boolean suiteExtraBedAvailable;

    // Family Room Details
    @Positive
    private Integer familyNumberOfRooms;
    private Integer familyMaxOccupancy;
    private Double familyPricePerNight;
    private String familyAcOrNonAc;
    private Boolean familyExtraBedAvailable;

    // Executive Room Details
    @Positive
    private Integer executiveNumberOfRooms;
    private Integer executiveMaxOccupancy;
    private Double executivePricePerNight;
    private String executiveAcOrNonAc;
    private Boolean executiveExtraBedAvailable;

    @Positive(message = "Minimum tariff must be positive")
    private Double minTariff;

    @Positive(message = "Maximum tariff must be positive")
    private Double maxTariff;

    private Boolean extraBedAvailable;

    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "basic_amenities", columnDefinition = "jsonb")
    private List<String> basicAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "hotel_facilities" , columnDefinition = "jsonb")
    private List<String> hotelFacilities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "food_services" , columnDefinition = "jsonb")
    private List<String> foodServices = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_amenities" , columnDefinition = "jsonb")
    private List<String> additionalAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "custom_amenities" , columnDefinition = "jsonb")
    private List<String> customAmenities = new ArrayList<>();

    // Legal
    @Size(max = 30, message = "Invalid GST number length")
    private String gstNumber;

    @Size(max = 30, message = "Invalid FSSAI license number length")
    private String fssaiLicenseNumber;

    @Size(max = 30, message = "Invalid trade license number")
    private String tradeLicenseNumber;

    @Size(max = 20, message = "Invalid ID number")
    private String aadhaarNumber;

    // Bank
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
    private String accountType; // SAVINGS / CURRENT

    // Documents Required
    private String fssaiCertificate;
    private String gstCertificate;
    private String tradeLicense;
    private String hotelPhoto;
    private String cancelledCheque;
    private String ownerIdProof;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles = new HashMap<>();

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String signatureImage;

    private String uploadSignature;

    private String declarationName;
    private LocalDateTime declarationDate;


    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    protected void generateId() {

        if (this.hotelId == null || this.hotelId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(HotelVendorRepository.class)
                            .findAllHotelIds();

            this.hotelId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIHN", 4, existingIds);
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