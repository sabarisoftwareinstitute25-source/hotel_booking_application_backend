package com.hotelbooking.mobileapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SixStarHotelProfileDTO {

    private String hotelId;

    private String hotelCategory;

    private String hotelName;

    private String hotelType;

    private Integer yearOfEstablishment;

    private Integer totalRooms;

    private String nationalRecognition;

    private String ownerName;

    private String designation;

    private String managerName;

    private String mobileNumber;

    private String alternateContact;

    private String email;

    private String website;

    private String profilePhoto;

    // Address
    private String addressLine1;

    private String addressLine2;

    private Boolean isPrimary = true;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pinCode;

    // Room Configuration
    private List<String> selectedRoomTypes = new ArrayList<>();

    // Accommodation
    private Integer luxuryUnits;
    private Integer luxuryMaxOccupancy;
    private String luxuryBedType;
    private Double luxuryPriceFrom;
    private Double luxuryPriceTo;

    private Integer clubUnits;
    private Integer clubMaxOccupancy;
    private String clubBedType;
    private Double clubPriceFrom;
    private Double clubPriceTo;

    private Integer executiveUnits;
    private Integer executiveMaxOccupancy;
    private String executiveBedType;
    private Double executivePriceFrom;
    private Double executivePriceTo;

    private Integer presidentialUnits;
    private Integer presidentialMaxOccupancy;
    private String presidentialBedType;
    private Double presidentialPriceFrom;
    private Double presidentialPriceTo;

    private Integer villaUnits;
    private Integer villaMaxOccupancy;
    private String villaBedType;
    private Double villaPriceFrom;
    private Double villaPriceTo;

    private Boolean personalButlerService;
    private Boolean dynamicPricingEnabled;

    // Amenities
    private List<String> roomAmenities = new ArrayList<>();

    private List<String> eliteServices = new ArrayList<>();

    private List<String> diningAndEvent = new ArrayList<>();

    private List<String> wellnessAndLeisure = new ArrayList<>();

    private List<String> guestPrivileges = new ArrayList<>();

    // Check-in
    private LocalTime standardCheckInTime;

    private LocalTime standardCheckOutTime;

    private String earlyCheckInLateCheckOut;

    private Boolean diplomaticProtocols;

    private Boolean petService;

    // Legal
    private String gstNumber;

    private String panNumber;

    private String tradeLicenseNumber;

    private String fssaiLicenseNumber;

    private List<String> compliance = new ArrayList<>();

    // Bank
    private String accountHolderName;

    private String bankName;

    private Integer accountNumber;

    private String ifscCode;

    private String branch;

    private String accountType;

    // Documents Required
    private String gstCertificate;

    private String panCard;

    private String tradeLicense;

    private String fssaiLicense;

    private String fireSafety;

    private String environmentalCertificate;

    private String internationalSafety;

    private String luxuryBrand;

    private String cancelledCheque;

    private String highResolutionProperty;

    private Map<String, Map<String, Object>> uploadedFiles;
}
