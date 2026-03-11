package com.hotelbooking.mobileapp.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @Column(nullable = false, length = 16)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

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
    private Integer yearOfEstablishment;

    @Column(name = "total_rooms", length = 10)
    private Integer totalRooms;

    // Contact
    private String profilePhoto;

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

    @Column(nullable = false)
    private String country;

    @NotBlank
    @Column(name = "pin_code", nullable = false, length = 6)
    private String pinCode;

    @Column(name = "landmark", length = 250)
    private String landmark;

    // Rooms
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    // Single Room Details
    private Integer singleNumberOfRooms;
    private Integer singleMaxOccupancy;
    private Double singlePricePerNight;
    private String singleAcOrNonAc;
    private Boolean singleExtraBedAvailable;

    // Double Room Details
    private Integer doubleNumberOfRooms;
    private Integer doubleMaxOccupancy;
    private Double doublePricePerNight;
    private String doubleAcOrNonAc;
    private Boolean doubleExtraBedAvailable;

    // Deluxe Room Details
    private Integer deluxeNumberOfRooms;
    private Integer deluxeMaxOccupancy;
    private Double deluxePricePerNight;
    private String deluxeAcOrNonAc;
    private Boolean deluxeExtraBedAvailable;

    // Suite Room Details
    private Integer suiteNumberOfRooms;
    private Integer suiteMaxOccupancy;
    private Double suitePricePerNight;
    private String suiteAcOrNonAc;
    private Boolean suiteExtraBedAvailable;

    // Family Room Details
    private Integer familyNumberOfRooms;
    private Integer familyMaxOccupancy;
    private Double familyPricePerNight;
    private String familyAcOrNonAc;
    private Boolean familyExtraBedAvailable;

    // Executive Room Details
    private Integer executiveNumberOfRooms;
    private Integer executiveMaxOccupancy;
    private Double executivePricePerNight;
    private String executiveAcOrNonAc;
    private Boolean executiveExtraBedAvailable;

    @Column(name = "min_tariff", length = 20)
    private Double minTariff;

    @Column(name = "max_tariff", length = 20)
    private Double maxTariff;

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
    private String fssaiLicenseNumber;
    private String tradeLicenseNumber;
    private String aadhaarNumber;

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

    // Documents Required
    private String fssaiCertificate;
    private String gstCertificate;
    private String tradeLicense;
    private String hotelPhoto;
    private String cancelledCheque;
    private String ownerIdProof;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
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

        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}