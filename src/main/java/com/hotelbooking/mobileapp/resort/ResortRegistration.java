package com.hotelbooking.mobileapp.resort;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.hotel.Vendor;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "resort_registration")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class ResortRegistration {

    @Id
    @Column(length = 20)
    private String resortId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(name = "property_type", length = 50)
    private String propertyType;

    // Basic Information
    private String resortName;
    private String ownerName;
    private String contactPersonName;
    private String mobileNumber;
    private String alternateMobile;
    private String email;
    private String website;
    private String companyName;
    private String profilePhoto;

    // Location
    private String fullAddress;
    private String area;
    private String city;
    private String state;
    private String pinCode;
    private String googleMapLocation;
    private String nearestAirport;
    private String nearestRailwayStation;

    // Resort Category
    private String resortCategory;
    private Integer totalNumberOfRooms;
    private Integer totalRoomCapacity;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> roomTypeAvailable = new ArrayList<>();

    private String totalPropertyArea;
    private Integer yearOfEstablishment;

    @Column(columnDefinition = "TEXT")
    private String descriptionOfResort;

    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> amenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> otherAmenities = new ArrayList<>();

    // Pricing
    private Double basePricePerNight;
    private Double weekendPrice;
    private Double peakSeasonPrice;
    private Double extraBedCharges;
    private String childPolicy;
    private Integer minimumStayRequirement;
    private Integer advancePaymentRequired;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    private String cancellationPolicy;

    // Booking
    private Boolean instantBooking;
    private Boolean manualApprovalRequired;

    private LocalDate availableFromDate;

    private String blackoutDates;
    private String seasonalPricingOption;

    // Legal
    private String businessRegistrationCertificate;
    private String gstNumber;
    private String tradeLicenseNumber;
    private String fssaiLicenseNumber;
    private String idProof;
    private String resortOwnership;
    private String fireSafetyCertificate;
    private String localTourismApproval;

    // Bank
    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;
    private String accountType;
    private String gstBillingDetails;
    private String cancelledCheque;

    // Media
    private String resortExteriorPhotos;
    private String lobbyPhoto;
    private String roomsPhotos;
    private String poolPhoto;
    private String restaurantPhotos;
    private String shortPromotionalVideo;
    private String virtualTour;

    // Rules
    private String checkInRequirements;
    private Boolean idProofRequired;
    private String petPolicy;
    private String smokingPolicy;
    private String eventPolicy;
    private String damagePolicy;
    private String refundPolicy;

    // Terms
    private boolean confirmInformation = false;
    private boolean agreeTerms = false;
    private boolean agreeCommission = false;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String digitalSignature;

    private LocalDate date;

    // Admin
    private String vendorStatus = "PENDING";
    private Boolean featured = false;
    private Boolean verifiedBadge = false;
    private Double rating;
    private String priorityPolicy;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void generateId() {

        if (this.resortId == null || this.resortId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(ResortRegistrationRepository.class)
                            .findAllResortIds();

            this.resortId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIHR", 4, existingIds);
        }

        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

}