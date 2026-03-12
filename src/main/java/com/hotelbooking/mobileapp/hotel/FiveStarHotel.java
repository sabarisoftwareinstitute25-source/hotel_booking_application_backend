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
import org.springframework.security.core.parameters.P;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String hotelCategory = "Five-Star Hotel";

    // Hotel Info
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

    @Column(name = "star_classification_no", length = 50)
    private String starClassificationNo;

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

    // Owner
    @NotBlank(message = "Owner name is required")
    @Size(min = 3, max = 50)
    private String ownerName;

    @Column(length = 100)
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

    // JSON Field
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private List<String> selectedRoomTypes = new ArrayList<>();

    // Deluxe Room
    @Positive
    private Integer deluxeNoOfUnits;
    private Integer deluxeMaxOccupancy;
    private Boolean deluxeClimateControl;
    private String deluxeBedType;
    private Double deluxeMinPricePerDay;
    private Double deluxeMaxPricePerDay;

    // Club Room
    @Positive
    private Integer clubNoOfUnits;
    private Integer clubMaxOccupancy;
    private Boolean clubClimateControl;
    private String clubBedType;
    private Double clubMinPricePerDay;
    private Double clubMaxPricePerDay;

    // Executive Room
    @Positive
    private Integer executiveNoOfUnits;
    private Integer executiveMaxOccupancy;
    private Boolean executiveClimateControl;
    private String executiveBedType;
    private Double executiveMinPricePerDay;
    private Double executiveMaxPricePerDay;

    // Suite Room
    @Positive
    private Integer suiteNoOfUnits;
    private Integer suiteMaxOccupancy;
    private Boolean suiteClimateControl;
    private String suiteBedType;
    private Double suiteMinPricePerDay;
    private Double suiteMaxPricePerDay;

    // Presidential Suite
    @Positive
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

    private String earlyCheckInLateCheckOut;

    private Boolean petsAllowed;

    // Legal
    private String gstNumber;
    private String panNumber;
    private String tradeLicenseNumber;
    private String fssaiLicenseNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "compliance", columnDefinition = "jsonb")
    private List<String> compliance = new ArrayList<>();

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

        // Auto set hotel category if null
        if (this.hotelCategory == null || this.hotelCategory.isBlank()) {
            this.hotelCategory = "Five-Star Hotel";
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