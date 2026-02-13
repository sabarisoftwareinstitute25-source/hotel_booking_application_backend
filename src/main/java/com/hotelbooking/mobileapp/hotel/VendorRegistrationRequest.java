package com.hotelbooking.mobileapp.hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Request DTO for vendor registration.
 * This exactly matches the frontend formData structure.
 */
public class VendorRegistrationRequest {

    // Step 1: Basic Details
    @NotBlank(message = "Hotel name is required")
    @Size(max = 150, message = "Hotel name cannot exceed 150 characters")
    private String hotelName;

    private String hotelType; // Optional

    private String yearOfEstablishment; // Optional

    private String totalRooms; // Optional

    @NotBlank(message = "Owner name is required")
    @Size(max = 100, message = "Owner name cannot exceed 100 characters")
    private String ownerName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
    private String mobileNumber;

    private String alternateContact; // Optional

    private List<String> landlineNumbers; // Optional

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    private String email;

    private String website; // Optional

    // Step 2: Address
    @NotBlank(message = "Address line 1 is required")
    @Size(max = 255, message = "Address line 1 cannot exceed 255 characters")
    private String addressLine1;

    private String addressLine2; // Optional

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    private String district; // Optional

    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State cannot exceed 100 characters")
    private String state;

    @NotBlank(message = "PIN code is required")
    @Pattern(regexp = "^\\d{6}$", message = "PIN code must be exactly 6 digits")
    private String pinCode;

    private String landmark; // Optional

    // Step 3: Room Details
    private Map<String, Boolean> selectedRoomTypes; // Optional

    private Map<String, Map<String, Object>> roomDetails; // Optional

    private String minTariff; // Optional

    private String maxTariff; // Optional

    private Boolean extraBedAvailable; // Optional

    // Step 4: Amenities
    private Map<String, Boolean> basicAmenities; // Optional

    private Map<String, Boolean> hotelFacilities; // Optional

    private Map<String, Boolean> foodServices; // Optional

    private Map<String, Boolean> additionalAmenities; // Optional

    private List<String> customAmenities; // Optional

    // Step 4: Legal Documents
    @NotBlank(message = "GST Number is required")
    private String gstNumber;

    private String fssaiLicense; // Optional

    private String tradeLicense; // Optional

    private String panNumber; // Optional

    private String aadharNumber; // Optional

    // Step 5: Bank Details
    @NotBlank(message = "Account holder name is required")
    @Size(max = 100, message = "Account holder name cannot exceed 100 characters")
    private String accountHolderName;

    @NotBlank(message = "Bank name is required")
    @Size(max = 100, message = "Bank name cannot exceed 100 characters")
    private String bankName;

    @NotBlank(message = "Account number is required")
    @Size(min = 9, max = 18, message = "Account number must be between 9 and 18 characters")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Account number must contain only letters and numbers")
    private String accountNumber;

    @NotBlank(message = "IFSC code is required")
    @Size(min = 11, max = 11, message = "IFSC code must be exactly 11 characters")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "IFSC code must be in format: AAAA0XXXXXX")
    private String ifscCode;

    private String branch; // Optional

    private String accountType; // Optional

    // Step 5: Documents
    private Map<String, Map<String, Object>> uploadedFiles; // Optional

    private String signatureName; // Optional

    private String declarationName; // Optional

    private String declarationDate; // Optional - ISO date string

    private Map<String, Object> personPhotoInfo; // Optional

    @NotNull(message = "Declaration must be accepted")
    private Boolean declarationAccepted;

    // Constructors
    public VendorRegistrationRequest() {
    }

    // Getters and Setters
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

    public List<String> getLandlineNumbers() {
        return landlineNumbers;
    }

    public void setLandlineNumbers(List<String> landlineNumbers) {
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

    public List<String> getCustomAmenities() {
        return customAmenities;
    }

    public void setCustomAmenities(List<String> customAmenities) {
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
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

    public String getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(String declarationDate) {
        this.declarationDate = declarationDate;
    }

    public Map<String, Object> getPersonPhotoInfo() {
        return personPhotoInfo;
    }

    public void setPersonPhotoInfo(Map<String, Object> personPhotoInfo) {
        this.personPhotoInfo = personPhotoInfo;
    }

    public Boolean getDeclarationAccepted() {
        return declarationAccepted;
    }

    public void setDeclarationAccepted(Boolean declarationAccepted) {
        this.declarationAccepted = declarationAccepted;
    }
}

