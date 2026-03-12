package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.dto.TwoStarHotelProfileDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TwoStarHotelService {

    private final TwoStarHotelRepository repository;

    public TwoStarHotelService(TwoStarHotelRepository repository) {
        this.repository = repository;
    }

    // Save Hotel
    public TwoStarHotel saveHotel(TwoStarHotel hotel) {
        return repository.save(hotel);
    }

    // Get All Hotels
    public List<TwoStarHotel> getAllHotels() {
        return repository.findAll();
    }

    // Get By ID
    public Optional<TwoStarHotel> getHotelById(String hotelId) {
        return repository.findById(hotelId);
    }

    // Delete
    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }

    // GET FULL HOTEL PROFILE
    public TwoStarHotelProfileDTO getHotelProfile(String hotelId) {

        TwoStarHotel hotel = repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        TwoStarHotelProfileDTO dto = new TwoStarHotelProfileDTO();

        // Property
        dto.setHotelId(hotel.getHotelId());
        dto.setHotelCategory(hotel.getHotelCategory());
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

        // Rooms
        dto.setSelectedRoomTypes(hotel.getSelectedRoomTypes());

        dto.setSingleNumberOfRooms(hotel.getSingleNumberOfRooms());
        dto.setSingleMaxOccupancy(hotel.getSingleMaxOccupancy());
        dto.setSinglePricePerNight(hotel.getSinglePricePerNight());
        dto.setSingleAcOrNonAc(hotel.getSingleAcOrNonAc());

        dto.setDoubleNumberOfRooms(hotel.getDoubleNumberOfRooms());
        dto.setDoubleMaxOccupancy(hotel.getDoubleMaxOccupancy());
        dto.setDoublePricePerNight(hotel.getDoublePricePerNight());
        dto.setDoubleAcOrNonAc(hotel.getDoubleAcOrNonAc());

        dto.setDeluxeNumberOfRooms(hotel.getDeluxeNumberOfRooms());
        dto.setDeluxeMaxOccupancy(hotel.getDeluxeMaxOccupancy());
        dto.setDeluxePricePerNight(hotel.getDeluxePricePerNight());
        dto.setDeluxeAcOrNonAc(hotel.getDeluxeAcOrNonAc());

        dto.setExtraBedFacility(hotel.getExtraBedFacility());

        dto.setMinPricePerDay(hotel.getMinPricePerDay());
        dto.setMaxPricePerDay(hotel.getMaxPricePerDay());

        // Amenities
        dto.setRoomAmenities(hotel.getRoomAmenities());
        dto.setHotelFacilities(hotel.getHotelFacilities());
        dto.setFoodBeverage(hotel.getFoodBeverage());
        dto.setGuestService(hotel.getGuestService());

        // Checkin
        dto.setStandardCheckInTime(hotel.getStandardCheckInTime());
        dto.setStandardCheckOutTime(hotel.getStandardCheckOutTime());
        dto.setIdProofRequired(hotel.getIdProofRequired());
        dto.setPetsAllowed(hotel.getPetsAllowed());

        // Legal
        dto.setGstNumber(hotel.getGstNumber());
        dto.setFssaiLicenseNumber(hotel.getFssaiLicenseNumber());
        dto.setTradeLicenseNumber(hotel.getTradeLicenseNumber());
        dto.setPanNumber(hotel.getPanNumber());

        // Bank
        dto.setAccountHolderName(hotel.getAccountHolderName());
        dto.setBankName(hotel.getBankName());
        dto.setAccountNumber(hotel.getAccountNumber());
        dto.setIfscCode(hotel.getIfscCode());
        dto.setBranch(hotel.getBranch());
        dto.setAccountType(hotel.getAccountType());

        // Documents
        dto.setGstCertificate(hotel.getGstCertificate());
        dto.setTradeLicense(hotel.getTradeLicense());
        dto.setFssaiCertificate(hotel.getFssaiCertificate());
        dto.setCancelledCheque(hotel.getCancelledCheque());
        dto.setHotelRegistrationCertificate(hotel.getHotelRegistrationCertificate());
        dto.setRoomPropertyPhoto(hotel.getRoomPropertyPhoto());

        dto.setUploadedFiles(hotel.getUploadedFiles());


        return dto;
    }
}