package com.hotelbooking.mobileapp.dto;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class NormalHotelVendorProfileDTO {

    private String hotelId;

    private String propertyType;

    private String hotelName;

    private String hotelType;

    private Integer yearOfEstablishment;

    private Integer totalRooms;

    private String profilePhoto;

    private String ownerName;

    private String mobileNumber;

    private String alternateContact;

    private List<String> landlineNumbers = new ArrayList<>();

    private String email;

    private String website;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String district;

    private String state;

    private String pinCode;

    private String landmark;

    private List<String> selectedRoomTypes = new ArrayList<>();

    // Single Room Details
    private Integer singleNumberOfRooms;
    private Integer singleMaxOccupancy;
    private Double singlePricePerNight;
    private String singleAcOrNonAc;
    private Boolean singleExtraBedAvailable;

    // Double Room Details
    private Integer doubleNumberOfRooms;
    private Integer doubleMaxOccupancy;
    private Double doublePricePerNight;
    private String doubleAcOrNonAc;
    private Boolean doubleExtraBedAvailable;

    // Deluxe Room Details
    private Integer deluxeNumberOfRooms;
    private Integer deluxeMaxOccupancy;
    private Double deluxePricePerNight;
    private String deluxeAcOrNonAc;
    private Boolean deluxeExtraBedAvailable;

    // Suite Room Details
    private Integer suiteNumberOfRooms;
    private Integer suiteMaxOccupancy;
    private Double suitePricePerNight;
    private String suiteAcOrNonAc;
    private Boolean suiteExtraBedAvailable;

    // Family Room Details
    private Integer familyNumberOfRooms;
    private Integer familyMaxOccupancy;
    private Double familyPricePerNight;
    private String familyAcOrNonAc;
    private Boolean familyExtraBedAvailable;

    // Executive Room Details
    private Integer executiveNumberOfRooms;
    private Integer executiveMaxOccupancy;
    private Double executivePricePerNight;
    private String executiveAcOrNonAc;
    private Boolean executiveExtraBedAvailable;

    private Double minTariff;

    private Double maxTariff;

    private Boolean extraBedAvailable;

    private List<String> basicAmenities = new ArrayList<>();

    private List<String> hotelFacilities = new ArrayList<>();

    private List<String> foodServices = new ArrayList<>();

    private List<String> additionalAmenities = new ArrayList<>();

    private List<String> customAmenities = new ArrayList<>();

    // Legal
    private String gstNumber;
    private String fssaiLicenseNumber;
    private String tradeLicenseNumber;
    private String aadhaarNumber;

    private String accountHolderName;

    private String bankName;

    private String accountNumber;

    private String ifscCode;

    private String branch;

    private String accountType; // SAVINGS / CURRENT

    // Documents Required
    private String fssaiCertificate;
    private String gstCertificate;
    private String tradeLicense;
    private String hotelPhoto;
    private String cancelledCheque;
    private String ownerIdProof;

    private Map<String, Map<String, Object>> uploadedFiles = new HashMap<>();

    private Boolean declarationAccepted = false;

    private String signatureImage;

    private String uploadSignature;

    private String declarationName;
    private LocalDateTime declarationDate;
}
