package com.hotelbooking.mobileapp.hotel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "six_star_hotels")
public class SixStarHotel {

    @Id
    @Column(nullable = false, length = 16)
    private String registrationId;

    @Column(name = "vendor_id", length = 16)
    private String vendorId;

    @Column(name = "hotel_id", nullable = false, length = 16)
    private String hotelId;

    // 1. Hotel Info
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String hotelName;

    @Size(max = 50)
    private String hotelType;

    @Size(max = 4)
    private String yearOfEstablishment;

    @Size(max = 10)
    private String totalRooms;

    @Size(max = 50)
    private String nationalRecognition;

    //2.Owner
    @NotBlank
    @Column(length = 50)
    private String ownerName;

    @NotBlank
    @Column(length = 50)
    private String designation;

    @NotBlank
    @Column(length = 50)
    private String managerName;

    @NotBlank
    @Column(length = 50)
    private String mobileNumber;

    @Column(length = 50)
    private String alternateContact;

    @Email
    @Column(length = 50)
    private String email;

    @Column(length = 250)
    private String website;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> personPhotoInfo;

    //Address
    @NotBlank
    @Column(nullable = false,length = 100)
    private String addressLine1;

    @Column(length = 100)
    private String addressLine2;

    @Column(nullable = false)
    private Boolean isPrimary = true;

    @NotBlank
    @Column(length = 50)
    private String city;

    @NotBlank
    @Column(length = 50)
    private String district;

    @NotBlank
    @Column(length = 50)
    private String state;

    @NotBlank
    @Column(length = 10)
    private String pinCode;

    // ======================================================
    // 4. ACCOMMODATION INVENTORY & PRICING (7-STAR)
    // ======================================================

    // -------- Luxury Room --------
    private Integer luxuryUnits;
    private Integer luxuryMaxOccupancy;
    private String luxuryBedType;
    private Double luxuryPriceFrom;
    private Double luxuryPriceTo;

    // -------- Club Level Room --------
    private Integer clubUnits;
    private Integer clubMaxOccupancy;
    private String clubBedType;
    private Double clubPriceFrom;
    private Double clubPriceTo;

    // -------- Executive Suite --------
    private Integer executiveUnits;
    private Integer executiveMaxOccupancy;
    private String executiveBedType;
    private Double executivePriceFrom;
    private Double executivePriceTo;

    // -------- Presidential Suite --------
    private Integer presidentialUnits;
    private Integer presidentialMaxOccupancy;
    private String presidentialBedType;
    private Double presidentialPriceFrom;
    private Double presidentialPriceTo;

    // -------- Private Villa --------
    private Integer villaUnits;
    private Integer villaMaxOccupancy;
    private String villaBedType;
    private Double villaPriceFrom;
    private Double villaPriceTo;

    // -------- Additional Features --------
    private Boolean personalButlerService;
    private Boolean dynamicPricingEnabled;

    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> roomAmenities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> eilteServices;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> diningAndEvent;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> wellnessAndLeisure;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> guestPrivileges;

    // Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private Boolean earlyCheckinLateCheckout;
    private Boolean diplomaticProtocals;
    private Boolean petService;

    // 5.Legal
    @NotBlank
    @Column(length = 50)
    private String gstNumber;
    @NotBlank
    @Column(length = 50)
    private String panNumber;

    @Column(length = 50)
    private String tradeLicense;

    @Column(length = 50)
    private String fssaiLicense;

    private Boolean compilance;

    // Bank
    @NotBlank
    @Column(length = 50)
    private String accountHolderName;

    @NotBlank
    @Column(length = 50)
    private String bankName;

    @NotBlank
    @Column(length = 50)
    private String accountNumber;

    @NotBlank @Column(length = 50)
    private String ifscCode;

    @NotBlank
    private String branch;

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
    private String signatureName;
    private LocalDate signedDate;

    // Digital Signature
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

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAlternateContact() {
        return alternateContact;
    }

