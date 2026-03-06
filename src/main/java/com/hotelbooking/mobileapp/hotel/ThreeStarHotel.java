package com.hotelbooking.mobileapp.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
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
    @Column(nullable = false, length = 16)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    // Property Type
    @NotBlank
    @Size(max = 50)
    @Column(name = "property_type", nullable = false, length = 50)
    private String propertyType;

    // Step 1: Hotel Info
    @NotBlank
    @Size(max = 50)
    @Column(name = "hotel_name", nullable = false, length = 50)
    private String hotelName;

    @Size(max = 50)
    @Column(name = "hotel_type", length = 50)
    private String hotelType;

    @Size(max = 4)
    @Column(name = "year_of_establishment", length = 4)
    private String yearOfEstablishment;

    @Size(max = 10)
    @Column(name = "total_rooms", length = 10)
    private String totalRooms;

    // Step 2: Contact
    @NotBlank
    @Size(max = 50)
    @Column(name = "owner_name", nullable = false, length = 50)
    private String ownerName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "designation", nullable = false, length = 100)
    private String designation;

    @NotBlank
    @Column(name = "mobile_number", nullable = false, length = 20)
    private String mobileNumber;

    @Column(name = "alternate_contact", length = 20)
    private String alternateContact;

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
    @Column(name = "address_line1", nullable = false, length = 250)
    private String addressLine1;

    @Column(name = "address_line2", length = 250)
    private String addressLine2;

    private Boolean isPrimary = true;

    @NotBlank
    @Column(length = 50)
    private String city;

    @NotBlank
    @Column(length = 50)
    private String district;

    @NotBlank
    @Column(length = 50)
    private String state;

    @NotBlank
    @Column(name = "pin_code", length = 10)
    private String pinCode;

    // Room Configuration
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    //====Standard=====
    private Integer standardNumberOfRooms;
    private String standardMaxOccupancy;
    private Boolean standardAc;
    private String standardBedType;
    private Double standardPricePerDay;

    //======Deluxe========
    private Integer deluxeNumberOfRooms;
    private String deluxeMaxOccupancy;
    private Boolean deluxeAc;
    private String deluxeBedType;
    private Double deluxePricePerDay;

    //======Suite======
    private Integer suiteNumberOfRooms;
    private String suiteMaxOccupancy;
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

    private Boolean earlyCheckInLateCheckOut;

    private Boolean petsAllowed;

    // Legal
    @Column(length = 50, nullable = false)
    private String gstNumber;

    @Column(length = 50)
    private String fssaiLicenseNumber;

    @Column(length = 50)
    private String tradeLicenseNumber;

    @Column(length = 50)
    private String panNumber;

    private Boolean fireSafety;

    // Bank Details
    @NotBlank
    @Column(length = 50, nullable = false)
    private String accountHolderName;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String bankName;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String accountNumber;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String ifscCode;

    @NotBlank
    @Column(length = 50)
    private String branch;

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

    private LocalDate declarationDate;

    private String signatoryName;

    private LocalDate Date;

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

        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }


    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}