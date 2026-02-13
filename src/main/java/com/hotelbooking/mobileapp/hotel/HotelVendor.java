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
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Hotel Vendor entity storing comprehensive vendor registration data.
 * This entity stores all information from the 5-step vendor registration form.
 */
@Entitygit
@Table(name = "normal_hotels")
public class HotelVendor {

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

    // Step 1: Property Information
    @NotBlank
    @Size(max = 50)
    @Column(name = "hotel_name", nullable = false, length = 50)
    private String hotelName;

    @Size(max = 50)
    @Column(name = "hotel_type", length = 50)
    private String hotelType; // Property category: For Hotel: Lodge, Budget Hotel, Standard Hotel, Guest House, Heritage Hotel, Boutique Hotel. For Villa: Private Villa, Bungalow. For Apartment: Serviced Apartment. For Resort: Beach Resort, Hill Resort

    @Size(max = 4)
    @Column(name = "year_of_establishment", length = 4)
    private String yearOfEstablishment;

    @Size(max = 10)
    @Column(name = "total_rooms", length = 10)
    private String totalRooms;

    // Step 1: Contact Information
    @NotBlank
    @Size(max = 50)
    @Column(name = "owner_name", nullable = false, length = 50)
    private String ownerName;

    @NotBlank
    @Size(max = 20)
    @Column(name = "mobile_number", nullable = false, length = 20)
    private String mobileNumber;

