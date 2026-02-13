package com.hotelbooking.mobileapp.hotel;

import jakarta.persistence.*;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Entity
@Table(name = "three_star_hotels")
public class ThreeStarHotel {

    @Id
    @Column(nullable = false, length = 16)
    private String registrationId;

    @Column(name = "vendor_id", length = 16)
    private String vendorId; // Reference to vendors table (EIH2026V010004)

    @Column(name = "hotel_id", nullable = false, length = 16)
    private String hotelId; // Reference to hotels table

    // Property Type (Hotel, Villa, Apartment, Resort)
    @NotBlank
    @Size(max = 50)
    @Column(name = "property_type", nullable = false, length = 50)
    private String propertyType; // Hotel, Villa, Apartment, Resort

    // Step 1: Hotel Info
    @NotBlank
    @Size(max = 50)
    @Column(name = "hotel_name", nullable = false, length = 50)
    private String hotelName; // Property name (hotel/villa/apartment/resort name)

    @Size(max = 50)
    @Column(name = "hotel_type", length = 50)
    private String hotelType;

    @Size(max = 4)
    @Column(name = "year_of_establishment", length = 4)
    private String yearOfEstablishment;

    @Size(max = 10)
    @Column(name = "total_rooms", length = 10)
    private String totalRooms;

    // Step 2: Contact & Address
    @NotBlank
    @Size(max = 50)
    @Column(name = "owner_name", nullable = false, length = 50)
    private String ownerName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "designation", nullable = false, length = 100)
    private String designation;

    @NotBlank
    @Size(max = 50)
    @Column(name = "mobile_number", nullable = false, length = 20)
    private String mobileNumber;

    @Size(max = 50)
    @Column(name = "alternate_contact", length = 20)
    private String alternateContact;

    @Email
    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 250)
    @Column(name = "website", length = 250)
    private String website;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "person_photo_info", columnDefinition = "jsonb")
    private Map<String, Object> personPhotoInfo;

    //Hotel Address
    @NotBlank
    @Size(max = 250)
    @Column(name = "address_line1", nullable = false, length = 250)
    private String addressLine1;

    @Size(max = 250)
    @Column(name = "address_line2", length = 250)
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
    @Column(name = "pin_code", length = 10)
    private String pinCode;

    //3.Room Configuration
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private Map<String, Boolean> selectedRoomTypes;

    private Boolean extraBedAvailable;

    private Boolean seasonalpricing;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_details", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> roomDetails;

    //4.Amenities & Policies
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_amenities", columnDefinition = "jsonb")
    private Map<String, Boolean> roomAmenities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "hotel_facilities", columnDefinition = "jsonb")
    private Map<String, Boolean> hotelFacilities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "food_beverage", columnDefinition = "jsonb")
    private Map<String, Boolean> foodBeverage;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "guest_service", columnDefinition = "jsonb")
    private Map<String, Boolean> guestService;

    //Check-in Policies
    @Column(nullable = false)
    private LocalTime standardCheckInTime;

    @Column(nullable = false)
    private LocalTime standardCheckOutTime;

    private Boolean earlycheckinlatecheckout;

    private Boolean petsAllowed;

    //5.Legal & Documents
    @Column(length = 50 , nullable = false)
    private String gstNumber;

    @Column(length = 50 )
    private String fssaiLicense;

    @Column(length = 50)
    private String tradeLicense;

    @Column(length = 50)
    private String panNumber;

    private Boolean firesafety;

    //Bank Details
    @NotBlank
    @Column(length = 50 , nullable = false)
    private String accountHolderName;

    @NotBlank
    @Column(length = 50 , nullable = false)
    private String bankName;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String accountNumber;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String ifscCode;

    @NotBlank
    private String branch;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles;

    @Column(nullable = false)
    private Boolean declarationAccepted = false;

    private LocalDate declarationDate;

    private String registrationStatus = "PENDING";

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    private LocalDate signedDate;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Boolean getEarlycheckinlatecheckout() {
        return earlycheckinlatecheckout;
    }

    public void setEarlycheckinlatecheckout(Boolean earlycheckinlatecheckout) {
        this.earlycheckinlatecheckout = earlycheckinlatecheckout;
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

    public Boolean getFiresafety() {
        return firesafety;
    }

    public void setFiresafety(Boolean firesafety) {
        this.firesafety = firesafety;
    }

    public Map<String, Boolean> getFoodBeverage() {
        return foodBeverage;
    }

    public void setFoodBeverage(Map<String, Boolean> foodBeverage) {
        this.foodBeverage = foodBeverage;
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

    public Map<String, Boolean> getHotelFacilities() {
        return hotelFacilities;
    }

    public void setHotelFacilities(Map<String, Boolean> hotelFacilities) {
        this.hotelFacilities = hotelFacilities;
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

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
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

    public Boolean getSeasonalpricing() {
        return seasonalpricing;
    }

    public void setSeasonalpricing(Boolean seasonalpricing) {
        this.seasonalpricing = seasonalpricing;
    }

    public Map<String, Boolean> getSelectedRoomTypes() {
        return selectedRoomTypes;
    }

    public void setSelectedRoomTypes(Map<String, Boolean> selectedRoomTypes) {
        this.selectedRoomTypes = selectedRoomTypes;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getYearOfEstablishment() {
        return yearOfEstablishment;
    }

    public void setYearOfEstablishment(String yearOfEstablishment) {
        this.yearOfEstablishment = yearOfEstablishment;
    }
}
