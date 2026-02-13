package com.hotelbooking.mobileapp.hotel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Entity
@Table(name = "five_star_hotels")
public class FiveStarHotel {

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
    private String starClassificationNo;

    // 2. Address
    @NotBlank
    @Column(nullable = false , length = 250)
    private String addressLine1;

    @Column(length = 250)
    private String addressLine2;

    @Column(nullable = false)
    private Boolean isPrimary = true;

    @NotBlank
    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String district;

    @NotBlank
    @Column(length = 50)
    private String state;

    @NotBlank
    @Column(length = 10)
    private String pinCode;

    // Owner
    @NotBlank
    @Column(length = 50)
    private String ownerName;

    @NotBlank
    @Column(length = 50)
    private String designation;

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

    // Room Configuration
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> selectedRoomTypes;

    private Boolean extraBedAvailable;
    private Boolean seasonalPricing;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> roomDetails;

    // Amenities
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> roomAmenities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> hotelService;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> diningAndEvent;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> wellnessAndLeisure;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> guestService;

    // Check-in
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private Boolean earlyCheckinLateCheckout;
    private Boolean petsAllowed;

    // Legal
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

    @NotBlank
    @Column(length = 50)
    private String ifscCode;

    @NotBlank
    @Column(length = 50)
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
    private LocalDate signedDate;

    // Digital Signature
    @Lob
    @Column(columnDefinition = "TEXT")
    private String signatureImage;

    // Lifecycle Hooks
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
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

    public String getAddressLine1() {
        return addressLine1;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Boolean getEarlyCheckinLateCheckout() {
        return earlyCheckinLateCheckout;
    }

    public void setEarlyCheckinLateCheckout(Boolean earlyCheckinLateCheckout) {
        this.earlyCheckinLateCheckout = earlyCheckinLateCheckout;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getExtraBedAvailable() {
        return extraBedAvailable;
    }

    public void setExtraBedAvailable(Boolean extraBedAvailable) {
        this.extraBedAvailable = extraBedAvailable;
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

    public Map<String, Boolean> getGuestService() {
        return guestService;
    }

    public void setGuestService(Map<String, Boolean> guestService) {
        this.guestService = guestService;
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

    public Map<String, Boolean> getHotelService() {
        return hotelService;
    }

    public void setHotelService(Map<String, Boolean> hotelService) {
        this.hotelService = hotelService;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public Map<String, Object> getPersonPhotoInfo() {
        return personPhotoInfo;
    }

    public void setPersonPhotoInfo(Map<String, Object> personPhotoInfo) {
        this.personPhotoInfo = personPhotoInfo;
    }

    public Boolean getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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

    public Map<String, Map<String, Object>> getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(Map<String, Map<String, Object>> roomDetails) {
        this.roomDetails = roomDetails;
    }

    public Boolean getSeasonalPricing() {
        return seasonalPricing;
    }

    public void setSeasonalPricing(Boolean seasonalPricing) {
        this.seasonalPricing = seasonalPricing;
    }

    public Map<String, Boolean> getSelectedRoomTypes() {
        return selectedRoomTypes;
    }

    public void setSelectedRoomTypes(Map<String, Boolean> selectedRoomTypes) {
        this.selectedRoomTypes = selectedRoomTypes;
    }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
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

    public String getStarClassificationNo() {
        return starClassificationNo;
    }

    public void setStarClassificationNo(String starClassificationNo) {
        this.starClassificationNo = starClassificationNo;
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