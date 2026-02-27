package com.hotelbooking.mobileapp.hotel;

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
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "six_star_hotels")
public class SixStarHotel {

    @Id
    @Column(nullable = false)
    private String registrationId;

    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "hotel_id", nullable = false)
    private String hotelId;

    // 1. Hotel Info
    @Column(nullable = false)
    private String hotelName;

    private String hotelType;

    private String yearOfEstablishment;

    private String totalRooms;

    private String nationalRecognition;

    //2.Owner
    private String ownerName;

    private String designation;

    private String managerName;

    private String mobileNumber;

    private String alternateContact;

    @Email
    private String email;

    private String website;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> personPhotoInfo = new ArrayList<>();

    //Address
    @Column(nullable = false)
    private String addressLine1;

    private String addressLine2;

    @Column(nullable = false)
    private Boolean isPrimary = true;

    private String city;

    private String district;

    private String state;

    private String pinCode;

    // ======================================================
    // 4. ACCOMMODATION INVENTORY & PRICING (7-STAR)
    // ======================================================

    // -------- Luxury Room --------
    private Integer luxuryUnits;
    private Integer luxuryMaxOccupancy;
    private String luxuryBedType;
    private Double luxuryPriceFrom;
    private Double luxuryPriceTo;

    // -------- Club Level Room --------
    private Integer clubUnits;
    private Integer clubMaxOccupancy;
    private String clubBedType;
    private Double clubPriceFrom;
    private Double clubPriceTo;

    // -------- Executive Suite --------
    private Integer executiveUnits;
    private Integer executiveMaxOccupancy;
    private String executiveBedType;
    private Double executivePriceFrom;
    private Double executivePriceTo;

    // -------- Presidential Suite --------
    private Integer presidentialUnits;
    private Integer presidentialMaxOccupancy;
    private String presidentialBedType;
    private Double presidentialPriceFrom;
    private Double presidentialPriceTo;

    // -------- Private Villa --------
    private Integer villaUnits;
    private Integer villaMaxOccupancy;
    private String villaBedType;
    private Double villaPriceFrom;
    private Double villaPriceTo;

    // -------- Additional Features --------
    private Boolean personalButlerService;
    private Boolean dynamicPricingEnabled;

    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> roomAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> eilteServices = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> diningAndEvent = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> wellnessAndLeisure = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> guestPrivileges = new ArrayList<>();

    // Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private Boolean earlyCheckInLateCheckOut;
    private Boolean diplomaticProtocals;
    private Boolean petService;

    // 5.Legal
    private String gstNumber;

    private String panNumber;

    private String tradeLicense;

    private String fssaiLicense;

    private Boolean compliance;

    // Bank
    private String accountHolderName;

    private String bankName;

    private String accountNumber;

    private String ifscCode;

    private String branch;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles;

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    private LocalDate declarationDate;
    private String registrationStatus = "PENDING";

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;
    private String signatureName;
    private LocalDate signedDate;

    // Digital Signature
    @Lob
    @Column(columnDefinition = "TEXT")
    private String signatureImage;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        if (this.registrationStatus == null) {
            this.registrationStatus = "PENDING";
        }
    }

}
