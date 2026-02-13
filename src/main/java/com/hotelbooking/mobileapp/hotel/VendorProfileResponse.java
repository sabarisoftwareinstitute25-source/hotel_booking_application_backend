package com.hotelbooking.mobileapp.hotel;

/**
 * Response DTO for vendor profile information.
 */
public class VendorProfileResponse {
    private boolean success;
    private String message;
    private String registrationId;
    private String hotelId;
    private String ownerName;
    private String hotelName;
    private String mobileNumber;
    private String email;
    private String profileImageUrl;
    private java.util.Map<String, Object> personPhotoInfo;
    private java.util.Map<String, java.util.Map<String, Object>> uploadedFiles;
    private String propertyType; // Hotel, Villa, Apartment, Resort
    private String hotelType;
    private String yearOfEstablishment;
    private String website;
    private String landmark;
    private String totalRooms;
    private String alternateContact;
    private java.util.List<String> landlineNumbers;
    // Address fields
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private String state;
    private String pinCode;
    // Legal documents
    private String gstNumber;
    private String fssaiLicense;
    private String tradeLicense;
    private String panNumber;
    private String aadharNumber;
    // Room details
    private java.util.Map<String, Boolean> selectedRoomTypes;
    private java.util.Map<String, java.util.Map<String, Object>> roomDetails;
    private String minTariff;
    private String maxTariff;
    private Boolean extraBedAvailable;
    // Amenities
    private java.util.Map<String, Boolean> basicAmenities;
    private java.util.Map<String, Boolean> hotelFacilities;
    private java.util.Map<String, Boolean> foodServices;
    private java.util.Map<String, Boolean> additionalAmenities;
    private java.util.List<String> customAmenities;
    // Bank details
    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branch;
    private String accountType;
    // Declaration
    private String signatureName;
    private String declarationName;
    private String declarationDate;
    private Boolean declarationAccepted;
    private HotelStatistics statistics;

    public VendorProfileResponse() {
    }

    public VendorProfileResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public java.util.Map<String, Object> getPersonPhotoInfo() {
        return personPhotoInfo;
    }

    public void setPersonPhotoInfo(java.util.Map<String, Object> personPhotoInfo) {
        this.personPhotoInfo = personPhotoInfo;
    }

    public java.util.Map<String, java.util.Map<String, Object>> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(java.util.Map<String, java.util.Map<String, Object>> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(String totalRooms) {
        this.totalRooms = totalRooms;
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

    public java.util.Map<String, Boolean> getSelectedRoomTypes() {
        return selectedRoomTypes;
    }

    public void setSelectedRoomTypes(java.util.Map<String, Boolean> selectedRoomTypes) {
        this.selectedRoomTypes = selectedRoomTypes;
    }

    public java.util.Map<String, java.util.Map<String, Object>> getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(java.util.Map<String, java.util.Map<String, Object>> roomDetails) {
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

    public java.util.Map<String, Boolean> getBasicAmenities() {
        return basicAmenities;
    }

    public void setBasicAmenities(java.util.Map<String, Boolean> basicAmenities) {
        this.basicAmenities = basicAmenities;
    }

    public java.util.Map<String, Boolean> getHotelFacilities() {
        return hotelFacilities;
    }

    public void setHotelFacilities(java.util.Map<String, Boolean> hotelFacilities) {
        this.hotelFacilities = hotelFacilities;
    }

    public java.util.Map<String, Boolean> getFoodServices() {
        return foodServices;
    }

    public void setFoodServices(java.util.Map<String, Boolean> foodServices) {
        this.foodServices = foodServices;
    }

    public java.util.Map<String, Boolean> getAdditionalAmenities() {
        return additionalAmenities;
    }

    public void setAdditionalAmenities(java.util.Map<String, Boolean> additionalAmenities) {
        this.additionalAmenities = additionalAmenities;
    }

    public java.util.List<String> getCustomAmenities() {
        return customAmenities;
    }

    public void setCustomAmenities(java.util.List<String> customAmenities) {
        this.customAmenities = customAmenities;
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

    public HotelStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(HotelStatistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Inner class for hotel statistics.
     */
    public static class HotelStatistics {
        private int totalRooms;
        private int activeNow;
        private double occupancy;
        private double rating;

        public HotelStatistics() {
        }

        public HotelStatistics(int totalRooms, int activeNow, double occupancy, double rating) {
            this.totalRooms = totalRooms;
            this.activeNow = activeNow;
            this.occupancy = occupancy;
            this.rating = rating;
        }

        // Getters and Setters
        public int getTotalRooms() {
            return totalRooms;
        }

        public void setTotalRooms(int totalRooms) {
            this.totalRooms = totalRooms;
        }

        public int getActiveNow() {
            return activeNow;
        }

        public void setActiveNow(int activeNow) {
            this.activeNow = activeNow;
        }

        public double getOccupancy() {
            return occupancy;
        }

        public void setOccupancy(double occupancy) {
            this.occupancy = occupancy;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }
    }
}

