package com.hotelbooking.mobileapp.apartment;

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
@Table(name = "apartment_registration")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class ApartmentRegistration {

    @Id
    @Column(length = 20)
    private String apartmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(name = "property_type", length = 50)
    private String propertyType;

    // Basic Contact Information
    private String apartmentName;
    private String ownerName;
    private String mobileNumber;
    private String alternateMobile;
    private String email;
    private String website;
    private String companyName;
    private String profilePhoto;

    // Property Location Details
    private String apartmentAddress;
    private String area;
    private String city;
    private String state;
    private String pinCode;
    private String googleMapLocation;

    // Property Details
    private String typeOfApartment;
    private String customApartmentType;
    private Integer totalNumberOfUnits;
    private Integer totalBedrooms;
    private Integer totalBathrooms;
    private Integer maxGuestCapacity;
    private Integer floorNumber;
    private Integer totalFloorsInBuilding;
    private Boolean elevatorAvailable;
    private String propertySize;
    private Integer yearOfConstruction;
    private String propertyDescription;

    // Amenities
    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> amenitiesAvailable = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> otherAmenities = new ArrayList<>();

    // pricing & Booking Details
    private Double basePricePerNight;
    private Double weeklyPrice;
    private Double monthlyPrice;
    private Double weekendPrice;
    private Double peakSeasonPrice;
    private Double securityDeposit;
    private Integer minimumStay;
    private Integer advancePayment;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String cancellationPolicy;

    // Availability Management
    private LocalDate availableFromDate;
    private String blackoutDates;
    private String calendarSyncOption;
    private Boolean instantBooking;

    // Legal & Verification Documents
    private String propertyOwnershipProof;
    private String idProof;
    private String gstNumber;
    private String tradeLicenseNumber;
    private String policeVerification;

    // Bank Details
    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;
    private String accountType;
    private String cancelledCheque;

    // Media Upload Section
    private String exteriorPhotos;
    private String interiorPhotos;
    private String bedroomPhotos;
    private String bathroomPhotos;
    private String amenitiesPhotos;
    private String shortVideo;
    private String virtualTourLink;

    // House Rules
    private String smokingPolicy;
    private String petPolicy;
    private String eventPolicy;
    private String visitorPolicy;
    private String quietHours;
    private String additionalRules;

    // Terms
    private boolean confirmInformation=false;
    private boolean agreeTerms=false;
    private boolean agreeCommission=false;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String digitalSignature;

    private LocalDate date;

    // Admin
    private String vendorStatus = "PENDING";
    private Boolean featured=false;
    private Boolean verifiedBadge=false;
    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void generateId() {

        if (this.apartmentId == null || this.apartmentId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(ApartmentRegistrationRepository.class)
                            .findAllApartmentIds();

            this.apartmentId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIHA", 4, existingIds);
        }

        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
