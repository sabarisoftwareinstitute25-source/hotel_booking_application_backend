package com.hotelbooking.mobileapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ThreeStarHotelProfileDTO {

    private String hotelId;
    private String propertyType;
    private String hotelName;
    private String hotelType;
    private Integer yearOfEstablishment;
    private Integer totalRooms;
    private String registrationNumber;
    private String ownerName;
    private String designation;
    private String mobileNumber;
    private String alternateContact;
    private String email;
    private String website;
    private List<String> personPhotoInfo = new ArrayList<>();
    private String addressLine1;
    private String addressLine2;
    private Boolean isPrimary = true;
    private String city;
    private String district;
    private String state;
    private String country;
    private String pinCode;
    private List<String> selectedRoomTypes = new ArrayList<>();

    //====Standard=====
    private Integer standardNumberOfRooms;
    private Integer standardMaxOccupancy;
    private Boolean standardAc;
    private String standardBedType;
    private Double standardPricePerDay;

    //======Deluxe========
    private Integer deluxeNumberOfRooms;
    private Integer deluxeMaxOccupancy;
    private Boolean deluxeAc;
    private String deluxeBedType;
    private Double deluxePricePerDay;

    //======Suite======
    private Integer suiteNumberOfRooms;
    private Integer suiteMaxOccupancy;
    private Boolean suiteAc;
    private String suiteBedType;
    private Double suitePricePerDay;

    private Boolean extraBedAvailable;
    private Boolean seasonalPricing;
    private List<String> roomAmenities = new ArrayList<>();
    private List<String> hotelFacilities = new ArrayList<>();
    private List<String> foodBeverage = new ArrayList<>();
    private List<String> guestService = new ArrayList<>();
    private LocalTime standardCheckInTime;
    private LocalTime standardCheckOutTime;
    private List<String> earlyCheckInLateCheckOut = new ArrayList<>();
    private Boolean petsAllowed;
    private String gstNumber;
    private String fssaiLicenseNumber;
    private String tradeLicenseNumber;
    private String panNumber;
    private Boolean fireSafety;
    private String accountHolderName;
    private String bankName;
    private Integer accountNumber;
    private String ifscCode;
    private String branch;
    private String accountType;
    // Document Required
    private String gstCertificate;
    private String panCard;
    private String tradeLicense;
    private String fssaiCertificate;
    private String fireSafetyCertificate;
    private String cancelledCheque;
    private String hotelRoomPhotos;
    private Map<String, Map<String, Object>> uploadedFiles = new HashMap<>();
    private Boolean declarationAccepted = false;
    private String signatureImage;
    private String uploadSignature;
    private LocalDateTime declarationDate;
    private String signatoryName;
    private LocalDate Date;
}
