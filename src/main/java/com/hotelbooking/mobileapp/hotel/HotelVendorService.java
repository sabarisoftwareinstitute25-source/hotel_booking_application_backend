package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.dto.NormalHotelProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelVendorService {

    private final HotelVendorRepository repository;

    // SAVE HOTEL
    public HotelVendor saveHotel(HotelVendor hotel) {
        return repository.save(hotel);
    }

    // GET ALL HOTELS
    public List<HotelVendor> getAllHotels() {
        return repository.findAll();
    }

    // GET HOTEL BY ID
    public HotelVendor getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    // DELETE HOTEL
    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }

    // GET FULL HOTEL PROFILE
    public NormalHotelProfileDTO getHotelProfile(String hotelId) {

        HotelVendor hotel = repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        NormalHotelProfileDTO dto = new NormalHotelProfileDTO();

        dto.setHotelId(hotel.getHotelId());
        dto.setHotelCategory(hotel.getHotelCategory());
        dto.setHotelName(hotel.getHotelName());
        dto.setHotelType(hotel.getHotelType());
        dto.setYearOfEstablishment(hotel.getYearOfEstablishment());
        dto.setTotalRooms(hotel.getTotalRooms());

        dto.setProfilePhoto(hotel.getProfilePhoto());
        dto.setOwnerName(hotel.getOwnerName());
        dto.setMobileNumber(hotel.getMobileNumber());
        dto.setAlternateContact(hotel.getAlternateContact());

        dto.setLandlineNumbers(hotel.getLandlineNumbers());
        dto.setEmail(hotel.getEmail());
        dto.setWebsite(hotel.getWebsite());

        dto.setAddressLine1(hotel.getAddressLine1());
        dto.setAddressLine2(hotel.getAddressLine2());
        dto.setCity(hotel.getCity());
        dto.setDistrict(hotel.getDistrict());
        dto.setState(hotel.getState());
        dto.setPinCode(hotel.getPinCode());
        dto.setLandmark(hotel.getLandmark());

        dto.setSelectedRoomTypes(hotel.getSelectedRoomTypes());

        dto.setMinTariff(hotel.getMinTariff());
        dto.setMaxTariff(hotel.getMaxTariff());
        dto.setExtraBedAvailable(hotel.getExtraBedAvailable());

        dto.setBasicAmenities(hotel.getBasicAmenities());
        dto.setHotelFacilities(hotel.getHotelFacilities());
        dto.setFoodServices(hotel.getFoodServices());
        dto.setAdditionalAmenities(hotel.getAdditionalAmenities());
        dto.setCustomAmenities(hotel.getCustomAmenities());

        dto.setGstNumber(hotel.getGstNumber());
        dto.setFssaiLicenseNumber(hotel.getFssaiLicenseNumber());
        dto.setTradeLicenseNumber(hotel.getTradeLicenseNumber());
        dto.setAadhaarNumber(hotel.getAadhaarNumber());

        dto.setAccountHolderName(hotel.getAccountHolderName());
        dto.setBankName(hotel.getBankName());
        dto.setAccountNumber(hotel.getAccountNumber());
        dto.setIfscCode(hotel.getIfscCode());
        dto.setBranch(hotel.getBranch());
        dto.setAccountType(hotel.getAccountType());

        dto.setFssaiCertificate(hotel.getFssaiCertificate());
        dto.setGstCertificate(hotel.getGstCertificate());
        dto.setTradeLicense(hotel.getTradeLicense());
        dto.setHotelPhoto(hotel.getHotelPhoto());
        dto.setCancelledCheque(hotel.getCancelledCheque());
        dto.setOwnerIdProof(hotel.getOwnerIdProof());

        dto.setUploadedFiles(hotel.getUploadedFiles());


        return dto;
    }
}