    public void setAlternateContact(String alternateContact) {
        this.alternateContact = alternateContact;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClubBedType() {
        return clubBedType;
    }

    public void setClubBedType(String clubBedType) {
        this.clubBedType = clubBedType;
    }

    public Integer getClubMaxOccupancy() {
        return clubMaxOccupancy;
    }

    public void setClubMaxOccupancy(Integer clubMaxOccupancy) {
        this.clubMaxOccupancy = clubMaxOccupancy;
    }

    public Double getClubPriceFrom() {
        return clubPriceFrom;
    }

    public void setClubPriceFrom(Double clubPriceFrom) {
        this.clubPriceFrom = clubPriceFrom;
    }

    public Double getClubPriceTo() {
        return clubPriceTo;
    }

    public void setClubPriceTo(Double clubPriceTo) {
        this.clubPriceTo = clubPriceTo;
    }

    public Integer getClubUnits() {
        return clubUnits;
    }

    public void setClubUnits(Integer clubUnits) {
        this.clubUnits = clubUnits;
    }

    public Boolean getCompilance() {
        return compilance;
    }

    public void setCompilance(Boolean compilance) {
        this.compilance = compilance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeclarationAccepted() {
        return declarationAccepted;
    }

    public void setDeclarationAccepted(Boolean declarationAccepted) {
        this.declarationAccepted = declarationAccepted;
    }

    public LocalDate getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(LocalDate declarationDate) {
        this.declarationDate = declarationDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Map<String, Boolean> getDiningAndEvent() {
        return diningAndEvent;
    }

    public void setDiningAndEvent(Map<String, Boolean> diningAndEvent) {
        this.diningAndEvent = diningAndEvent;
    }

    public Boolean getDiplomaticProtocals() {
        return diplomaticProtocals;
    }

    public void setDiplomaticProtocals(Boolean diplomaticProtocals) {
        this.diplomaticProtocals = diplomaticProtocals;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Boolean getDynamicPricingEnabled() {
        return dynamicPricingEnabled;
    }

    public void setDynamicPricingEnabled(Boolean dynamicPricingEnabled) {
        this.dynamicPricingEnabled = dynamicPricingEnabled;
    }

    public Boolean getEarlyCheckinLateCheckout() {
        return earlyCheckinLateCheckout;
    }

    public void setEarlyCheckinLateCheckout(Boolean earlyCheckinLateCheckout) {
        this.earlyCheckinLateCheckout = earlyCheckinLateCheckout;
    }

    public Map<String, Boolean> getEilteServices() {
        return eilteServices;
    }

    public void setEilteServices(Map<String, Boolean> eilteServices) {
        this.eilteServices = eilteServices;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExecutiveBedType() {
        return executiveBedType;
    }

    public void setExecutiveBedType(String executiveBedType) {
        this.executiveBedType = executiveBedType;
    }

    public Integer getExecutiveMaxOccupancy() {
        return executiveMaxOccupancy;
    }

    public void setExecutiveMaxOccupancy(Integer executiveMaxOccupancy) {
        this.executiveMaxOccupancy = executiveMaxOccupancy;
    }

    public Double getExecutivePriceFrom() {
        return executivePriceFrom;
    }

    public void setExecutivePriceFrom(Double executivePriceFrom) {
        this.executivePriceFrom = executivePriceFrom;
    }

    public Double getExecutivePriceTo() {
        return executivePriceTo;
    }

    public void setExecutivePriceTo(Double executivePriceTo) {
        this.executivePriceTo = executivePriceTo;
    }

    public Integer getExecutiveUnits() {
        return executiveUnits;
    }

    public void setExecutiveUnits(Integer executiveUnits) {
        this.executiveUnits = executiveUnits;
    }

    public String getFssaiLicense() {
        return fssaiLicense;
    }

    public void setFssaiLicense(String fssaiLicense) {
        this.fssaiLicense = fssaiLicense;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public Map<String, Boolean> getGuestPrivileges() {
        return guestPrivileges;
    }

    public void setGuestPrivileges(Map<String, Boolean> guestPrivileges) {
        this.guestPrivileges = guestPrivileges;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public String getLuxuryBedType() {
        return luxuryBedType;
    }

    public void setLuxuryBedType(String luxuryBedType) {
        this.luxuryBedType = luxuryBedType;
    }

    public Integer getLuxuryMaxOccupancy() {
        return luxuryMaxOccupancy;
    }

    public void setLuxuryMaxOccupancy(Integer luxuryMaxOccupancy) {
        this.luxuryMaxOccupancy = luxuryMaxOccupancy;
    }

    public Double getLuxuryPriceFrom() {
        return luxuryPriceFrom;
    }

    public void setLuxuryPriceFrom(Double luxuryPriceFrom) {
        this.luxuryPriceFrom = luxuryPriceFrom;
    }

    public Double getLuxuryPriceTo() {
        return luxuryPriceTo;
    }

    public void setLuxuryPriceTo(Double luxuryPriceTo) {
        this.luxuryPriceTo = luxuryPriceTo;
    }

    public Integer getLuxuryUnits() {
        return luxuryUnits;
    }

    public void setLuxuryUnits(Integer luxuryUnits) {
        this.luxuryUnits = luxuryUnits;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNationalRecognition() {
        return nationalRecognition;
    }

    public void setNationalRecognition(String nationalRecognition) {
        this.nationalRecognition = nationalRecognition;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public Boolean getPersonalButlerService() {
        return personalButlerService;
    }

    public void setPersonalButlerService(Boolean personalButlerService) {
        this.personalButlerService = personalButlerService;
    }

    public Map<String, Object> getPersonPhotoInfo() {
        return personPhotoInfo;
    }

    public void setPersonPhotoInfo(Map<String, Object> personPhotoInfo) {
        this.personPhotoInfo = personPhotoInfo;
    }

    public Boolean getPetService() {
        return petService;
    }

    public void setPetService(Boolean petService) {
        this.petService = petService;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPresidentialBedType() {
        return presidentialBedType;
    }

    public void setPresidentialBedType(String presidentialBedType) {
        this.presidentialBedType = presidentialBedType;
    }

    public Integer getPresidentialMaxOccupancy() {
        return presidentialMaxOccupancy;
    }

    public void setPresidentialMaxOccupancy(Integer presidentialMaxOccupancy) {
        this.presidentialMaxOccupancy = presidentialMaxOccupancy;
    }

    public Double getPresidentialPriceFrom() {
        return presidentialPriceFrom;
    }

    public void setPresidentialPriceFrom(Double presidentialPriceFrom) {
        this.presidentialPriceFrom = presidentialPriceFrom;
    }

    public Double getPresidentialPriceTo() {
        return presidentialPriceTo;
    }

    public void setPresidentialPriceTo(Double presidentialPriceTo) {
        this.presidentialPriceTo = presidentialPriceTo;
    }

    public Integer getPresidentialUnits() {
        return presidentialUnits;
    }

    public void setPresidentialUnits(Integer presidentialUnits) {
        this.presidentialUnits = presidentialUnits;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public Map<String, Boolean> getRoomAmenities() {
        return roomAmenities;
    }

    public void setRoomAmenities(Map<String, Boolean> roomAmenities) {
        this.roomAmenities = roomAmenities;
    }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public LocalDate getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(LocalDate signedDate) {
        this.signedDate = signedDate;
    }

    public LocalTime getStandardCheckInTime() {
        return standardCheckInTime;
    }

    public void setStandardCheckInTime(LocalTime standardCheckInTime) {
        this.standardCheckInTime = standardCheckInTime;
    }

    public LocalTime getStandardCheckOutTime() {
        return standardCheckOutTime;
    }

    public void setStandardCheckOutTime(LocalTime standardCheckOutTime) {
        this.standardCheckOutTime = standardCheckOutTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(String totalRooms) {
        this.totalRooms = totalRooms;
    }

    public String getTradeLicense() {
        return tradeLicense;
    }

    public void setTradeLicense(String tradeLicense) {
        this.tradeLicense = tradeLicense;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<String, Map<String, Object>> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(Map<String, Map<String, Object>> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVillaBedType() {
        return villaBedType;
    }

    public void setVillaBedType(String villaBedType) {
        this.villaBedType = villaBedType;
    }

    public Integer getVillaMaxOccupancy() {
        return villaMaxOccupancy;
    }

    public void setVillaMaxOccupancy(Integer villaMaxOccupancy) {
        this.villaMaxOccupancy = villaMaxOccupancy;
    }

    public Double getVillaPriceFrom() {
        return villaPriceFrom;
    }

    public void setVillaPriceFrom(Double villaPriceFrom) {
        this.villaPriceFrom = villaPriceFrom;
    }

    public Double getVillaPriceTo() {
        return villaPriceTo;
    }

    public void setVillaPriceTo(Double villaPriceTo) {
        this.villaPriceTo = villaPriceTo;
    }

    public Integer getVillaUnits() {
        return villaUnits;
    }

    public void setVillaUnits(Integer villaUnits) {
        this.villaUnits = villaUnits;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Map<String, Boolean> getWellnessAndLeisure() {
        return wellnessAndLeisure;
    }

    public void setWellnessAndLeisure(Map<String, Boolean> wellnessAndLeisure) {
        this.wellnessAndLeisure = wellnessAndLeisure;
    }

    public String getYearOfEstablishment() {
        return yearOfEstablishment;
    }

    public void setYearOfEstablishment(String yearOfEstablishment) {
        this.yearOfEstablishment = yearOfEstablishment;
    }
}
