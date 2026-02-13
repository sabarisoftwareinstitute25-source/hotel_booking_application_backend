package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.util.ErrorMessageSanitizer;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import com.hotelbooking.mobileapp.booking.Booking;
import com.hotelbooking.mobileapp.booking.BookingRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelVendorRepository HotelVendorRepository;

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired(required = false)
    private VendorRepository vendorRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    public Hotel createHotel(Hotel hotel) {
        if (hotel.getId() == null || hotel.getId().isEmpty()) {
            hotel.setId(idGeneratorService.generateHotelId());
        }
        return hotelRepository.save(hotel);
    }

    public List<Hotel> searchHotels(String city, String country, Integer starRating) {
        return hotelRepository.searchHotels(city, country, starRating);
    }

    /**
     * Enhanced search with dates and guest information.
     * Filters hotels by location, dates, and ensures availability for requested rooms/guests.
     */
    public List<Hotel> searchHotelsWithDates(HotelSearchRequest request) {
        // Extract location filters
        String city = request.getCity();
        String country = request.getCountry();
        
        // If location is provided but city/country not, try to extract from location
        if (request.getLocation() != null && !request.getLocation().isEmpty()) {
            String location = request.getLocation().trim();
            // Try to match location to city or country
            // For now, use location as city if city not specified
            if (city == null || city.isEmpty()) {
                city = location;
            }
        }
        
        // Search hotels by location filters
        List<Hotel> hotels = hotelRepository.searchHotels(
            city != null && !city.isEmpty() ? city : null,
            country != null && !country.isEmpty() ? country : null,
            request.getStarRating()
        );
        
        // Note: In a production system, you would also filter by:
        // - Room availability for check-in/check-out dates
        // - Room capacity (rooms, adults, children)
        // For now, we return hotels matching location/rating filters
        
        return hotels;
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> findById(String id) {
        return hotelRepository.findById(id);
    }

    /**
     * Register a new property (hotel or villa).
     * Creates a hotel entity and stores owner information.
     * 
     * @param hotel The hotel entity to register
     * @param request The registration request containing owner details
     * @return The registered hotel entity
     */
    public Hotel registerProperty(Hotel hotel, PropertyRegistrationRequest request) {
        // Validate property type
        String propertyType = request.getPropertyType();
        if (propertyType == null || propertyType.trim().isEmpty()) {
            throw new IllegalArgumentException("Property type is required");
        }

        // Generate hotel ID if not provided
        if (hotel.getId() == null || hotel.getId().isEmpty()) {
            hotel.setId(idGeneratorService.generateHotelId());
        }

        // Check if hotel with same name and location already exists
        List<Hotel> existingHotels = hotelRepository.findByCity(hotel.getCity());
        for (Hotel existing : existingHotels) {
            if (existing.getName().equalsIgnoreCase(hotel.getName()) &&
                existing.getAddress().equalsIgnoreCase(hotel.getAddress())) {
                throw new IllegalArgumentException(
                    "A property with the same name and address already exists in " + hotel.getCity()
                );
            }
        }

        // Save the hotel
        Hotel savedHotel = hotelRepository.save(hotel);

        // Note: In a production system, you would also:
        // - Create an owner account or link to existing owner account
        // - Store owner contact information (name, email, phone) in a separate table
        // - Send confirmation email to owner
        // - Set up initial room inventory
        // - Create property verification workflow

        return savedHotel;
    }

    /**
     * Register a hotel vendor with comprehensive details from 5-step form.
     * Creates a hotel entity and stores all vendor registration information.
     * Uses REQUIRES_NEW propagation to ensure transaction commits independently.
     * 
     * @param request The comprehensive vendor registration request
     * @return Response with registration details
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public HotelVendorRegistrationResponse registerVendor(HotelVendorRegistrationRequest request) {
        // Validate required fields
        if (request.getHotelName() == null || request.getHotelName().trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name is required");
        }
        if (request.getOwnerName() == null || request.getOwnerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Owner/Manager name is required");
        }
        if (request.getMobileNumber() == null || request.getMobileNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Mobile number is required");
        }
        if (request.getAddressLine1() == null || request.getAddressLine1().trim().isEmpty()) {
            throw new IllegalArgumentException("Address line 1 is required");
        }
        if (request.getCity() == null || request.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        if (request.getState() == null || request.getState().trim().isEmpty()) {
            throw new IllegalArgumentException("State is required");
        }
        if (request.getPinCode() == null || request.getPinCode().trim().isEmpty()) {
            throw new IllegalArgumentException("PIN code is required");
        }
        if (request.getAccountHolderName() == null || request.getAccountHolderName().trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name is required");
        }
        if (request.getBankName() == null || request.getBankName().trim().isEmpty()) {
            throw new IllegalArgumentException("Bank name is required");
        }
        if (request.getAccountNumber() == null || request.getAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
        // Validate account number format: 9-18 alphanumeric characters
        String accountNumber = request.getAccountNumber().trim();
        if (accountNumber.length() < 9 || accountNumber.length() > 18) {
            throw new IllegalArgumentException("Account number must be between 9 and 18 characters");
        }
        if (!accountNumber.matches("^[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("Account number must contain only letters (A-Z, a-z) and numbers (0-9)");
        }
        if (request.getIfscCode() == null || request.getIfscCode().trim().isEmpty()) {
            throw new IllegalArgumentException("IFSC code is required");
        }
        // Validate IFSC format: AAAA0XXXXXX (4 letters, 1 zero, 6 alphanumeric)
        String ifsc = request.getIfscCode().trim().toUpperCase();
        if (ifsc.length() != 11 || !ifsc.matches("^[A-Z]{4}0[A-Z0-9]{6}$")) {
            throw new IllegalArgumentException("IFSC code must be exactly 11 characters in format: AAAA0XXXXXX (4 letters, 1 zero, 6 alphanumeric)");
        }
        
        // Validate Aadhar number (optional, only validate 12 digits if provided)
        String aadhar = null;
        if (request.getAadharNumber() != null && !request.getAadharNumber().trim().isEmpty()) {
            aadhar = request.getAadharNumber().trim().replaceAll("[\\s-]", "");
            if (aadhar.length() != 12 || !aadhar.matches("^\\d{12}$")) {
                throw new IllegalArgumentException("Aadhar number must be exactly 12 digits");
            }
        }
        
        // Validate GSTIN (required, but no format validation)
        if (request.getGstNumber() == null || request.getGstNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("GST Number is required");
        }
        // Normalize GSTIN (remove spaces, convert to uppercase) before saving
        String gstin = request.getGstNumber().trim().replaceAll("\\s", "").toUpperCase();
        request.setGstNumber(gstin);
        
        // FSSAI license is optional - no validation needed
        if (request.getFssaiLicense() != null && !request.getFssaiLicense().trim().isEmpty()) {
            // Normalize FSSAI (remove spaces) before saving
            request.setFssaiLicense(request.getFssaiLicense().trim().replaceAll("[\\s-]", ""));
        }
        
        if (request.getDeclarationAccepted() == null || !request.getDeclarationAccepted()) {
            throw new IllegalArgumentException("Declaration must be accepted");
        }

        // Check for duplicate hotel (same name and address)
        List<Hotel> existingHotels = hotelRepository.findByCity(request.getCity());
        String fullAddress = request.getAddressLine1() + 
                           (request.getAddressLine2() != null && !request.getAddressLine2().isEmpty() 
                            ? ", " + request.getAddressLine2() : "");
        
        for (Hotel existing : existingHotels) {
            if (existing.getName().equalsIgnoreCase(request.getHotelName().trim()) &&
                existing.getAddress().equalsIgnoreCase(fullAddress.trim())) {
                throw new IllegalArgumentException(
                    "A hotel with the same name and address already exists in " + request.getCity()
                );
            }
        }

        // Create hotel entity from vendor registration
        Hotel hotel = new Hotel();
        hotel.setId(idGeneratorService.generateHotelId());
        hotel.setName(request.getHotelName().trim());
        hotel.setAddress(fullAddress.trim());
        hotel.setCity(request.getCity().trim());
        hotel.setCountry("India"); // Default to India, can be enhanced later
        // Determine star rating from hotel type or use default
        Integer starRating = determineStarRating(request.getHotelType());
        hotel.setStarRating(starRating);

        // Save the hotel
        Hotel savedHotel = hotelRepository.save(hotel);

        // Get property type from request (default to "Hotel" if not provided for backward compatibility)
        String propertyType = request.getPropertyType() != null && !request.getPropertyType().trim().isEmpty() 
            ? request.getPropertyType().trim() 
            : "Hotel";
        
        // Find existing vendor from vendors table by email or phone
        String vendorId = null;
        String registrationId = null;
        
        if (vendorRepository != null) {
            String email = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : null;
            String mobileNumber = request.getMobileNumber() != null ? request.getMobileNumber().trim() : null;
            
            Optional<Vendor> existingVendor = Optional.empty();
            if (email != null && !email.isEmpty()) {
                existingVendor = vendorRepository.findByEmail(email);
            }
            if (existingVendor.isEmpty() && mobileNumber != null && !mobileNumber.isEmpty()) {
                existingVendor = vendorRepository.findByPhone(mobileNumber);
            }
            
            if (existingVendor.isPresent()) {
                vendorId = existingVendor.get().getVendorId();
                // Use vendor ID as registration ID
                registrationId = vendorId;
                System.out.println("✅ Found existing vendor: " + vendorId);
                System.out.println("✅ Using vendor ID as registration ID: " + registrationId);
            } else {
                System.out.println("⚠️ No existing vendor found for email: " + email + " or phone: " + mobileNumber);
                System.out.println("⚠️ Generating new registration ID...");
            }
        }
        
        // Generate registration ID only if vendor not found
        if (registrationId == null) {
            // Generate registration ID based on property type: HVyyyyMM0001 (Hotel), VVyyyyMM0001 (Villa), AVyyyyMM0001 (Apartment), RVyyyyMM0001 (Resort)
            registrationId = idGeneratorService.generateHotelVendorRegistrationId(propertyType);
            System.out.println("✅ Generated new registration ID: " + registrationId);
        }

        // Create and save HotelVendor entity with all registration data
        HotelVendor vendor = new HotelVendor();
        vendor.setRegistrationId(registrationId);
        vendor.setVendorId(vendorId); // Link to vendors table (null if no vendor account exists)
        vendor.setHotelId(savedHotel.getId());
        vendor.setPropertyType(propertyType);
        
        // Step 1: Property Information
        vendor.setHotelName(request.getHotelName().trim());
        vendor.setHotelType(request.getHotelType());
        vendor.setYearOfEstablishment(request.getYearOfEstablishment());
        vendor.setTotalRooms(request.getTotalRooms());
        
        // Step 1: Contact Information
        vendor.setOwnerName(request.getOwnerName().trim());
        vendor.setMobileNumber(request.getMobileNumber().trim());
        vendor.setAlternateContact(request.getAlternateContact() != null ? request.getAlternateContact().trim() : null);
        vendor.setLandlineNumbers(request.getLandlineNumbers() != null && !request.getLandlineNumbers().isEmpty() 
            ? request.getLandlineNumbers() : new ArrayList<>());
        vendor.setEmail(request.getEmail() != null ? request.getEmail().trim() : null);
        vendor.setWebsite(request.getWebsite() != null ? request.getWebsite().trim() : null);
        vendor.setPersonPhotoInfo(request.getPersonPhotoInfo() != null && !request.getPersonPhotoInfo().isEmpty() 
            ? request.getPersonPhotoInfo() : new HashMap<>());
        
        // Step 2: Hotel Address
        vendor.setAddressLine1(request.getAddressLine1().trim());
        vendor.setAddressLine2(request.getAddressLine2());
        vendor.setCity(request.getCity().trim());
        vendor.setDistrict(request.getDistrict() != null ? request.getDistrict().trim() : null);
        vendor.setState(request.getState().trim());
        vendor.setPinCode(request.getPinCode().trim());
        vendor.setLandmark(request.getLandmark());
        
        // Step 3: Room Availability
        vendor.setSelectedRoomTypes(request.getSelectedRoomTypes() != null && !request.getSelectedRoomTypes().isEmpty() 
            ? request.getSelectedRoomTypes() : new HashMap<>());
        vendor.setRoomDetails(request.getRoomDetails() != null && !request.getRoomDetails().isEmpty() 
            ? request.getRoomDetails() : new HashMap<>());
        vendor.setMinTariff(request.getMinTariff() != null ? request.getMinTariff().trim() : null);
        vendor.setMaxTariff(request.getMaxTariff() != null ? request.getMaxTariff().trim() : null);
        vendor.setExtraBedAvailable(request.getExtraBedAvailable() != null ? request.getExtraBedAvailable() : false);
        
        // Step 4: Amenities & Legal
        vendor.setBasicAmenities(request.getBasicAmenities() != null && !request.getBasicAmenities().isEmpty() 
            ? request.getBasicAmenities() : new HashMap<>());
        vendor.setHotelFacilities(request.getHotelFacilities() != null && !request.getHotelFacilities().isEmpty() 
            ? request.getHotelFacilities() : new HashMap<>());
        vendor.setFoodServices(request.getFoodServices() != null && !request.getFoodServices().isEmpty() 
            ? request.getFoodServices() : new HashMap<>());
        vendor.setAdditionalAmenities(request.getAdditionalAmenities() != null && !request.getAdditionalAmenities().isEmpty() 
            ? request.getAdditionalAmenities() : new HashMap<>());
        vendor.setCustomAmenities(request.getCustomAmenities() != null && !request.getCustomAmenities().isEmpty() 
            ? request.getCustomAmenities() : new ArrayList<>());
        // GSTIN is already validated and normalized above
        vendor.setGstNumber(request.getGstNumber() != null ? request.getGstNumber().trim().replaceAll("\\s", "").toUpperCase() : null);
        // FSSAI is already validated and normalized above
        vendor.setFssaiLicense(request.getFssaiLicense() != null ? request.getFssaiLicense().trim().replaceAll("[\\s-]", "") : null);
        vendor.setTradeLicense(request.getTradeLicense());
        vendor.setPanNumber(request.getPanNumber());
        // Normalize Aadhar number (remove spaces and hyphens)
        vendor.setAadharNumber(aadhar);
        
        // Step 5: Bank & Documents
        vendor.setAccountHolderName(request.getAccountHolderName().trim());
        vendor.setBankName(request.getBankName().trim());
        vendor.setAccountNumber(request.getAccountNumber().trim());
        vendor.setIfscCode(request.getIfscCode().trim());
        vendor.setBranch(request.getBranch() != null ? request.getBranch().trim() : null);
        vendor.setAccountType(
                request.getAccountType() != null
                        ? Boolean.valueOf(request.getAccountType().trim())
                        : null
        );
        vendor.setUploadedFiles(request.getUploadedFiles() != null && !request.getUploadedFiles().isEmpty() 
            ? request.getUploadedFiles() : new HashMap<>());
        vendor.setSignatureName(request.getSignatureName() != null ? request.getSignatureName().trim() : null);
        vendor.setDeclarationName(request.getDeclarationName() != null ? request.getDeclarationName().trim() : null);
        
        // Parse declaration date if provided
        if (request.getDeclarationDate() != null && !request.getDeclarationDate().isEmpty()) {
            try {
                vendor.setDeclarationDate(Instant.parse(request.getDeclarationDate()));
            } catch (Exception e) {
                // If parsing fails, use current time
                vendor.setDeclarationDate(Instant.now());
            }
        } else {
            vendor.setDeclarationDate(Instant.now());
        }
        
        vendor.setDeclarationAccepted(request.getDeclarationAccepted());
        vendor.setRegistrationStatus("PENDING");
        vendor.setCreatedAt(Instant.now());
        vendor.setUpdatedAt(Instant.now());
        
        // Save vendor registration data to database
        try {
            // Validate entity before saving
            System.out.println("🔍 Pre-save validation...");
            if (vendor.getRegistrationId() == null || vendor.getRegistrationId().isEmpty()) {
                throw new IllegalStateException("Registration ID cannot be null or empty");
            }
            if (vendor.getHotelId() == null || vendor.getHotelId().isEmpty()) {
                throw new IllegalStateException("Hotel ID cannot be null or empty");
            }
            if (vendor.getHotelName() == null || vendor.getHotelName().trim().isEmpty()) {
                throw new IllegalStateException("Hotel name cannot be null or empty");
            }
            if (vendor.getOwnerName() == null || vendor.getOwnerName().trim().isEmpty()) {
                throw new IllegalStateException("Owner name cannot be null or empty");
            }
            if (vendor.getMobileNumber() == null || vendor.getMobileNumber().trim().isEmpty()) {
                throw new IllegalStateException("Mobile number cannot be null or empty");
            }
            
            System.out.println("✅ Validation passed");
            System.out.println("💾 Attempting to save HotelVendor:");
            System.out.println("   Registration ID: " + vendor.getRegistrationId());
            System.out.println("   Hotel ID: " + vendor.getHotelId());
            System.out.println("   Hotel Name: " + vendor.getHotelName());
            System.out.println("   Owner Name: " + vendor.getOwnerName());
            System.out.println("   Mobile: " + vendor.getMobileNumber());
            System.out.println("   City: " + vendor.getCity());
            System.out.println("   State: " + vendor.getState());
            System.out.println("   Declaration Accepted: " + vendor.getDeclarationAccepted());
            System.out.println("   Created At: " + vendor.getCreatedAt());
            System.out.println("   Updated At: " + vendor.getUpdatedAt());
            
            // Ensure all collections are initialized (double-check before save)
            if (vendor.getLandlineNumbers() == null) {
                vendor.setLandlineNumbers(new ArrayList<>());
            }
            if (vendor.getCustomAmenities() == null) {
                vendor.setCustomAmenities(new ArrayList<>());
            }
            if (vendor.getSelectedRoomTypes() == null) {
                vendor.setSelectedRoomTypes(new HashMap<>());
            }
            if (vendor.getRoomDetails() == null) {
                vendor.setRoomDetails(new HashMap<>());
            }
            if (vendor.getBasicAmenities() == null) {
                vendor.setBasicAmenities(new HashMap<>());
            }
            if (vendor.getHotelFacilities() == null) {
                vendor.setHotelFacilities(new HashMap<>());
            }
            if (vendor.getFoodServices() == null) {
                vendor.setFoodServices(new HashMap<>());
            }
            if (vendor.getAdditionalAmenities() == null) {
                vendor.setAdditionalAmenities(new HashMap<>());
            }
            if (vendor.getUploadedFiles() == null) {
                vendor.setUploadedFiles(new HashMap<>());
            }
            if (vendor.getPersonPhotoInfo() == null) {
                vendor.setPersonPhotoInfo(new HashMap<>());
            }
            
            System.out.println("💾 Calling saveAndFlush()...");
            HotelVendor savedVendor = HotelVendorRepository.saveAndFlush(vendor);
            System.out.println("✅ saveAndFlush() completed - no exceptions thrown");
            System.out.println("   Saved Registration ID: " + savedVendor.getRegistrationId());
            
            // Force a commit by doing a count query
            long countBefore = HotelVendorRepository.count();
            System.out.println("   Total vendors in DB before: " + countBefore);
            
            // Verify it was saved by querying immediately
            System.out.println("🔍 Verifying data in database...");
            Optional<HotelVendor> verifyVendor = HotelVendorRepository.findById(savedVendor.getRegistrationId());
            if (verifyVendor.isPresent()) {
                HotelVendor found = verifyVendor.get();
                System.out.println("✅ HotelVendor VERIFIED in database!");
                System.out.println("   Registration ID: " + found.getRegistrationId());
                System.out.println("   Hotel ID: " + found.getHotelId());
                System.out.println("   Hotel Name: " + found.getHotelName());
                System.out.println("   Owner Name: " + found.getOwnerName());
                System.out.println("   Mobile: " + found.getMobileNumber());
                System.out.println("   City: " + found.getCity());
                System.out.println("   State: " + found.getState());
                System.out.println("   Created At: " + found.getCreatedAt());
                System.out.println("   Registration Status: " + found.getRegistrationStatus());
                
                long countAfter = HotelVendorRepository.count();
                System.out.println("   Total vendors in DB after: " + countAfter);
            } else {
                System.err.println("❌ CRITICAL: HotelVendor was NOT found in database after saveAndFlush!");
                System.err.println("   Expected Registration ID: " + savedVendor.getRegistrationId());
                
                // Try to find by hotel ID as fallback
                Optional<HotelVendor> byHotelId = HotelVendorRepository.findByHotelId(savedVendor.getHotelId());
                if (byHotelId.isPresent()) {
                    System.err.println("   ⚠️ Found by Hotel ID instead - possible ID mismatch");
                } else {
                    System.err.println("   ❌ Not found by Hotel ID either");
                }
                
                throw new RuntimeException("HotelVendor was not persisted to database");
            }
        } catch (ConstraintViolationException e) {
            System.err.println("❌ VALIDATION ERROR saving HotelVendor:");
            e.getConstraintViolations().forEach(violation -> {
                System.err.println("   Field: " + violation.getPropertyPath());
                System.err.println("   Message: " + violation.getMessage());
                System.err.println("   Invalid Value: " + violation.getInvalidValue());
            });
            System.err.println("   Full exception: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Validation failed: " + e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            System.err.println("❌ DATABASE CONSTRAINT ERROR saving HotelVendor:");
            System.err.println("   Error: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("   Cause: " + e.getCause().getMessage());
                if (e.getCause().getCause() != null) {
                    System.err.println("   Root Cause: " + e.getCause().getCause().getMessage());
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Database constraint violation: " + e.getMessage(), e);
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            System.err.println("❌ HIBERNATE CONSTRAINT ERROR saving HotelVendor:");
            System.err.println("   Error: " + e.getMessage());
            System.err.println("   SQL State: " + e.getSQLState());
            System.err.println("   Constraint Name: " + e.getConstraintName());
            if (e.getCause() != null) {
                System.err.println("   Cause: " + e.getCause().getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException("Database constraint violation: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("❌ UNEXPECTED ERROR saving HotelVendor to database:");
            System.err.println("   Error Type: " + e.getClass().getName());
            System.err.println("   Error Message: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("   Cause: " + e.getCause().getMessage());
                if (e.getCause().getCause() != null) {
                    System.err.println("   Root Cause: " + e.getCause().getCause().getMessage());
                }
            }
            System.err.println("   Stack Trace:");
            e.printStackTrace();
            // Throw user-friendly error message
            String userMessage = ErrorMessageSanitizer.getUserFriendlyMessage(e);
            throw new RuntimeException(userMessage, e);
        }

        // Note: In a production system, you would also:
        // - Handle file uploads (documents, photos) and store file paths/URLs in cloud storage
        // - Send confirmation email to vendor
        // - Create verification workflow
        // - Link vendor to user account if they have one
        // - Set up notification system for admin review

        String message = String.format(
            "Hotel vendor registration submitted successfully! Your hotel '%s' has been registered. " +
            "Registration ID: %s. Our team will review your application and contact you soon.",
            request.getHotelName(),
            registrationId
        );

        return new HotelVendorRegistrationResponse(true, message, registrationId, savedHotel);
    }

    /**
     * Determine star rating based on hotel type.
     * This is a simple mapping - can be enhanced based on business logic.
     */
    private Integer determineStarRating(String hotelType) {
        if (hotelType == null || hotelType.trim().isEmpty()) {
            return 1; // Default
        }
        String type = hotelType.toLowerCase();
        if (type.contains("budget") || type.contains("lodge") || type.contains("guest house")) {
            return 1;
        } else if (type.contains("standard")) {
            return 2;
        } else if (type.contains("heritage") || type.contains("boutique")) {
            return 3;
        } else {
            return 2; // Default for unknown types
        }
    }

    /**
     * Get vendor profile by email or mobile number.
     */
    public VendorProfileResponse getVendorProfile(String email, String mobileNumber) {
        System.out.println("🔍 GETTING VENDOR PROFILE:");
        System.out.println("   Email: " + email);
        System.out.println("   Mobile: " + mobileNumber);
        
        Optional<HotelVendor> vendorOpt = Optional.empty();
        
        if (email != null && !email.trim().isEmpty()) {
            System.out.println("   Searching by email: " + email.trim().toLowerCase());
            vendorOpt = HotelVendorRepository.findByEmail(email.trim().toLowerCase());
            System.out.println("   Found by email: " + vendorOpt.isPresent());
        } else if (mobileNumber != null && !mobileNumber.trim().isEmpty()) {
            System.out.println("   Searching by mobile: " + mobileNumber.trim());
            vendorOpt = HotelVendorRepository.findByMobileNumber(mobileNumber.trim());
            System.out.println("   Found by mobile: " + vendorOpt.isPresent());
        } else {
            System.out.println("   ❌ Both email and mobile are null/empty");
            return new VendorProfileResponse(false, "Email or mobile number is required");
        }
        
        if (vendorOpt.isEmpty()) {
            System.out.println("   ❌ Vendor not found in database");
            return new VendorProfileResponse(false, "Vendor not found");
        }
        
        System.out.println("   ✅ Vendor found: " + vendorOpt.get().getRegistrationId());
        
        HotelVendor vendor = vendorOpt.get();
        VendorProfileResponse response = new VendorProfileResponse(true, "Profile retrieved successfully");
        response.setRegistrationId(vendor.getRegistrationId());
        response.setHotelId(vendor.getHotelId());
        response.setOwnerName(vendor.getOwnerName());
        response.setHotelName(vendor.getHotelName());
        response.setMobileNumber(vendor.getMobileNumber());
        response.setEmail(vendor.getEmail());
        
        // Set property type and additional property details
        response.setPropertyType(vendor.getPropertyType() != null ? vendor.getPropertyType() : "Hotel");
        response.setHotelType(vendor.getHotelType() != null ? vendor.getHotelType() : "");
        response.setYearOfEstablishment(vendor.getYearOfEstablishment() != null ? vendor.getYearOfEstablishment() : "");
        response.setWebsite(vendor.getWebsite() != null ? vendor.getWebsite() : "");
        response.setLandmark(vendor.getLandmark() != null ? vendor.getLandmark() : "");
        
        // Extract profile image URL from personPhotoInfo if available
        if (vendor.getPersonPhotoInfo() != null && vendor.getPersonPhotoInfo().containsKey("url")) {
            response.setProfileImageUrl(vendor.getPersonPhotoInfo().get("url").toString());
        }
        
        // Set full personPhotoInfo (includes base64 data if available)
        response.setPersonPhotoInfo(vendor.getPersonPhotoInfo() != null ? vendor.getPersonPhotoInfo() : new java.util.HashMap<>());
        
        // Set uploadedFiles (includes base64 data for all documents)
        response.setUploadedFiles(vendor.getUploadedFiles() != null ? vendor.getUploadedFiles() : new java.util.HashMap<>());
        
        // Set additional contact information
        response.setTotalRooms(vendor.getTotalRooms() != null ? vendor.getTotalRooms() : "");
        response.setAlternateContact(vendor.getAlternateContact() != null ? vendor.getAlternateContact() : "");
        response.setLandlineNumbers(vendor.getLandlineNumbers() != null ? vendor.getLandlineNumbers() : new java.util.ArrayList<>());
        
        // Set address fields
        response.setAddressLine1(vendor.getAddressLine1() != null ? vendor.getAddressLine1() : "");
        response.setAddressLine2(vendor.getAddressLine2() != null ? vendor.getAddressLine2() : "");
        response.setCity(vendor.getCity() != null ? vendor.getCity() : "");
        response.setDistrict(vendor.getDistrict() != null ? vendor.getDistrict() : "");
        response.setState(vendor.getState() != null ? vendor.getState() : "");
        response.setPinCode(vendor.getPinCode() != null ? vendor.getPinCode() : "");
        
        // Set legal documents
        response.setGstNumber(vendor.getGstNumber() != null ? vendor.getGstNumber() : "");
        response.setFssaiLicense(vendor.getFssaiLicense() != null ? vendor.getFssaiLicense() : "");
        response.setTradeLicense(vendor.getTradeLicense() != null ? vendor.getTradeLicense() : "");
        response.setPanNumber(vendor.getPanNumber() != null ? vendor.getPanNumber() : "");
        response.setAadharNumber(vendor.getAadharNumber() != null ? vendor.getAadharNumber() : "");
        
        // Set room details
        response.setSelectedRoomTypes(vendor.getSelectedRoomTypes() != null ? vendor.getSelectedRoomTypes() : new java.util.HashMap<>());
        response.setRoomDetails(vendor.getRoomDetails() != null ? vendor.getRoomDetails() : new java.util.HashMap<>());
        response.setMinTariff(vendor.getMinTariff() != null ? vendor.getMinTariff() : "");
        response.setMaxTariff(vendor.getMaxTariff() != null ? vendor.getMaxTariff() : "");
        response.setExtraBedAvailable(vendor.getExtraBedAvailable() != null ? vendor.getExtraBedAvailable() : false);
        
        // Set amenities
        response.setBasicAmenities(vendor.getBasicAmenities() != null ? vendor.getBasicAmenities() : new java.util.HashMap<>());
        response.setHotelFacilities(vendor.getHotelFacilities() != null ? vendor.getHotelFacilities() : new java.util.HashMap<>());
        response.setFoodServices(vendor.getFoodServices() != null ? vendor.getFoodServices() : new java.util.HashMap<>());
        response.setAdditionalAmenities(vendor.getAdditionalAmenities() != null ? vendor.getAdditionalAmenities() : new java.util.HashMap<>());
        response.setCustomAmenities(vendor.getCustomAmenities() != null ? vendor.getCustomAmenities() : new java.util.ArrayList<>());
        
        // Set bank details
        response.setAccountHolderName(vendor.getAccountHolderName() != null ? vendor.getAccountHolderName() : "");
        response.setBankName(vendor.getBankName() != null ? vendor.getBankName() : "");
        response.setAccountNumber(vendor.getAccountNumber() != null ? vendor.getAccountNumber() : "");
        response.setIfscCode(vendor.getIfscCode() != null ? vendor.getIfscCode() : "");
        response.setBranch(vendor.getBranch() != null ? vendor.getBranch() : "");
        response.setAccountType(
                vendor.getAccountType() != null
                        ? vendor.getAccountType().toString()
                        : ""
        );

        // Set declaration
        response.setSignatureName(vendor.getSignatureName() != null ? vendor.getSignatureName() : "");
        response.setDeclarationName(vendor.getDeclarationName() != null ? vendor.getDeclarationName() : "");
        if (vendor.getDeclarationDate() != null) {
            // Convert Instant to ISO-8601 string format
            response.setDeclarationDate(vendor.getDeclarationDate().toString());
        } else {
            response.setDeclarationDate("");
        }
        response.setDeclarationAccepted(vendor.getDeclarationAccepted() != null ? vendor.getDeclarationAccepted() : false);
        
        // Calculate and set statistics
        VendorProfileResponse.HotelStatistics statistics = calculateHotelStatistics(vendor.getHotelId());
        response.setStatistics(statistics);
        
        return response;
    }

    /**
     * Calculate hotel statistics: total rooms, active bookings, occupancy, rating.
     */
    private VendorProfileResponse.HotelStatistics calculateHotelStatistics(String hotelId) {
        // Get vendor to access totalRooms from registration
        Optional<HotelVendor> vendorOpt = HotelVendorRepository.findByHotelId(hotelId);
        int totalRooms = 0;
        
        if (vendorOpt.isPresent()) {
            HotelVendor vendor = vendorOpt.get();
            // Get total rooms from vendor registration (stored as string)
            String totalRoomsStr = vendor.getTotalRooms();
            if (totalRoomsStr != null && !totalRoomsStr.trim().isEmpty()) {
                try {
                    totalRooms = Integer.parseInt(totalRoomsStr.trim());
                } catch (NumberFormatException e) {
                    // If parsing fails, try to get from rooms repository
                    List<Room> rooms = roomRepository.findByHotelId(hotelId);
                    totalRooms = rooms.size();
                }
            } else {
                // Fallback to counting rooms from repository
                List<Room> rooms = roomRepository.findByHotelId(hotelId);
                totalRooms = rooms.size();
            }
        } else {
            // Fallback to counting rooms from repository
            List<Room> rooms = roomRepository.findByHotelId(hotelId);
            totalRooms = rooms.size();
        }
        
        // Get active bookings (check-in date <= today <= check-out date)
        LocalDate today = LocalDate.now();
        List<Booking> allBookings = bookingRepository.findAll();
        int activeNow = (int) allBookings.stream()
            .filter(booking -> booking.getHotel().getId().equals(hotelId))
            .filter(booking -> booking.getStatus().equalsIgnoreCase("CONFIRMED") || 
                             booking.getStatus().equalsIgnoreCase("ACTIVE"))
            .filter(booking -> !booking.getCheckInDate().isAfter(today) && 
                             !booking.getCheckOutDate().isBefore(today))
            .count();
        
        // Calculate occupancy percentage
        double occupancy = 0.0;
        if (totalRooms > 0) {
            occupancy = (activeNow * 100.0) / totalRooms;
            // Round to 1 decimal place
            occupancy = Math.round(occupancy * 10.0) / 10.0;
            // Cap at 100%
            if (occupancy > 100.0) {
                occupancy = 100.0;
            }
        }
        
        // Ensure activeNow doesn't exceed totalRooms
        if (activeNow > totalRooms && totalRooms > 0) {
            activeNow = totalRooms;
            occupancy = 100.0;
        }
        
        // Get hotel rating (use star rating as default, can be enhanced with reviews later)
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
        double rating = 3.0; // Default rating
        if (hotelOpt.isPresent()) {
            Integer starRating = hotelOpt.get().getStarRating();
            if (starRating != null) {
                // Convert star rating (1-5) to a rating scale (3.0-5.0)
                rating = 3.0 + (starRating - 1) * 0.5;
                // Add some variation for realism (can be replaced with actual review ratings)
                rating = Math.round(rating * 10.0) / 10.0;
            }
        }
        
        return new VendorProfileResponse.HotelStatistics(totalRooms, activeNow, occupancy, rating);
    }

    /**
     * Generates a vendor registration ID based on property type.
     * Format: [PREFIX]yyyymm0001
     * Prefixes: HV (Hotel), VV (Villa), AV (Apartment), RV (Resort)
     * Example: HV2026010001, VV2026010001, AV2026010001, RV2026010001
     */
    private String generateVendorRegistrationId(String propertyType) {
        // Determine prefix based on property type
        String typePrefix;
        switch (propertyType != null ? propertyType.toUpperCase() : "HOTEL") {
            case "HOTEL":
                typePrefix = "HV";
                break;
            case "VILLA":
                typePrefix = "VV";
                break;
            case "APARTMENT":
                typePrefix = "AV";
                break;
            case "RESORT":
                typePrefix = "RV";
                break;
            default:
                typePrefix = "HV"; // Default to Hotel for backward compatibility
                break;
        }
        
        // Get current year and month
        YearMonth currentYearMonth = YearMonth.now();
        String yearMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String prefix = typePrefix + yearMonth;
        
        // Find the highest sequence number for this property type and month
        int maxSequence = 0;
        List<HotelVendor> allVendors = HotelVendorRepository.findAll();
        
        // Pattern: [PREFIX] + 4 digits (year) + 2 digits (month) + 4 digits (sequence)
        Pattern pattern = Pattern.compile("^" + typePrefix + "(\\d{4})(\\d{2})(\\d{4})$");
        
        for (HotelVendor vendor : allVendors) {
            String regId = vendor.getRegistrationId();
            if (regId != null && regId.startsWith(typePrefix)) {
                Matcher matcher = pattern.matcher(regId);
                if (matcher.matches()) {
                    try {
                        String year = matcher.group(1);
                        String month = matcher.group(2);
                        String currentYear = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy"));
                        String currentMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("MM"));
                        
                        // Only process if it's the same property type, year and month
                        if (year.equals(currentYear) && month.equals(currentMonth)) {
                            int sequence = Integer.parseInt(matcher.group(3));
                            if (sequence > maxSequence) {
                                maxSequence = sequence;
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid format
                    }
                }
            }
        }
        
        // Increment sequence number
        int nextSequence = maxSequence + 1;
        
        // Format as 4-digit number (0001, 0002, etc.)
        String sequenceStr = String.format("%04d", nextSequence);
        
        return prefix + sequenceStr;
    }
}

