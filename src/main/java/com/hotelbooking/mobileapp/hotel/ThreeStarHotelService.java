package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.dto.ThreeStarHotelProfileDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThreeStarHotelService {

    private final ThreeStarHotelRepository repository;

    public ThreeStarHotelService(ThreeStarHotelRepository repository) {
        this.repository = repository;
    }

    // Save Hotel
    public ThreeStarHotel saveHotel(ThreeStarHotel hotel) {
        return repository.save(hotel);
    }

    // Get All Hotels
    public List<ThreeStarHotel> getAllHotels() {
        return repository.findAll();
    }

    // Get By ID
    public Optional<ThreeStarHotel> getHotelById(String hotelId) {
        return repository.findById(hotelId);
    }

    // Delete
    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }

    // Get Full Hotel Profile
    public ThreeStarHotelProfileDTO getHotelProfile(String hotelId) {

        ThreeStarHotel hotel = repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        ThreeStarHotelProfileDTO dto = new ThreeStarHotelProfileDTO();

        // Property Info
        dto.setHotelId(hotel.getHotelId());
        dto.setPropertyType(hotel.getPropertyType());
        dto.setHotelName(hotel.getHotelName());
        dto.setHotelType(hotel.getHotelType());
        dto.setYearOfEstablishment(hotel.getYearOfEstablishment());
        dto.setTotalRooms(hotel.getTotalRooms());

        // Contact
        dto.setOwnerName(hotel.getOwnerName());
        dto.setDesignation(hotel.getDesignation());
        dto.setMobileNumber(hotel.getMobileNumber());
        dto.setAlternateContact(hotel.getAlternateContact());
        dto.setEmail(hotel.getEmail());
        dto.setWebsite(hotel.getWebsite());

        // Address
        dto.setAddressLine1(hotel.getAddressLine1());
        dto.setAddressLine2(hotel.getAddressLine2());
        dto.setIsPrimary(hotel.getIsPrimary());
        dto.setCity(hotel.getCity());
        dto.setDistrict(hotel.getDistrict());
        dto.setState(hotel.getState());
        dto.setCountry(hotel.getCountry());
        dto.setPinCode(hotel.getPinCode());

        // Room Types
        dto.setSelectedRoomTypes(hotel.getSelectedRoomTypes());

        // Standard
        dto.setStandardNumberOfRooms(hotel.getStandardNumberOfRooms());
        dto.setStandardMaxOccupancy(hotel.getStandardMaxOccupancy());
        dto.setStandardAc(hotel.getStandardAc());
        dto.setStandardBedType(hotel.getStandardBedType());
        dto.setStandardPricePerDay(hotel.getStandardPricePerDay());

        // Deluxe
        dto.setDeluxeNumberOfRooms(hotel.getDeluxeNumberOfRooms());
        dto.setDeluxeMaxOccupancy(hotel.getDeluxeMaxOccupancy());
        dto.setDeluxeAc(hotel.getDeluxeAc());
        dto.setDeluxeBedType(hotel.getDeluxeBedType());
        dto.setDeluxePricePerDay(hotel.getDeluxePricePerDay());

        // Suite
        dto.setSuiteNumberOfRooms(hotel.getSuiteNumberOfRooms());
        dto.setSuiteMaxOccupancy(hotel.getSuiteMaxOccupancy());
        dto.setSuiteAc(hotel.getSuiteAc());
        dto.setSuiteBedType(hotel.getSuiteBedType());
        dto.setSuitePricePerDay(hotel.getSuitePricePerDay());

        dto.setExtraBedAvailable(hotel.getExtraBedAvailable());
        dto.setSeasonalPricing(hotel.getSeasonalPricing());

        // Amenities
        dto.setRoomAmenities(hotel.getRoomAmenities());
        dto.setHotelFacilities(hotel.getHotelFacilities());
        dto.setFoodBeverage(hotel.getFoodBeverage());
        dto.setGuestService(hotel.getGuestService());

        // Checkin
        dto.setStandardCheckInTime(hotel.getStandardCheckInTime());
        dto.setStandardCheckOutTime(hotel.getStandardCheckOutTime());
        dto.setEarlyCheckInLateCheckOut(hotel.getEarlyCheckInLateCheckOut());
        dto.setPetsAllowed(hotel.getPetsAllowed());

        // Legal
        dto.setGstNumber(hotel.getGstNumber());
        dto.setFssaiLicenseNumber(hotel.getFssaiLicenseNumber());
        dto.setTradeLicenseNumber(hotel.getTradeLicenseNumber());
        dto.setPanNumber(hotel.getPanNumber());
        dto.setFireSafety(hotel.getFireSafety());

        // Bank
        dto.setAccountHolderName(hotel.getAccountHolderName());
        dto.setBankName(hotel.getBankName());
        dto.setAccountNumber(hotel.getAccountNumber());
        dto.setIfscCode(hotel.getIfscCode());
        dto.setBranch(hotel.getBranch());
        dto.setAccountType(hotel.getAccountType());

        // Documents
        dto.setGstCertificate(hotel.getGstCertificate());
        dto.setPanCard(hotel.getPanCard());
        dto.setTradeLicense(hotel.getTradeLicense());
        dto.setFssaiCertificate(hotel.getFssaiCertificate());
        dto.setFireSafetyCertificate(hotel.getFireSafetyCertificate());
        dto.setCancelledCheque(hotel.getCancelledCheque());
        dto.setHotelRoomPhotos(hotel.getHotelRoomPhotos());
        dto.setUploadedFiles(hotel.getUploadedFiles());

        // Declaration
        dto.setDeclarationAccepted(hotel.getDeclarationAccepted());
        dto.setSignatureImage(hotel.getSignatureImage());
        dto.setUploadSignature(hotel.getUploadSignature());
        dto.setDeclarationDate(hotel.getDeclarationDate());
        dto.setSignatoryName(hotel.getSignatoryName());

        return dto;
    }

}