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
@Table(name = "five_star_hotels")
public class FiveStarHotel {

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

    @Column(name = "hotel_type", length = 50)
    private String hotelType;

    @Column(name = "year_of_establishment", length = 4)
    private Integer yearOfEstablishment;

    @Column(name = "total_rooms", length = 10)
    private Integer totalRooms;

    @Column(name = "star_classification_no", length = 50)
    private String starClassificationNo;

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

    // Owner
    @Column(name = "owner_name", length = 150)
    private String ownerName;

    @Column(length = 100)
    private String designation;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "alternate_contact", length = 20)
    private String alternateContact;

    @Column(nullable = false)
    private String email;

    @Column(length = 200)
    private String website;

    private String profilePhoto;

    // JSON Field
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    // Deluxe Room
    private Integer deluxeNoOfUnits;
    private Integer deluxeMaxOccupancy;
    private Boolean deluxeClimateControl;
    private String deluxeBedType;
    private Double deluxeMinPricePerDay;
    private Double deluxeMaxPricePerDay;

    // Club Room
    private Integer clubNoOfUnits;
    private Integer clubMaxOccupancy;
    private Boolean clubClimateControl;
    private String clubBedType;
    private Double clubMinPricePerDay;
    private Double clubMaxPricePerDay;

    // Executive Room
    private Integer executiveNoOfUnits;
    private Integer executiveMaxOccupancy;
    private Boolean executiveClimateControl;
    private String executiveBedType;
    private Double executiveMinPricePerDay;
    private Double executiveMaxPricePerDay;

    // Suite Room
    private Integer suiteNoOfUnits;
    private Integer suiteMaxOccupancy;
    private Boolean suiteClimateControl;
    private String suiteBedType;
    private Double suiteMinPricePerDay;
    private Double suiteMaxPricePerDay;

    // Presidential Suite
    private Integer presidentialNoOfUnits;
    private Integer presidentialMaxOccupancy;
    private Boolean presidentialClimateControl;
    private String presidentialBedType;
    private Double presidentialMinPricePerDay;
    private Double presidentialMaxPricePerDay;

    private Boolean extraBedAvailable;
    private Boolean seasonalPricing;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_amenities", columnDefinition = "jsonb")
    private List<String> roomAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "hotel_service", columnDefinition = "jsonb")
    private List<String> hotelService = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dining_and_event", columnDefinition = "jsonb")
    private List<String> diningAndEvent = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "wellness_and_leisure", columnDefinition = "jsonb")
    private List<String> wellnessAndLeisure = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "guest_service", columnDefinition = "jsonb")
    private List<String> guestService = new ArrayList<>();

    // Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private String earlyCheckInLateCheckout;
    private Boolean petsAllowed;

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

    // Documents
    private String gstCertificate;
    private String panCard;
    private String tradeLicense;
    private String fssaiCertificate;
    private String fireSafetyNoc;
    private String starCertification;
    private String pollutionControlCertificate;
    private String cancelledCheque;
    private String property;

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

    private LocalDate date;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;


    @PrePersist
    protected void generateId() {

        if (this.hotelId == null || this.hotelId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(FiveStarHotelRepository.class)
                            .findAllHotelIds();

            this.hotelId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIH5", 4, existingIds);
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