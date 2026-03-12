package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.dto.SixStarHotelProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SixStarHotelService {

    private final SixStarHotelRepository repository;

    public SixStarHotel saveHotel(SixStarHotel hotel) {
        return repository.save(hotel);
    }

    public List<SixStarHotel> getAllHotels() {
        return repository.findAll();
    }

    public SixStarHotel getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }

    public SixStarHotelProfileDTO getHotelProfile(String hotelId) {

        SixStarHotel hotel = repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Six Star Hotel not found"));

        SixStarHotelProfileDTO dto = new SixStarHotelProfileDTO();

        dto.setHotelId(hotel.getHotelId());
        dto.setHotelCategory(hotel.getHotelCategory());
        dto.setHotelName(hotel.getHotelName());
        dto.setHotelType(hotel.getHotelType());
        dto.setYearOfEstablishment(hotel.getYearOfEstablishment());
        dto.setTotalRooms(hotel.getTotalRooms());
        dto.setNationalRecognition(hotel.getNationalRecognition());

        dto.setOwnerName(hotel.getOwnerName());
        dto.setDesignation(hotel.getDesignation());
        dto.setManagerName(hotel.getManagerName());
        dto.setMobileNumber(hotel.getMobileNumber());
        dto.setAlternateContact(hotel.getAlternateContact());
        dto.setEmail(hotel.getEmail());
        dto.setWebsite(hotel.getWebsite());
        dto.setProfilePhoto(hotel.getProfilePhoto());

        // Address
        dto.setAddressLine1(hotel.getAddressLine1());
        dto.setAddressLine2(hotel.getAddressLine2());
        dto.setIsPrimary(hotel.getIsPrimary());
        dto.setCity(hotel.getCity());
        dto.setDistrict(hotel.getDistrict());
        dto.setState(hotel.getState());
        dto.setCountry(hotel.getCountry());
        dto.setPinCode(hotel.getPinCode());

        dto.setSelectedRoomTypes(hotel.getSelectedRoomTypes());

        // Luxury Room
        dto.setLuxuryUnits(hotel.getLuxuryUnits());
        dto.setLuxuryMaxOccupancy(hotel.getLuxuryMaxOccupancy());
        dto.setLuxuryBedType(hotel.getLuxuryBedType());
        dto.setLuxuryPriceFrom(hotel.getLuxuryPriceFrom());
        dto.setLuxuryPriceTo(hotel.getLuxuryPriceTo());

        // Club Room
        dto.setClubUnits(hotel.getClubUnits());
        dto.setClubMaxOccupancy(hotel.getClubMaxOccupancy());
        dto.setClubBedType(hotel.getClubBedType());
        dto.setClubPriceFrom(hotel.getClubPriceFrom());
        dto.setClubPriceTo(hotel.getClubPriceTo());

        // Executive Room
        dto.setExecutiveUnits(hotel.getExecutiveUnits());
        dto.setExecutiveMaxOccupancy(hotel.getExecutiveMaxOccupancy());
        dto.setExecutiveBedType(hotel.getExecutiveBedType());
        dto.setExecutivePriceFrom(hotel.getExecutivePriceFrom());
        dto.setExecutivePriceTo(hotel.getExecutivePriceTo());

        // Presidential
        dto.setPresidentialUnits(hotel.getPresidentialUnits());
        dto.setPresidentialMaxOccupancy(hotel.getPresidentialMaxOccupancy());
        dto.setPresidentialBedType(hotel.getPresidentialBedType());
        dto.setPresidentialPriceFrom(hotel.getPresidentialPriceFrom());
        dto.setPresidentialPriceTo(hotel.getPresidentialPriceTo());

        // Villa
        dto.setVillaUnits(hotel.getVillaUnits());
        dto.setVillaMaxOccupancy(hotel.getVillaMaxOccupancy());
        dto.setVillaBedType(hotel.getVillaBedType());
        dto.setVillaPriceFrom(hotel.getVillaPriceFrom());
        dto.setVillaPriceTo(hotel.getVillaPriceTo());

        dto.setPersonalButlerService(hotel.getPersonalButlerService());
        dto.setDynamicPricingEnabled(hotel.getDynamicPricingEnabled());

        // Amenities
        dto.setRoomAmenities(hotel.getRoomAmenities());
        dto.setEliteServices(hotel.getEliteServices());
        dto.setDiningAndEvent(hotel.getDiningAndEvent());
        dto.setWellnessAndLeisure(hotel.getWellnessAndLeisure());
        dto.setGuestPrivileges(hotel.getGuestPrivileges());

        // Check-in
        dto.setStandardCheckInTime(hotel.getStandardCheckInTime());
        dto.setStandardCheckOutTime(hotel.getStandardCheckOutTime());
        dto.setEarlyCheckInLateCheckOut(hotel.getEarlyCheckInLateCheckOut());
        dto.setDiplomaticProtocols(hotel.getDiplomaticProtocols());
        dto.setPetService(hotel.getPetService());

        // Legal
        dto.setGstNumber(hotel.getGstNumber());
        dto.setPanNumber(hotel.getPanNumber());
        dto.setTradeLicenseNumber(hotel.getTradeLicenseNumber());
        dto.setFssaiLicenseNumber(hotel.getFssaiLicenseNumber());
        dto.setCompliance(hotel.getCompliance());

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
        dto.setFssaiLicense(hotel.getFssaiLicense());
        dto.setFireSafety(hotel.getFireSafety());
        dto.setEnvironmentalCertificate(hotel.getEnvironmentalCertificate());
        dto.setInternationalSafety(hotel.getInternationalSafety());
        dto.setLuxuryBrand(hotel.getLuxuryBrand());
        dto.setCancelledCheque(hotel.getCancelledCheque());
        dto.setHighResolutionProperty(hotel.getHighResolutionProperty());

        dto.setUploadedFiles(hotel.getUploadedFiles());

        return dto;
    }
}