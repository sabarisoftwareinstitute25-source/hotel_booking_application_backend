package com.hotelbooking.mobileapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FourStarHotelProfileDTO {

    private String hotelId;

    private String hotelName;

    private String hotelCategory;

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

    private Integer superiorNumberOfRooms;

    private Integer superiorMaxOccupancy;

    private Double superiorPricePerDay;

    private Boolean superiorAc;

    private String superiorBedSize;

    private Integer deluxeNumberOfRooms;

    private Integer deluxeMaxOccupancy;

    private Double deluxePricePerDay;

    private String deluxeBedType;

    private Boolean deluxeAc;

    private Integer executiveNumberOfRooms;

    private Integer executiveMaxOccupancy;

    private Double executivePricePerDay;

    private String executiveBedType;

    private Boolean executiveAc;

    private Integer suiteNumberOfRooms;

    private Integer suiteMaxOccupancy;

    private Double suitePricePerDay;

    private String suiteBedType;

    private Boolean suiteAc;

    private Boolean extraBedAvailable;

    private Boolean seasonalPricing;

    private List<String> roomAmenities = new ArrayList<>();

    private List<String> hotelFacilities = new ArrayList<>();

    private List<String> foodBeverage = new ArrayList<>();

    private List<String> guestService = new ArrayList<>();

    private List<String> wellness = new ArrayList<>();

    private LocalTime standardCheckInTime;

    private LocalTime standardCheckOutTime;

    private String earlyCheckInLateCheckOut;

    private Boolean petsAllowed;

    private String gstNumber;

    private String fssaiLicenseNumber;

    private String tradeLicenseNumber;

    private String panNumber;

    private Boolean fireSafety;

    private Boolean starCertification;

    private String accountHolderName;

    private String bankName;

    private Integer accountNumber;

    private String ifscCode;

    private String branch;

    private String accountType;

    private String gstCertificate;

    private String panCard;

    private String tradeLicense;

    private String fssaiCertificate;

    private String fireSafetyCertificate;

    private String starClassificationCertificate;

    private String cancelledCheque;

    private String hotelRoomPhotos;

    private Map<String, Map<String, Object>> uploadedFiles;

//    private Boolean declarationAccepted = false;
//    private String signatureImage;
//    private String uploadSignature;
//    private String signatoryName;
//    private LocalDateTime Date;
}
