package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.dto.FiveStarHotelProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FiveStarHotelService {

    private final FiveStarHotelRepository repository;

    public FiveStarHotel saveHotel(FiveStarHotel hotel) {
        return repository.save(hotel);
    }

    public List<FiveStarHotel> getAllHotels() {
        return repository.findAll();
    }

    public FiveStarHotel getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }

    // Get Full Hotel Profile
    public FiveStarHotelProfileDTO getHotelProfile(String hotelId) {

        FiveStarHotel hotel = repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Five Star Hotel not found"));

        FiveStarHotelProfileDTO dto = new FiveStarHotelProfileDTO();

        dto.setHotelId(hotel.getHotelId());
        dto.setHotelCategory(hotel.getHotelCategory());
        dto.setHotelName(hotel.getHotelName());
        dto.setHotelType(hotel.getHotelType());
        dto.setYearOfEstablishment(hotel.getYearOfEstablishment());
        dto.setTotalRooms(hotel.getTotalRooms());
        dto.setStarClassificationNo(hotel.getStarClassificationNo());

        dto.setAddressLine1(hotel.getAddressLine1());
        dto.setAddressLine2(hotel.getAddressLine2());
        dto.setIsPrimary(hotel.getIsPrimary());
        dto.setCity(hotel.getCity());
        dto.setDistrict(hotel.getDistrict());
        dto.setState(hotel.getState());
        dto.setCountry(hotel.getCountry());
        dto.setPinCode(hotel.getPinCode());

        dto.setOwnerName(hotel.getOwnerName());
        dto.setDesignation(hotel.getDesignation());
        dto.setMobileNumber(hotel.getMobileNumber());
        dto.setAlternateContact(hotel.getAlternateContact());
        dto.setEmail(hotel.getEmail());
        dto.setWebsite(hotel.getWebsite());
        dto.setProfilePhoto(hotel.getProfilePhoto());

        dto.setSelectedRoomTypes(hotel.getSelectedRoomTypes());

        // Deluxe
        dto.setDeluxeNoOfUnits(hotel.getDeluxeNoOfUnits());
        dto.setDeluxeMaxOccupancy(hotel.getDeluxeMaxOccupancy());
        dto.setDeluxeClimateControl(hotel.getDeluxeClimateControl());
        dto.setDeluxeBedType(hotel.getDeluxeBedType());
        dto.setDeluxeMinPricePerDay(hotel.getDeluxeMinPricePerDay());
        dto.setDeluxeMaxPricePerDay(hotel.getDeluxeMaxPricePerDay());

        // Club
        dto.setClubNoOfUnits(hotel.getClubNoOfUnits());
        dto.setClubMaxOccupancy(hotel.getClubMaxOccupancy());
        dto.setClubClimateControl(hotel.getClubClimateControl());
        dto.setClubBedType(hotel.getClubBedType());
        dto.setClubMinPricePerDay(hotel.getClubMinPricePerDay());
        dto.setClubMaxPricePerDay(hotel.getClubMaxPricePerDay());

        // Executive
        dto.setExecutiveNoOfUnits(hotel.getExecutiveNoOfUnits());
        dto.setExecutiveMaxOccupancy(hotel.getExecutiveMaxOccupancy());
        dto.setExecutiveClimateControl(hotel.getExecutiveClimateControl());
        dto.setExecutiveBedType(hotel.getExecutiveBedType());
        dto.setExecutiveMinPricePerDay(hotel.getExecutiveMinPricePerDay());
        dto.setExecutiveMaxPricePerDay(hotel.getExecutiveMaxPricePerDay());

        // Suite
        dto.setSuiteNoOfUnits(hotel.getSuiteNoOfUnits());
        dto.setSuiteMaxOccupancy(hotel.getSuiteMaxOccupancy());
        dto.setSuiteClimateControl(hotel.getSuiteClimateControl());
        dto.setSuiteBedType(hotel.getSuiteBedType());
        dto.setSuiteMinPricePerDay(hotel.getSuiteMinPricePerDay());
        dto.setSuiteMaxPricePerDay(hotel.getSuiteMaxPricePerDay());

        // Presidential
        dto.setPresidentialNoOfUnits(hotel.getPresidentialNoOfUnits());
        dto.setPresidentialMaxOccupancy(hotel.getPresidentialMaxOccupancy());
        dto.setPresidentialClimateControl(hotel.getPresidentialClimateControl());
        dto.setPresidentialBedType(hotel.getPresidentialBedType());
        dto.setPresidentialMinPricePerDay(hotel.getPresidentialMinPricePerDay());
        dto.setPresidentialMaxPricePerDay(hotel.getPresidentialMaxPricePerDay());

        dto.setExtraBedAvailable(hotel.getExtraBedAvailable());
        dto.setSeasonalPricing(hotel.getSeasonalPricing());

        dto.setRoomAmenities(hotel.getRoomAmenities());
        dto.setHotelService(hotel.getHotelService());
        dto.setDiningAndEvent(hotel.getDiningAndEvent());
        dto.setWellnessAndLeisure(hotel.getWellnessAndLeisure());
        dto.setGuestService(hotel.getGuestService());

        dto.setStandardCheckInTime(hotel.getStandardCheckInTime());
        dto.setStandardCheckOutTime(hotel.getStandardCheckOutTime());
        dto.setEarlyCheckInLateCheckOut(hotel.getEarlyCheckInLateCheckOut());
        dto.setPetsAllowed(hotel.getPetsAllowed());

        dto.setGstNumber(hotel.getGstNumber());
        dto.setPanNumber(hotel.getPanNumber());
        dto.setTradeLicenseNumber(hotel.getTradeLicenseNumber());
        dto.setFssaiLicenseNumber(hotel.getFssaiLicenseNumber());
        dto.setCompliance(hotel.getCompliance());

        dto.setAccountHolderName(hotel.getAccountHolderName());
        dto.setBankName(hotel.getBankName());
        dto.setAccountNumber(hotel.getAccountNumber());
        dto.setIfscCode(hotel.getIfscCode());
        dto.setBranch(hotel.getBranch());
        dto.setAccountType(hotel.getAccountType());

        dto.setGstCertificate(hotel.getGstCertificate());
        dto.setPanCard(hotel.getPanCard());
        dto.setTradeLicense(hotel.getTradeLicense());
        dto.setFssaiCertificate(hotel.getFssaiCertificate());
        dto.setFireSafetyNoc(hotel.getFireSafetyNoc());
        dto.setStarCertification(hotel.getStarCertification());
        dto.setPollutionControlCertificate(hotel.getPollutionControlCertificate());
        dto.setCancelledCheque(hotel.getCancelledCheque());
        dto.setProperty(hotel.getProperty());

        dto.setUploadedFiles(hotel.getUploadedFiles());

        return dto;
    }
}