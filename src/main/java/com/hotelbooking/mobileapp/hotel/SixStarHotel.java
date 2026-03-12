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

    private String hotelCategory = "Six-Star Hotel";

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

    @Column(name = "national_recognition", length = 150)
    private String nationalRecognition;

    // Owner
    @NotBlank(message = "Owner name is required")
    @Size(min = 3, max = 50)
    private String ownerName;

    @Column(length = 100)
    private String designation;

    @NotBlank(message = "Manager name is required")
    @Size(min = 3, max = 50)
    private String managerName;

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

    // Accommodation
    @Positive
    private Integer luxuryUnits;
    private Integer luxuryMaxOccupancy;
    private String luxuryBedType;
    private Double luxuryPriceFrom;
    private Double luxuryPriceTo;

    @Positive
    private Integer clubUnits;
    private Integer clubMaxOccupancy;
    private String clubBedType;
    private Double clubPriceFrom;
    private Double clubPriceTo;

    @Positive
    private Integer executiveUnits;
    private Integer executiveMaxOccupancy;
    private String executiveBedType;
    private Double executivePriceFrom;
    private Double executivePriceTo;

    @Positive
    private Integer presidentialUnits;
    private Integer presidentialMaxOccupancy;
    private String presidentialBedType;
    private Double presidentialPriceFrom;
    private Double presidentialPriceTo;

    @Positive
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
    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotNull(message = "Account number is required")
    private Integer accountNumber;

    @NotBlank(message = "IFSC code is required")
    private String ifscCode;

    private String branch;

    @NotBlank(message = "Account Type is required")
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

        // Auto set hotel category if null
        if (this.hotelCategory == null || this.hotelCategory.isBlank()) {
            this.hotelCategory = "Six-Star Hotel";
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
