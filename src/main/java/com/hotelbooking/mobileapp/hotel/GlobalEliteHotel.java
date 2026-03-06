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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "global_elite_hotels")
public class GlobalEliteHotel {

    @Id
    @Column(nullable = false, length = 16)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    // Step 1 - Property Overview
    @Column(nullable = false)
    private String propertyName;

    private String ownershipGroup;
    private String luxuryClassification;
    private String propertyPositioning;
    private Integer yearEstablished;
    private Integer renovatedYear;
    private String globalAwards;
    private String internationalRecognitionLevel;
    private Integer totalInventory;
    private String staffToGuestRatio;

    // Step 2 - Ownership & Contacts
    @Column(nullable = false)
    private String entityName;

    @Column(nullable = false)
    private String ultimateBeneficialOwner;

    @Column(nullable = false)
    private String authorizedSignatory;

    private String designation;
    private String managingDirector;
    private String vipRelationsDirector;

    @Column(nullable = false)
    private String primaryContactNumber;

    private String secondaryContact;
    private String executiveEmail;
    private String website;

    // Step 3 - Accommodation
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> selectAccommodationCategories = new ArrayList<>();

    private String rateEngineType;

    // Step 4 - Luxury Features
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> inSuiteTechnology = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> signatureLuxuryFeatures = new ArrayList<>();

    private String luxuryLinenBrand;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> arrivalExperience = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> gastronomyFeatures = new ArrayList<>();

    private Integer numberOfMichelinStarRestaurants;
    private Integer royalBanquetHallCapacity;
    private Boolean globalEventHostingExperience;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> wellnessFeatures = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> guestPrivileges = new ArrayList<>();

    // Step 5 - Compliance & Banking
    @Column(nullable = false)
    private String taxId;

    private String internationalComplianceStandard;
    private Boolean safetyCertification;
    private Boolean environmentalCertification;
    private Boolean cyberSecurityCertification;
    private Boolean crisisManagementProtocol;

    private String accountName;
    private String bankName;
    private String accountNumber;
    private String swiftCode;
    private String bankCountry;
    private String settlementCurrency;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> integrationCapabilities = new ArrayList<>();

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

    private String authorizedSignatureName;
    private String title;
    private LocalDate signedDate;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String signatureImage;

    @PrePersist
    protected void generateId() {

        if (this.hotelId == null || this.hotelId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(GlobalEliteHotelRepository.class)
                            .findAllHotelIds();

            this.hotelId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIHG", 4, existingIds);
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