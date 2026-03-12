package com.hotelbooking.mobileapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TwoStarHotelProfileDTO {

    private String hotelId;

    private String hotelCategory;

    private String hotelName;

    private String hotelType;

    private Integer yearOfEstablishment;

    private Integer totalRooms;

    private String ownerName;

    private String designation;

    private String mobileNumber;

    private String alternateContact;

    private String email;

    private String website;

    private String profilePhoto;

    private String addressLine1;

    private String addressLine2;

    private Boolean isPrimary = true;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pinCode;

    private List<String> selectedRoomTypes = new ArrayList<>();

    // Single Room Details
    private Integer singleNumberOfRooms;
    private Integer singleMaxOccupancy;
    private Double singlePricePerNight;
    private String singleAcOrNonAc;

    // Double Room Details
    private Integer doubleNumberOfRooms;
    private Integer doubleMaxOccupancy;
    private Double doublePricePerNight;
    private String doubleAcOrNonAc;

    // Deluxe Room Details
    private Integer deluxeNumberOfRooms;
    private Integer deluxeMaxOccupancy;
    private Double deluxePricePerNight;
    private String deluxeAcOrNonAc;

    private Boolean extraBedFacility;

    private Double minPricePerDay;

    private Double maxPricePerDay;

    private List<String> roomAmenities = new ArrayList<>();

    private List<String> hotelFacilities = new ArrayList<>();

    private List<String> foodBeverage = new ArrayList<>();

    private List<String> guestService = new ArrayList<>();

    private LocalTime standardCheckInTime;

    private LocalTime standardCheckOutTime;

    private Boolean coupleFriendly;

    private String idProofRequired;

    private Boolean petsAllowed;

    private String gstNumber;

    private String fssaiLicenseNumber;

    private String tradeLicenseNumber;

    private String panNumber;

    private String accountHolderName;

    private String bankName;

    private Integer accountNumber;

    private String ifscCode;

    private String branch;

    private String accountType;

    // Documents to be Submitted
    private String gstCertificate;

    private String tradeLicense;

    private String fssaiCertificate;

    private String cancelledCheque;

    private String hotelRegistrationCertificate;

    private String roomPropertyPhoto;

    private Map<String, Map<String, Object>> uploadedFiles = new HashMap<>();

//    private Boolean declarationAccepted = false;
//
//    private String signatureImage;
//
//    private String uploadSignature;
//
//    private String declarationName;
//
//    private LocalDateTime date;
}
