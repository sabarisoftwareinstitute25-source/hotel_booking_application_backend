package com.hotelbooking.mobileapp.dto;


import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VendorProfileDTO {

    // Person Details
    private String ownerName;
    private String email;
    private String phoneNumber;

    // Identity Card
    private String aadharCardUrl;
    private String profilePhotoUrl;

    // Hotel Information
    private String hotelName;
    private String hotelType;
    private String hotelCategory;
    private Integer totalRooms;

    // Contact Details
    //private String email;
    //private String phoneNumber;

    // Address Details
    private String addressLine1;
    private String city;
    private String district;
    private String state;

    // Landmark
    private String nearestLandmark;

    // Websites
    private String webSiteUrl;

    // Room Types & Availability


    // Price Range
    private BigDecimal minTariff;
    private BigDecimal maxTariff;

    // Room Policies
    private Boolean extraBedFacility;

    // Amenities


    // Legal Documents


    // Bank Account Details
    private String accountHolder;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branch;
    private String accountType;

    // Business Documents
    private String gstCertificate;
    private String fssaiLicense;
    private String tradeLicense;

    // --- Declaration ---
    @AssertTrue(message = "Declaration must be accepted")
    private boolean declarationAccepted;

    // --- Status (Optional for response) ---
    private String declarationStatus; // Accepted / Not Accepted
    private boolean documentsUploaded;
}
