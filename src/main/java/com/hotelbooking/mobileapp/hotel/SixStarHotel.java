package com.hotelbooking.mobileapp.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "six_star_hotels")
public class SixStarHotel {

    @Id
    @Column(nullable = false, length = 20)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    @Column(nullable = false, length = 50)
    private String propertyType = "Hotel";
    // Hotel Info
    @Column(name = "hotel_name", nullable = false, length = 150)
    private String hotelName;

    @Column(length = 50)
    private String hotelType;

    @Column(name = "year_of_establishment", length = 4)
    private Integer yearOfEstablishment;

    @Column(name = "total_rooms", length = 10)
    private Integer totalRooms;

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
    private String state;

    @Column(nullable = false)
    private String country;

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

    private String earlyCheckInLateCheckOut;
    private Boolean diplomaticProtocols;
    private Boolean petService;

    // Legal
    @Column(length = 50)
    private String gstNumber;

    @Column(length = 50)
    private String panNumber;

    @Column(length = 50)
    private String tradeLicenseNumber;

    @Column(length = 50)
    private String fssaiLicenseNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "compliance", columnDefinition = "jsonb")
    private List<String> compliance = new ArrayList<>();

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

    private String accountType;

    // Documents Required
    private String gstCertificate;
    private String panCard;
    private String tradeLicense;
    private String fssaiLicense;
    private String fireSafety;
    private String environmentalCertificate;
    private String internationalSafety;
    private String luxuryBrand;
    private String cancelledCheque;
    private String highResolutionProperty;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles;

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String signatureImage;

    private String signatoryName;

    private LocalDate date;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;



    @PrePersist
    protected void generateId() {

        if (this.hotelId == null || this.hotelId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(SixStarHotelRepository.class)
                            .findAllHotelIds();

            this.hotelId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIH6", 4, existingIds);
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
        this.updatedAt = Instant.now();
    }
}