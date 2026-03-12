package com.hotelbooking.mobileapp.hotel;


import com.hotelbooking.mobileapp.dto.FourStarHotelProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FourStarHotelService {

    private final FourStarHotelRepository repository;

    public FourStarHotel saveHotel(FourStarHotel hotel) {
        return repository.save(hotel);
    }

    public List<FourStarHotel> getAllHotels() {
        return repository.findAll();
    }

    public FourStarHotel getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }

    // Get Full Hotel Profile
    public FourStarHotelProfileDTO getHotelProfile(String hotelId) {

        FourStarHotel hotel = repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        FourStarHotelProfileDTO dto = new FourStarHotelProfileDTO();

        dto.setHotelName(hotel.getHotelName());
        dto.setHotelCategory(hotel.getHotelCategory());
        dto.setHotelType(hotel.getHotelType());
        dto.setYearOfEstablishment(hotel.getYearOfEstablishment());
        dto.setTotalRooms(hotel.getTotalRooms());

        dto.setOwnerName(hotel.getOwnerName());
        dto.setDesignation(hotel.getDesignation());
        dto.setMobileNumber(hotel.getMobileNumber());
        dto.setAlternateContact(hotel.getAlternateContact());
        dto.setEmail(hotel.getEmail());
        dto.setWebsite(hotel.getWebsite());

        dto.setAddressLine1(hotel.getAddressLine1());
        dto.setAddressLine2(hotel.getAddressLine2());
        dto.setCity(hotel.getCity());
        dto.setDistrict(hotel.getDistrict());
        dto.setState(hotel.getState());
        dto.setCountry(hotel.getCountry());
        dto.setPinCode(hotel.getPinCode());

        dto.setSelectedRoomTypes(hotel.getSelectedRoomTypes());

        dto.setRoomAmenities(hotel.getRoomAmenities());
        dto.setHotelFacilities(hotel.getHotelFacilities());
        dto.setFoodBeverage(hotel.getFoodBeverage());
        dto.setGuestService(hotel.getGuestService());
        dto.setWellness(hotel.getWellness());

        dto.setStandardCheckInTime(hotel.getStandardCheckInTime());
        dto.setStandardCheckOutTime(hotel.getStandardCheckOutTime());

        dto.setPetsAllowed(hotel.getPetsAllowed());

        dto.setGstNumber(hotel.getGstNumber());
        dto.setFssaiLicenseNumber(hotel.getFssaiLicenseNumber());
        dto.setTradeLicenseNumber(hotel.getTradeLicenseNumber());
        dto.setPanNumber(hotel.getPanNumber());

        dto.setAccountHolderName(hotel.getAccountHolderName());
        dto.setBankName(hotel.getBankName());
        dto.setAccountNumber(hotel.getAccountNumber());
        dto.setIfscCode(hotel.getIfscCode());
        dto.setBranch(hotel.getBranch());
        dto.setAccountType(hotel.getAccountType());


        return dto;
    }


}