    @Size(max = 20)
    @Column(name = "alternate_contact", length = 20)
    private String alternateContact;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "landline_numbers", columnDefinition = "jsonb")
    private java.util.List<String> landlineNumbers;

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

    // Step 2: Hotel Address
    @NotBlank
    @Size(max = 100)
    @Column(name = "address_line1", nullable = false, length = 100)
    private String addressLine1;

    @Size(max = 100)
    @Column(name = "address_line2", length = 100)
    private String addressLine2;

    @NotBlank
    @Size(max = 50)
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Size(max = 50)
    @Column(name = "district", length = 50)
    private String district;

    @NotBlank
    @Size(max = 50)
    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @NotBlank
    @Size(max = 10)
    @Column(name = "pin_code", nullable = false, length = 6)
    private String pinCode;

    @Size(max = 250)
    @Column(name = "landmark", length = 250)
    private String landmark;

    // Step 3: Room Availability
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "selected_room_types", columnDefinition = "jsonb")
    private Map<String, Boolean> selectedRoomTypes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_details", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> roomDetails;

    @Size(max = 20)
    @Column(name = "min_tariff", length = 20)
    private String minTariff;

    @Size(max = 50)
    @Column(name = "max_tariff", length = 50)
    private String maxTariff;

    @Column(name = "extra_bed_available")
    private Boolean extraBedAvailable;

    // Step 4: Amenities & Legal
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "basic_amenities", columnDefinition = "jsonb")
    private Map<String, Boolean> basicAmenities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "hotel_facilities", columnDefinition = "jsonb")
    private Map<String, Boolean> hotelFacilities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "food_services", columnDefinition = "jsonb")
    private Map<String, Boolean> foodServices;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_amenities", columnDefinition = "jsonb")
    private Map<String, Boolean> additionalAmenities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "custom_amenities", columnDefinition = "jsonb")
    private java.util.List<String> customAmenities;

    @Size(max = 50)
    @Column(name = "gst_number", length = 50)
    private String gstNumber;

    @Size(max = 50)
    @Column(name = "fssai_license", length = 50)
    private String fssaiLicense;

    @Size(max = 50)
    @Column(name = "trade_license", length = 50)
    private String tradeLicense;

    @Size(max = 50)
    @Column(name = "pan_number", length = 20)
    private String panNumber;

    @Size(max = 50)
    @Column(name = "aadhar_number", length = 20)
    private String aadharNumber;

    // Step 5: Bank & Documents
    @NotBlank
    @Size(max = 50)
    @Column(name = "account_holder_name", nullable = false, length = 50)
    private String accountHolderName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "account_number", nullable = false, length = 30)
    private String accountNumber;

    @NotBlank
    @Size(max = 50)
    @Column(name = "ifsc_code", nullable = false, length = 20)
    private String ifscCode;

    @Size(max = 50)
    @Column(name = "branch", length = 50)
    private String branch;

   @Column(name= "account_type", nullable = false)
    private Boolean accountType; // Savings / Current

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "uploaded_files", columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> uploadedFiles;

    @Size(max = 50)
    @Column(name = "signature_name", length = 50)
    private String signatureName;

    @Size(max = 50)
    @Column(name = "declaration_name", length = 50)
    private String declarationName;

    @Column(name = "declaration_date")
    private Instant declarationDate;

    @Column(name = "declaration_accepted", nullable = false)
    private Boolean declarationAccepted = false;

    // Status fields
    @Column(name = "registration_status", length = 20)
    private String registrationStatus = "PENDING"; // PENDING, APPROVED, REJECTED, UNDER_REVIEW

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Constructors
    public HotelVendor() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.declarationAccepted = false;
        this.registrationStatus = "PENDING";
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (updatedAt == null) {
            updatedAt = Instant.now();
        }
        if (declarationAccepted == null) {
            declarationAccepted = false;
        }
        if (registrationStatus == null || registrationStatus.isEmpty()) {
            registrationStatus = "PENDING";
        }
        // Initialize empty collections to avoid null JSONB issues
        if (landlineNumbers == null) {
            landlineNumbers = new ArrayList<>();
        }
        if (customAmenities == null) {
            customAmenities = new ArrayList<>();
        }
        if (selectedRoomTypes == null) {
            selectedRoomTypes = new HashMap<>();
        }
        if (roomDetails == null) {
            roomDetails = new HashMap<>();
        }
        if (basicAmenities == null) {
            basicAmenities = new HashMap<>();
        }
        if (hotelFacilities == null) {
            hotelFacilities = new HashMap<>();
        }
        if (foodServices == null) {
            foodServices = new HashMap<>();
        }
        if (additionalAmenities == null) {
            additionalAmenities = new HashMap<>();
        }
        if (uploadedFiles == null) {
            uploadedFiles = new HashMap<>();
        }
        if (personPhotoInfo == null) {
            personPhotoInfo = new HashMap<>();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    // Getters and Setters
    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
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

    public String getYearOfEstablishment() {
        return yearOfEstablishment;
    }

    public void setYearOfEstablishment(String yearOfEstablishment) {
        this.yearOfEstablishment = yearOfEstablishment;
    }

    public String getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(String totalRooms) {
        this.totalRooms = totalRooms;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAlternateContact() {
        return alternateContact;
    }

    public void setAlternateContact(String alternateContact) {
        this.alternateContact = alternateContact;
    }

    public java.util.List<String> getLandlineNumbers() {
        return landlineNumbers;
    }

    public void setLandlineNumbers(java.util.List<String> landlineNumbers) {
        this.landlineNumbers = landlineNumbers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Map<String, Object> getPersonPhotoInfo() {
        return personPhotoInfo;
    }

    public void setPersonPhotoInfo(Map<String, Object> personPhotoInfo) {
        this.personPhotoInfo = personPhotoInfo;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Map<String, Boolean> getSelectedRoomTypes() {
        return selectedRoomTypes;
    }

    public void setSelectedRoomTypes(Map<String, Boolean> selectedRoomTypes) {
        this.selectedRoomTypes = selectedRoomTypes;
    }

    public Map<String, Map<String, Object>> getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(Map<String, Map<String, Object>> roomDetails) {
        this.roomDetails = roomDetails;
    }

    public String getMinTariff() {
        return minTariff;
    }

    public void setMinTariff(String minTariff) {
        this.minTariff = minTariff;
    }

    public String getMaxTariff() {
        return maxTariff;
    }

    public void setMaxTariff(String maxTariff) {
        this.maxTariff = maxTariff;
    }

    public Boolean getExtraBedAvailable() {
        return extraBedAvailable;
    }

    public void setExtraBedAvailable(Boolean extraBedAvailable) {
        this.extraBedAvailable = extraBedAvailable;
    }

    public Map<String, Boolean> getBasicAmenities() {
        return basicAmenities;
    }

    public void setBasicAmenities(Map<String, Boolean> basicAmenities) {
        this.basicAmenities = basicAmenities;
    }

    public Map<String, Boolean> getHotelFacilities() {
        return hotelFacilities;
    }

    public void setHotelFacilities(Map<String, Boolean> hotelFacilities) {
        this.hotelFacilities = hotelFacilities;
    }

    public Map<String, Boolean> getFoodServices() {
        return foodServices;
    }

    public void setFoodServices(Map<String, Boolean> foodServices) {
        this.foodServices = foodServices;
    }

    public Map<String, Boolean> getAdditionalAmenities() {
        return additionalAmenities;
    }

    public void setAdditionalAmenities(Map<String, Boolean> additionalAmenities) {
        this.additionalAmenities = additionalAmenities;
    }

    public java.util.List<String> getCustomAmenities() {
        return customAmenities;
    }

    public void setCustomAmenities(java.util.List<String> customAmenities) {
        this.customAmenities = customAmenities;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getFssaiLicense() {
        return fssaiLicense;
    }

    public void setFssaiLicense(String fssaiLicense) {
        this.fssaiLicense = fssaiLicense;
    }

    public String getTradeLicense() {
        return tradeLicense;
    }

    public void setTradeLicense(String tradeLicense) {
        this.tradeLicense = tradeLicense;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Boolean getAccountType() {
        return accountType;
    }

    public void setAccountType(Boolean accountType) {
        this.accountType = accountType;
    }

    public Map<String, Map<String, Object>> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(Map<String, Map<String, Object>> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public String getDeclarationName() {
        return declarationName;
    }

    public void setDeclarationName(String declarationName) {
        this.declarationName = declarationName;
    }

    public Instant getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(Instant declarationDate) {
        this.declarationDate = declarationDate;
    }

    public Boolean getDeclarationAccepted() {
        return declarationAccepted;
    }

    public void setDeclarationAccepted(Boolean declarationAccepted) {
        this.declarationAccepted = declarationAccepted;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

