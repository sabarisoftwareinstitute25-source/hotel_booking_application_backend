package com.hotelbooking.mobileapp.villa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotelbooking.mobileapp.hotel.Vendor;
import com.hotelbooking.mobileapp.util.BeanUtil;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
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
@Table(name = "villa_registration")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class VillaRegistration {

    @Id
    @Column(length = 20)
    private String villaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(name = "property_type", length = 50)
    private String propertyType;

    // Basic Info
    private String villaName;
    private String ownerName;
    private String mobileNumber;
    private String alternativeMobile;
    private String email;
    private String website;
    private String profilePhoto;

    // Location
    private String villaAddress;
    private String area;
    private String city;
    private String state;
    private String pinCode;
    private String googleMapLocation;

    // Property Details
    private String typeOfProperty;
    private String customPropertyType;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer guestCapacity;
    private String propertySize;
    private Integer yearOfConstruction;

    @Column(columnDefinition = "TEXT")
    private String propertyDescription;

    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> amenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> otherAmenities = new ArrayList<>();

    // Pricing
    private BigDecimal basePrice;
    private BigDecimal weekendPrice;
    private BigDecimal peakSeasonPrice;
    private BigDecimal securityDeposit;
    private Integer minimumStay;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    private String cancellationPolicy;

    // Documents
    private String propertyOwnershipProof;
    private String idProof;
    private String tradeLicense;

    // Bank
    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;
    private String cancelledCheque;

    // Photos
    private String villaExteriorPhotos;
    private String interiorPhotos;
    private String bedroomPhotos;
    private String bathroomPhotos;
    private String shortVideo;

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
    private boolean featured=false;
    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void generateId() {

        if (this.villaId == null || this.villaId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(VillaRegistrationRepository.class)
                            .findAllVillaIds();

            this.villaId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIHV", 4, existingIds);
        }

        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
