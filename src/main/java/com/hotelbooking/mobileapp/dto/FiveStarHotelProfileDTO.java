package com.hotelbooking.mobileapp.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FiveStarHotelProfileDTO {

    private String hotelId;

    private String hotelCategory;

    private String hotelName;

    private String hotelType;

    private Integer yearOfEstablishment;

    private Integer totalRooms;

    private String starClassificationNo;

    private String addressLine1;

    private String addressLine2;

    private Boolean isPrimary = true;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pinCode;

    private String ownerName;

    private String designation;

    private String mobileNumber;

    private String alternateContact;

    private String email;

    private String website;

    private String profilePhoto;

    private List<String> selectedRoomTypes = new ArrayList<>();

    // Deluxe Room
    private Integer deluxeNoOfUnits;
    private Integer deluxeMaxOccupancy;
    private Boolean deluxeClimateControl;
    private String deluxeBedType;
    private Double deluxeMinPricePerDay;
    private Double deluxeMaxPricePerDay;

    // Club Room
    private Integer clubNoOfUnits;
    private Integer clubMaxOccupancy;
    private Boolean clubClimateControl;
    private String clubBedType;
    private Double clubMinPricePerDay;
    private Double clubMaxPricePerDay;

    // Executive Room
    private Integer executiveNoOfUnits;
    private Integer executiveMaxOccupancy;
    private Boolean executiveClimateControl;
    private String executiveBedType;
    private Double executiveMinPricePerDay;
    private Double executiveMaxPricePerDay;

    // Suite Room
    private Integer suiteNoOfUnits;
    private Integer suiteMaxOccupancy;
    private Boolean suiteClimateControl;
    private String suiteBedType;
    private Double suiteMinPricePerDay;
    private Double suiteMaxPricePerDay;

    // Presidential Suite
    private Integer presidentialNoOfUnits;
    private Integer presidentialMaxOccupancy;
    private Boolean presidentialClimateControl;
    private String presidentialBedType;
    private Double presidentialMinPricePerDay;
    private Double presidentialMaxPricePerDay;

    private Boolean extraBedAvailable;

    private Boolean seasonalPricing;

    private List<String> roomAmenities = new ArrayList<>();

    private List<String> hotelService = new ArrayList<>();

    private List<String> diningAndEvent = new ArrayList<>();

    private List<String> wellnessAndLeisure = new ArrayList<>();

    private List<String> guestService = new ArrayList<>();

    private LocalTime standardCheckInTime;

    private LocalTime standardCheckOutTime;

    private List<String> earlyCheckInLateCheckOut = new ArrayList<>();

    private Boolean petsAllowed;

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

    // Documents
    private String gstCertificate;

    private String panCard;

    private String tradeLicense;

    private String fssaiCertificate;

    private String fireSafetyNoc;

    private String starCertification;

    private String pollutionControlCertificate;

    private String cancelledCheque;

    private String property;

    private Map<String, Map<String, Object>> uploadedFiles;

}
