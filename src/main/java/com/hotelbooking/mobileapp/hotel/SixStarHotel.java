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
@Table(name = "six_star_hotels")
public class SixStarHotel {

    @Id
    @Column(nullable = false, length = 20)
    private String registrationId;

    @Column(name = "vendor_id", length = 20)
    private String vendorId;

    @Column(name = "hotel_id", nullable = false, length = 20)
    private String hotelId;

    // Hotel Info
    @Column(name = "hotel_name", nullable = false, length = 150)
    private String hotelName;

    @Column(length = 50)
    private String hotelType;

    @Column(name = "year_of_establishment", length = 4)
    private String yearOfEstablishment;

    @Column(name = "total_rooms", length = 10)
    private String totalRooms;

    @Column(name = "national_recognition", length = 150)
    private String nationalRecognition;

    // Owner
    @Column(name = "owner_name", length = 150)
    private String ownerName;

    @Column(length = 100)
    private String designation;

    @Column(name = "manager_name", length = 150)
    private String managerName;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "alternate_contact", length = 20)
    private String alternateContact;

    @Column(length = 150)
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

    // Accommodation
    private Integer luxuryUnits;
    private Integer luxuryMaxOccupancy;
    private String luxuryBedType;
    private Double luxuryPriceFrom;
    private Double luxuryPriceTo;

    private Integer clubUnits;
    private Integer clubMaxOccupancy;
    private String clubBedType;
    private Double clubPriceFrom;
    private Double clubPriceTo;

    private Integer executiveUnits;
    private Integer executiveMaxOccupancy;
    private String executiveBedType;
    private Double executivePriceFrom;
    private Double executivePriceTo;

    private Integer presidentialUnits;
    private Integer presidentialMaxOccupancy;
    private String presidentialBedType;
    private Double presidentialPriceFrom;
    private Double presidentialPriceTo;

    private Integer villaUnits;
    private Integer villaMaxOccupancy;
    private String villaBedType;
    private Double villaPriceFrom;
    private Double villaPriceTo;

    private Boolean personalButlerService;
    private Boolean dynamicPricingEnabled;

    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_amenities", columnDefinition = "jsonb")
    private List<String> roomAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "elite_services", columnDefinition = "jsonb")
    private List<String> eliteServices = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dining_and_event", columnDefinition = "jsonb")
    private List<String> diningAndEvent = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "wellness_and_leisure", columnDefinition = "jsonb")
    private List<String> wellnessAndLeisure = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "guest_privileges", columnDefinition = "jsonb")
    private List<String> guestPrivileges = new ArrayList<>();

    // Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private Boolean earlyCheckInLateCheckOut;
    private Boolean diplomaticProtocols;
    private Boolean petService;

    // Legal
    @Column(length = 50)
    private String gstNumber;

    @Column(length = 50)
    private String panNumber;

    @Column(length = 50)
    private String tradeLicense;

    @Column(length = 50)
    private String fssaiLicense;

    private Boolean compliance;

    // Bank
    @Column(length = 150)
    private String accountHolderName;

    @Column(length = 150)
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
    private String signatureName;
    private LocalDate signedDate;

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

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}