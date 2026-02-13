package com.hotelbooking.mobileapp.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Map;

/**
 * Hotel Controller matching frontend API endpoints.
 * Endpoints:
 * GET /api/hotels/search?city=&country=&starRating=
 * GET /api/hotels
 * GET /api/hotels/{id}
 * GET /api/hotels/rooms?hotelId=
 */
@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelVendorRepository hotelVendorRepository;

    /**
     * GET /api/hotels/search
     * Frontend expects: List<Hotel> (wrapped in ApiResponse by frontend)
     * Supports both old format (query params) and new format (request body with dates/guests)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer starRating,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String checkIn,
            @RequestParam(required = false) String checkOut,
            @RequestParam(required = false) Integer rooms,
            @RequestParam(required = false) Integer adults,
            @RequestParam(required = false) Integer children) {
        try {
            // If new parameters provided, use enhanced search
            if (location != null || checkIn != null || checkOut != null || 
                rooms != null || adults != null || children != null) {
                HotelSearchRequest request = new HotelSearchRequest();
                request.setLocation(location);
                request.setCity(city);
                request.setCountry(country);
                request.setStarRating(starRating);
                if (checkIn != null) {
                    request.setCheckIn(java.time.LocalDate.parse(checkIn));
                }
                if (checkOut != null) {
                    request.setCheckOut(java.time.LocalDate.parse(checkOut));
                }
                request.setRooms(rooms != null ? rooms : 1);
                request.setAdults(adults != null ? adults : 1);
                request.setChildren(children != null ? children : 0);
                
                List<Hotel> hotels = hotelService.searchHotelsWithDates(request);
                return ResponseEntity.ok(hotels);
            } else {
                // Use old search format
                List<Hotel> hotels = hotelService.searchHotels(city, country, starRating);
                return ResponseEntity.ok(hotels);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/hotels/search
     * Enhanced search with dates and guest information.
     * Frontend expects: List<Hotel> (wrapped in ApiResponse by frontend)
     */
    @PostMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotelsWithDates(@RequestBody HotelSearchRequest request) {
        try {
            List<Hotel> hotels = hotelService.searchHotelsWithDates(request);
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/hotels
     * Frontend expects: List<Hotel> (wrapped in ApiResponse by frontend)
     */
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        try {
            List<Hotel> hotels = hotelService.findAll();
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/hotels/{id}
     * Frontend expects: Hotel (wrapped in ApiResponse by frontend)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String id) {
        try {
            Optional<Hotel> hotelOpt = hotelService.findById(id);
            return hotelOpt.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/hotels/rooms?hotelId=
     * Frontend expects: List<Room> (wrapped in ApiResponse by frontend)
     */
    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getHotelRooms(@RequestParam String hotelId) {
        try {
            List<Room> rooms = roomRepository.findByHotelId(hotelId);
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/hotels/register
     * Register a new hotel or villa property.
     * Frontend expects: PropertyRegistrationResponse {success, message, hotel, propertyId}
     */
    @PostMapping("/register")
    public ResponseEntity<PropertyRegistrationResponse> registerProperty(
            @Valid @RequestBody PropertyRegistrationRequest request) {
        try {
            // Validate request
            if (request.getPropertyName() == null || request.getPropertyName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new PropertyRegistrationResponse(false, "Property name is required"));
            }

            // Create hotel entity from registration request
            Hotel hotel = new Hotel();
            hotel.setName(request.getPropertyName().trim());
            hotel.setAddress(request.getAddress().trim());
            hotel.setCity(request.getCity().trim());
            hotel.setCountry(request.getCountry().trim());
            hotel.setStarRating(request.getStarRating());

            // Register the property
            Hotel registeredHotel = hotelService.registerProperty(hotel, request);

            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PropertyRegistrationResponse(
                    true,
                    "Property registered successfully! Your " + request.getPropertyType() + 
                    " '" + request.getPropertyName() + "' has been listed.",
                    registeredHotel
                ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new PropertyRegistrationResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PropertyRegistrationResponse(
                    false,
                    "Failed to register property: " + e.getMessage()
                ));
        }
    }

    /**
     * POST /api/hotels/vendor/register
     * Register a new hotel vendor with full registration form data.
     * This saves to hotel_vendors table.
     */
    @PostMapping("/vendor/register")
    public ResponseEntity<?> registerHotelVendor(
            @RequestBody Map<String, Object> requestMap) {
        try {
            System.out.println("📥 Received vendor registration request");
            System.out.println("   Request keys: " + requestMap.keySet());
            
            // Convert Map to HotelVendorRegistrationRequest
            HotelVendorRegistrationRequest request = convertMapToRequest(requestMap);
            
            System.out.println("✅ Converted to HotelVendorRegistrationRequest");
            System.out.println("   Hotel Name: " + request.getHotelName());
            System.out.println("   Owner Name: " + request.getOwnerName());
            System.out.println("   Mobile: " + request.getMobileNumber());
            System.out.println("   Email: " + request.getEmail());
            System.out.println("   Property Type: " + request.getPropertyType());
            
            HotelVendorRegistrationResponse response = hotelService.registerVendor(request);
            System.out.println("✅ Registration successful: " + response.getRegistrationId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Validation error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body(new HotelVendorRegistrationResponse(false, e.getMessage(), null, null));
        } catch (Exception e) {
            System.err.println("❌ Unexpected error registering vendor:");
            System.err.println("   Error: " + e.getMessage());
            System.err.println("   Type: " + e.getClass().getName());
            e.printStackTrace();
            String userMessage = com.hotelbooking.mobileapp.util.ErrorMessageSanitizer.getUserFriendlyMessage(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HotelVendorRegistrationResponse(
                    false,
                    "Failed to register hotel vendor: " + userMessage,
                    null,
                    null
                ));
        }
    }
    
    /**
     * Convert Map to HotelVendorRegistrationRequest.
     * Handles field name mapping and type conversions.
     */
    private HotelVendorRegistrationRequest convertMapToRequest(Map<String, Object> map) {
        HotelVendorRegistrationRequest request = new HotelVendorRegistrationRequest();
        
        // Property Type
        if (map.containsKey("propertyType")) {
            request.setPropertyType(String.valueOf(map.get("propertyType")));
        }
        
        // Step 1: Basic Details
        if (map.containsKey("hotelName")) {
            request.setHotelName(String.valueOf(map.get("hotelName")));
        }
        if (map.containsKey("hotelType")) {
            request.setHotelType(String.valueOf(map.get("hotelType")));
        }
        if (map.containsKey("yearOfEstablishment")) {
            request.setYearOfEstablishment(String.valueOf(map.get("yearOfEstablishment")));
        }
        if (map.containsKey("totalRooms")) {
            request.setTotalRooms(String.valueOf(map.get("totalRooms")));
        }
        if (map.containsKey("ownerName")) {
            request.setOwnerName(String.valueOf(map.get("ownerName")));
        }
        if (map.containsKey("mobileNumber")) {
            request.setMobileNumber(String.valueOf(map.get("mobileNumber")));
        }
        if (map.containsKey("alternateContact")) {
            request.setAlternateContact(String.valueOf(map.get("alternateContact")));
        }
        if (map.containsKey("landlineNumbers") && map.get("landlineNumbers") instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> landlines = (List<String>) map.get("landlineNumbers");
            request.setLandlineNumbers(landlines);
        }
        if (map.containsKey("email")) {
            request.setEmail(String.valueOf(map.get("email")));
        }
        if (map.containsKey("website")) {
            request.setWebsite(String.valueOf(map.get("website")));
        }
        if (map.containsKey("personPhotoInfo") && map.get("personPhotoInfo") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> photoInfo = (Map<String, Object>) map.get("personPhotoInfo");
            request.setPersonPhotoInfo(photoInfo);
        }
        
        // Step 2: Address
        if (map.containsKey("addressLine1")) {
            request.setAddressLine1(String.valueOf(map.get("addressLine1")));
        }
        if (map.containsKey("addressLine2")) {
            request.setAddressLine2(String.valueOf(map.get("addressLine2")));
        }
        if (map.containsKey("city")) {
            request.setCity(String.valueOf(map.get("city")));
        }
        if (map.containsKey("district")) {
            request.setDistrict(String.valueOf(map.get("district")));
        }
        if (map.containsKey("state")) {
            request.setState(String.valueOf(map.get("state")));
        }
        if (map.containsKey("pinCode")) {
            request.setPinCode(String.valueOf(map.get("pinCode")));
        }
        if (map.containsKey("landmark")) {
            request.setLandmark(String.valueOf(map.get("landmark")));
        }
        
        // Step 3: Room Details
        if (map.containsKey("selectedRoomTypes") && map.get("selectedRoomTypes") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Boolean> roomTypes = (Map<String, Boolean>) map.get("selectedRoomTypes");
            request.setSelectedRoomTypes(roomTypes);
        }
        if (map.containsKey("roomDetails") && map.get("roomDetails") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> roomDetails = (Map<String, Map<String, Object>>) map.get("roomDetails");
            request.setRoomDetails(roomDetails);
        }
        if (map.containsKey("minTariff")) {
            request.setMinTariff(String.valueOf(map.get("minTariff")));
        }
        if (map.containsKey("maxTariff")) {
            request.setMaxTariff(String.valueOf(map.get("maxTariff")));
        }
        if (map.containsKey("extraBedAvailable")) {
            Object extraBed = map.get("extraBedAvailable");
            if (extraBed instanceof Boolean) {
                request.setExtraBedAvailable((Boolean) extraBed);
            } else {
                request.setExtraBedAvailable(Boolean.parseBoolean(String.valueOf(extraBed)));
            }
        }
        
        // Step 4: Amenities
        if (map.containsKey("basicAmenities") && map.get("basicAmenities") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Boolean> amenities = (Map<String, Boolean>) map.get("basicAmenities");
            request.setBasicAmenities(amenities);
        }
        if (map.containsKey("hotelFacilities") && map.get("hotelFacilities") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Boolean> facilities = (Map<String, Boolean>) map.get("hotelFacilities");
            request.setHotelFacilities(facilities);
        }
        if (map.containsKey("foodServices") && map.get("foodServices") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Boolean> food = (Map<String, Boolean>) map.get("foodServices");
            request.setFoodServices(food);
        }
        if (map.containsKey("additionalAmenities") && map.get("additionalAmenities") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Boolean> additional = (Map<String, Boolean>) map.get("additionalAmenities");
            request.setAdditionalAmenities(additional);
        }
        if (map.containsKey("customAmenities") && map.get("customAmenities") instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> custom = (List<String>) map.get("customAmenities");
            request.setCustomAmenities(custom);
        }
        
        // Legal Documents
        if (map.containsKey("gstNumber")) {
            request.setGstNumber(String.valueOf(map.get("gstNumber")));
        }
        if (map.containsKey("fssaiLicense")) {
            request.setFssaiLicense(String.valueOf(map.get("fssaiLicense")));
        }
        if (map.containsKey("tradeLicense")) {
            request.setTradeLicense(String.valueOf(map.get("tradeLicense")));
        }
        if (map.containsKey("panNumber")) {
            request.setPanNumber(String.valueOf(map.get("panNumber")));
        }
        if (map.containsKey("aadharNumber")) {
            request.setAadharNumber(String.valueOf(map.get("aadharNumber")));
        }
        
        // Step 5: Bank Details
        if (map.containsKey("accountHolderName")) {
            request.setAccountHolderName(String.valueOf(map.get("accountHolderName")));
        }
        if (map.containsKey("bankName")) {
            request.setBankName(String.valueOf(map.get("bankName")));
        }
        if (map.containsKey("accountNumber")) {
            request.setAccountNumber(String.valueOf(map.get("accountNumber")));
        }
        if (map.containsKey("ifscCode")) {
            request.setIfscCode(String.valueOf(map.get("ifscCode")));
        }
        if (map.containsKey("branch")) {
            request.setBranch(String.valueOf(map.get("branch")));
        }
        if (map.containsKey("accountType")) {
            request.setAccountType(String.valueOf(map.get("accountType")));
        }
        if (map.containsKey("uploadedFiles") && map.get("uploadedFiles") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> files = (Map<String, Map<String, Object>>) map.get("uploadedFiles");
            request.setUploadedFiles(files);
        }
        if (map.containsKey("signatureName")) {
            request.setSignatureName(String.valueOf(map.get("signatureName")));
        }
        if (map.containsKey("declarationName")) {
            request.setDeclarationName(String.valueOf(map.get("declarationName")));
        }
        if (map.containsKey("declarationDate")) {
            request.setDeclarationDate(String.valueOf(map.get("declarationDate")));
        }
        if (map.containsKey("declarationAccepted")) {
            Object accepted = map.get("declarationAccepted");
            if (accepted instanceof Boolean) {
                request.setDeclarationAccepted((Boolean) accepted);
            } else {
                request.setDeclarationAccepted(Boolean.parseBoolean(String.valueOf(accepted)));
            }
        }
        
        return request;
    }

    /**
     * GET /api/hotels/vendor/test
     * Test endpoint to verify database connection and table exists.
     */
    @GetMapping("/vendor/test")
    public ResponseEntity<Map<String, Object>> testVendorDatabase() {
        Map<String, Object> response = new java.util.HashMap<>();
        try {
            System.out.println("🔍 Testing hotel_vendors table connection...");
            
            // Try to count vendors
            long count = hotelVendorRepository.count();
            System.out.println("   ✅ Count query successful: " + count + " vendors");
            
            response.put("success", true);
            response.put("message", "Database connection successful");
            response.put("vendorCount", count);
            response.put("tableExists", true);
            response.put("timestamp", System.currentTimeMillis());
            
            // Try to find all vendors (limit to 10 for performance)
            List<HotelVendor> vendors = hotelVendorRepository.findAll();
            if (vendors.size() > 10) {
                vendors = vendors.subList(0, 10);
            }
            
            // Convert to safe JSON format
            List<Map<String, Object>> vendorList = new java.util.ArrayList<>();
            for (HotelVendor vendor : vendors) {
                Map<String, Object> vendorMap = new java.util.HashMap<>();
                vendorMap.put("registrationId", vendor.getRegistrationId());
                vendorMap.put("hotelId", vendor.getHotelId());
                vendorMap.put("hotelName", vendor.getHotelName());
                vendorMap.put("ownerName", vendor.getOwnerName());
                vendorMap.put("city", vendor.getCity());
                vendorMap.put("createdAt", vendor.getCreatedAt());
                vendorList.add(vendorMap);
            }
            
            response.put("vendors", vendorList);
            response.put("totalVendors", hotelVendorRepository.count());
            
            System.out.println("   ✅ Test completed successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Database test failed:");
            System.err.println("   Error: " + e.getMessage());
            e.printStackTrace();
            
            response.put("success", false);
            response.put("message", "Database error: " + e.getMessage());
            response.put("error", e.getClass().getName());
            if (e.getCause() != null) {
                response.put("cause", e.getCause().getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/hotels/vendor/profile
     * Get vendor profile information with statistics.
     * Query parameters: email (optional), mobileNumber (optional)
     */
    @GetMapping("/vendor/profile")
    public ResponseEntity<VendorProfileResponse> getVendorProfile(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobileNumber) {
        try {
            System.out.println("📥 GET /api/hotels/vendor/profile");
            System.out.println("   Request params - email: " + email + ", mobileNumber: " + mobileNumber);
            
            if ((email == null || email.trim().isEmpty()) && 
                (mobileNumber == null || mobileNumber.trim().isEmpty())) {
                System.out.println("   ❌ Both email and mobileNumber are empty");
                return ResponseEntity.badRequest()
                    .body(new VendorProfileResponse(false, "Email or mobile number is required"));
            }
            
            VendorProfileResponse response = hotelService.getVendorProfile(email, mobileNumber);
            
            System.out.println("   Response success: " + response.isSuccess());
            System.out.println("   Response message: " + response.getMessage());
            if (response.isSuccess()) {
                System.out.println("   Owner Name: " + response.getOwnerName());
                System.out.println("   Email: " + response.getEmail());
                System.out.println("   Mobile: " + response.getMobileNumber());
            }
            
            if (!response.isSuccess()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ ERROR GETTING VENDOR PROFILE:");
            System.err.println("   Error: " + e.getMessage());
            e.printStackTrace();
            String userMessage = com.hotelbooking.mobileapp.util.ErrorMessageSanitizer.getUserFriendlyMessage(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new VendorProfileResponse(false, userMessage));
        }
    }
}

