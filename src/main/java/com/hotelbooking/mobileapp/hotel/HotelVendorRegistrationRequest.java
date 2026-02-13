package com.hotelbooking.mobileapp.hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Comprehensive request DTO for hotel vendor registration.
 * Matches all fields from the frontend 5-step registration form.
 */
public class HotelVendorRegistrationRequest {

    // Step 1: Basic Details - Property Information
    // Property type is optional for backward compatibility (defaults to "Hotel" if not provided)
    @Pattern(regexp = "Hotel|Villa|Apartment|Resort", message = "Property type must be: Hotel, Villa, Apartment, or Resort")
    private String propertyType; // Hotel, Villa, Apartment, Resort (optional, defaults to "Hotel")

    @NotBlank(message = "Property name is required")
    @Size(max = 150, message = "Property name cannot exceed 150 characters")
    private String hotelName; // Property name (hotel/villa/apartment/resort name)

    @NotBlank(message = "Property category is required")
    @Size(max = 50, message = "Property category cannot exceed 50 characters")
    private String hotelType; // For Hotel: Lodge, Budget Hotel, Standard Hotel, Guest House, Heritage Hotel, Boutique Hotel. For Villa: Private Villa, Bungalow. For Apartment: Serviced Apartment. For Resort: Beach Resort, Hill Resort

    @Size(max = 4, message = "Year must be 4 digits")
    private String yearOfEstablishment;

    @Size(max = 10, message = "Total rooms cannot exceed 10 digits")
    private String totalRooms;

    // Step 1: Contact Information
    @NotBlank(message = "Owner/Manager name is required")
    @Size(max = 100, message = "Owner name cannot exceed 100 characters")
    private String ownerName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
    @Size(max = 20, message = "Mobile number cannot exceed 20 characters")
    private String mobileNumber;

    @Size(max = 20, message = "Alternate contact cannot exceed 20 characters")
    private String alternateContact;

    private List<String> landlineNumbers; // Optional, can have multiple

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    private String email;

    @Size(max = 255, message = "Website cannot exceed 255 characters")
    private String website;

    // Profile photo info (file metadata)
    private Map<String, Object> personPhotoInfo;

    // Step 2: Hotel Address
    @NotBlank(message = "Address line 1 is required")
    @Size(max = 255, message = "Address line 1 cannot exceed 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address line 2 cannot exceed 255 characters")
    private String addressLine2;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @Size(max = 100, message = "District cannot exceed 100 characters")
    private String district; // Optional field

    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State cannot exceed 100 characters")
    private String state;

    @NotBlank(message = "PIN code is required")
    @Pattern(regexp = "^\\d{6}$", message = "PIN code must be 6 digits")
    private String pinCode;

    @Size(max = 255, message = "Landmark cannot exceed 255 characters")
    private String landmark;

    // Step 3: Room Availability
    private Map<String, Boolean> selectedRoomTypes; // Room type -> selected (true/false)
    private Map<String, Map<String, Object>> roomDetails; // Room type -> details (rooms, occupancy, price, ac, extraBed, etc.)
    
    @Size(max = 20, message = "Min tariff cannot exceed 20 characters")
    private String minTariff;

    @Size(max = 20, message = "Max tariff cannot exceed 20 characters")
    private String maxTariff;

    private Boolean extraBedAvailable;

    // Step 4: Amenities & Legal
    private Map<String, Boolean> basicAmenities; // Amenity name -> available (true/false)
    private Map<String, Boolean> hotelFacilities;
    private Map<String, Boolean> foodServices;
    private Map<String, Boolean> additionalAmenities;
    private List<String> customAmenities; // User-added amenities

    @Size(min = 15, max = 17, message = "GSTIN must be 15 characters (spaces allowed for formatting)")
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "GSTIN must be in format: 27ABCDE1234F1Z5 (2-digit state code + 10-digit PAN + entity number + Z + checksum)")
    private String gstNumber;

    @Size(max = 50, message = "FSSAI license cannot exceed 50 characters")
    @Pattern(regexp = "^[12]\\d{13}$", message = "FSSAI license must be 14 digits, starting with 1 (registered) or 2 (licensed)")
    private String fssaiLicense;

    @Size(max = 50, message = "Trade license cannot exceed 50 characters")
    private String tradeLicense;

    @Size(max = 20, message = "PAN number cannot exceed 20 characters")
    private String panNumber;

    @Size(min = 12, max = 14, message = "Aadhar number must be 12 digits (spaces allowed for formatting)")
    @Pattern(regexp = "^\\d{12}$|^\\d{4}\\s\\d{4}\\s\\d{4}$", message = "Aadhar number must be 12 digits (format: 1234 5678 9012 or 123456789012)")
    private String aadharNumber;

    // Step 5: Bank & Documents
    @NotBlank(message = "Account holder name is required")
    @Size(max = 100, message = "Account holder name cannot exceed 100 characters")
    private String accountHolderName;

    @NotBlank(message = "Bank name is required")
    @Size(max = 100, message = "Bank name cannot exceed 100 characters")
    private String bankName;

    @NotBlank(message = "Account number is required")
    @Size(min = 9, max = 18, message = "Account number must be between 9 and 18 characters")
    @Pattern(regexp = "^[A-Za-z0-9]{9,18}$", message = "Account number must contain only letters (A-Z, a-z) and numbers (0-9)")
    private String accountNumber; // Format: 9-18 alphanumeric characters (letters and numbers, case-insensitive)

    @NotBlank(message = "IFSC code is required")
    @Size(min = 11, max = 11, message = "IFSC code must be exactly 11 characters")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "IFSC code must be in format: AAAA0XXXXXX (4 letters, 1 zero, 6 alphanumeric)")
    private String ifscCode; // Format: AAAA0XXXXXX (Bank Name: 4 letters, Reserved: 0, Branch Code: 6 alphanumeric)

    @Size(max = 100, message = "Branch cannot exceed 100 characters")
    private String branch;

    @Size(max = 20, message = "Account type cannot exceed 20 characters")
    private String accountType; // Savings / Current

    // Document uploads (file metadata)
    private Map<String, Map<String, Object>> uploadedFiles; // Document name -> file info (name, size, path, uploaded)

    @Size(max = 100, message = "Signature name cannot exceed 100 characters")
    private String signatureName;

    @Size(max = 100, message = "Declaration name cannot exceed 100 characters")
    private String declarationName;

    private String declarationDate; // ISO date string

    @NotNull(message = "Declaration must be accepted")
    private Boolean declarationAccepted;

    // Constructors
    public HotelVendorRegistrationRequest() {
    }

    // Getters and Setters
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

    public Boolean getDeclarationAccepted() {
        return declarationAccepted;
    }

    public void setDeclarationAccepted(Boolean declarationAccepted) {
        this.declarationAccepted = declarationAccepted;
    }
}

