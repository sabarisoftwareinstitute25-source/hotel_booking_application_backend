package com.hotelbooking.mobileapp.hotel;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ThreeStarHotelServiceImpl implements ThreeStarHotelService {

    private final ThreeStarHotelRepository repository;

    public ThreeStarHotelServiceImpl(ThreeStarHotelRepository repository) {
        this.repository = repository;
    }

    @Override
    public ThreeStarHotel save(ThreeStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public ThreeStarHotel update(String registrationId, ThreeStarHotel hotel) {

        ThreeStarHotel existing = repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Basic Info
        existing.setHotelName(hotel.getHotelName());
        existing.setHotelType(hotel.getHotelType());
        existing.setPropertyType(hotel.getPropertyType());
        existing.setYearOfEstablishment(hotel.getYearOfEstablishment());
        existing.setTotalRooms(hotel.getTotalRooms());

        // Contact
        existing.setOwnerName(hotel.getOwnerName());
        existing.setDesignation(hotel.getDesignation());
        existing.setMobileNumber(hotel.getMobileNumber());
        existing.setAlternateContact(hotel.getAlternateContact());
        existing.setEmail(hotel.getEmail());
        existing.setWebsite(hotel.getWebsite());

        // Address
        existing.setAddressLine1(hotel.getAddressLine1());
        existing.setAddressLine2(hotel.getAddressLine2());
        existing.setCity(hotel.getCity());
        existing.setDistrict(hotel.getDistrict());
        existing.setState(hotel.getState());
        existing.setPinCode(hotel.getPinCode());

        // Room Config
        existing.setSelectedRoomTypes(hotel.getSelectedRoomTypes());
        existing.setExtraBedAvailable(hotel.getExtraBedAvailable());
        existing.setSeasonalpricing(hotel.getSeasonalpricing());
        existing.setRoomDetails(hotel.getRoomDetails());

        // Amenities
        existing.setRoomAmenities(hotel.getRoomAmenities());
        existing.setHotelFacilities(hotel.getHotelFacilities());
        existing.setFoodBeverage(hotel.getFoodBeverage());
        existing.setGuestService(hotel.getGuestService());

        // Policies
        existing.setStandardCheckInTime(hotel.getStandardCheckInTime());
        existing.setStandardCheckOutTime(hotel.getStandardCheckOutTime());
        existing.setEarlycheckinlatecheckout(hotel.getEarlycheckinlatecheckout());
        existing.setPetsAllowed(hotel.getPetsAllowed());

        // Legal
        existing.setGstNumber(hotel.getGstNumber());
        existing.setFssaiLicense(hotel.getFssaiLicense());
        existing.setTradeLicense(hotel.getTradeLicense());
        existing.setPanNumber(hotel.getPanNumber());
        existing.setFiresafety(hotel.getFiresafety());

        // Bank
        existing.setAccountHolderName(hotel.getAccountHolderName());
        existing.setBankName(hotel.getBankName());
        existing.setAccountNumber(hotel.getAccountNumber());
        existing.setIfscCode(hotel.getIfscCode());
        existing.setBranch(hotel.getBranch());

        // Files
        existing.setUploadedFiles(hotel.getUploadedFiles());

        existing.setDeclarationAccepted(hotel.getDeclarationAccepted());
        existing.setDeclarationDate(hotel.getDeclarationDate());
        existing.setRegistrationStatus(hotel.getRegistrationStatus());
        existing.setSignedDate(hotel.getSignedDate());

        return repository.save(existing);
    }

    @Override
    public ThreeStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Override
    public List<ThreeStarHotel> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String registrationId) {
        repository.deleteById(registrationId);
    }

    @Override
    public List<ThreeStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }
}