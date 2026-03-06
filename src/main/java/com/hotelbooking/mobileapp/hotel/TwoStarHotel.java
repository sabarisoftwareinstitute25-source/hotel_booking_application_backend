package com.hotelbooking.mobileapp.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "two_star_hotels")
public class TwoStarHotel {

    @Id
    @Column(nullable = false, length = 20)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

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

    private String profilePhoto;

    // Step 3: Address
    @NotBlank
    @Column(nullable = false, length = 150)
    private String addressLine1;

    @Column(length = 150)
    private String addressLine2;

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

    // Single Room Details
    private Integer singleNumberOfRooms;
    private Integer singleMaxOccupancy;
    private Double singlePricePerNight;
    private String singleAcOrNonAc;

    // Double Room Details
    private Integer doubleNumberOfRooms;
    private Integer doubleMaxOccupancy;
    private Double doublePricePerNight;
    private String doubleAcOrNonAc;

    // Deluxe Room Details
    private Integer deluxeNumberOfRooms;
    private Integer deluxeMaxOccupancy;
    private Double deluxePricePerNight;
    private String deluxeAcOrNonAc;

    private Boolean extraBedAvailable;

//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(name = "room_details", columnDefinition = "jsonb")
//    private Map<String, Map<String, Object>> roomDetails = new HashMap<>();

    @Column(length = 20)
    private Double minPricePerDay;

    @Column(length = 20)
    private Double maxPricePerDay;

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
    private List<String> guestService = new ArrayList<>();

    // Step 6: Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private String idProofRequired;
    private String uploadAadhaarCard;
    private String uploadPassport;
    private String uploadDrivingLicense;

    private Boolean petsAllowed;

    // Step 7: Legal & Bank
    @Column(length = 50)
    private String gstNumber;

    @Column(length = 50)
    private String fssaiLicenseNumber;

    @Column(length = 50)
    private String tradeLicenseNumber;

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

    private String accountType;

    // Documents to be Submitted
    private String gstCertificate;
    private String tradeLicense;
    private String fssaiCertificate;
    private String cancelledCheque;
    private String hotelRegistrationCertificate;
    private String roomPropertyPhoto;

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

    private LocalDate declarationDate;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;


    @PrePersist
    protected void generateId() {

        if (this.hotelId == null || this.hotelId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(TwoStarHotelRepository.class)
                            .findAllHotelIds();

            this.hotelId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIH2", 4, existingIds);
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