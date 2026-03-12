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
@Table(name = "seven_star_hotels")
public class SevenStarHotel {

    @Id
    @Column(nullable = false, length = 16)
    private String hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    @Column(nullable = false, length = 50)
    private String propertyType = "Hotel";

    private String hotelCategory = "Seven-Star Hotel";

    // Step 1
    @Column(nullable = false)
    private String palaceName;

    private String sovereignClassification;
    private String heritageStatus;
    private String ownershipCategory;
    private Integer yearOfOrigin;
    private Integer totalGuestCapacity;
    private String historicSignificance;
    private String globalPrestigeRank;
    private String estateSize;
    private String staffToGuestRatio;

    // Step 2
    @Column(nullable = false)
    private String legalHoldingEntity;

    @Column(nullable = false)
    private String sovereignOwner;

    private String chiefExecutiveCustodian;
    private String protocolAffairsDirector;
    private String headOfGuestExperience;
    private String eliteGuestLiaisonOfficer;
    private String directCommandContactNumber;
    private String encryptedCommunicationLine;
    private String executiveEmailChannel;
    private String officialPortfolio;

    private String profilePhoto;

    // Global Access
    @Column(nullable = false)
    private String estateAddress;

    @Column(nullable = false)
    private String city;

    private String state;
    private String country;
    private String postalCode;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> exclusiveArrivalInfrastructure = new ArrayList<>();

    private String distanceFromInternationalHub;

    // Step 3
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> selectRoyalResidenceTypes = new ArrayList<>();

    // Imperial Chamber
    private Integer imperialUnits;
    private String imperialArea;
    private Integer imperialMaxGuest;
    private String imperialSignatureFeatures;
    private Double imperialAvgNightlyRate;
    private Double imperialPeakRate;

    // Royal Suite
    private Integer royalUnits;
    private String royalArea;
    private Integer royalMaxGuest;
    private String royalSignatureFeatures;
    private Double royalAvgNightlyRate;
    private Double royalPeakRate;

    // Crown Residence
    private Integer crownUnits;
    private String crownArea;
    private Integer crownMaxGuest;
    private String crownSignatureFeatures;
    private Double crownAvgNightlyRate;
    private Double crownPeakRate;

    // Presidential Palace Suite
    private Integer presidentialUnits;
    private String presidentialArea;
    private Integer presidentialMaxGuest;
    private String presidentialSignatureFeatures;
    private Double presidentialAvgNightlyRate;
    private Double presidentialPeakRate;

    // Private Kingdom Villa
    private Integer villaUnits;
    private String villaArea;
    private Integer villaMaxGuest;
    private String villaSignatureFeatures;
    private Double villaAvgNightlyRate;
    private Double villaPeakRate;

    private String pricingIntelligenceEngine;

    // Step 4
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> inResidenceIntelligenceSystems = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> signatureRoyalAmenities = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> arrivalCeremonialProtocols = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> gastronomicOfferings = new ArrayList<>();

    private String royalBanquetCourtSeatingCapacity;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> wellness = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> privileges = new ArrayList<>();

    // Step 6
    private String registryId;
    private String securityCertificationLevel;
    private Boolean disasterClearance;
    private Boolean environmentalSovereignCertification;
    private String cyberIntelligenceProtectionLevel;
    private String crisisCommandSystem;

    @Column(nullable = false)
    private String treasuryAccountName;

    @Column(nullable = false)
    private String globalBankInstitution;

    private String accountNumber;
    private String iban;
    private String settlementCurrency;
    private String accountType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> alternativeSettlementOptions = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> integrationCapabilities = new ArrayList<>();

    // Credentials Upload
    private String royalAuthorizationCertificate;
    private String estateOwnershipProof;
    private String internationalSecurityCertificate;
    private String insuranceCoveragePortfolio;
    private String accreditationProofs;
    private String financialVerificationLetter;
    private String ultraHdEstatePortfolio;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles;

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String signatureImage;

    private String authorizedAuthority;

    private String title;

    private LocalDate date;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    protected void generateId() {

        if (this.hotelId == null || this.hotelId.isBlank()) {

            List<String> existingIds =
                    BeanUtil.getBean(SevenStarHotelRepository.class)
                            .findAllHotelIds();

            this.hotelId =
                    BeanUtil.getBean(IdGeneratorService.class)
                            .generateMonthlyId("EIH7", 4, existingIds);
        }

        // Auto set property type if null
        if (this.propertyType == null || this.propertyType.isBlank()) {
            this.propertyType = "Hotel";
        }

        // Auto set hotel category if null
        if (this.hotelCategory == null || this.hotelCategory.isBlank()) {
            this.hotelCategory = "Seven-Star Hotel";
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