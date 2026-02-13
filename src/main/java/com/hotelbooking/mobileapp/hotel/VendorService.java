package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.util.IdGeneratorService;
import com.hotelbooking.mobileapp.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Check if vendor account details exist by email or phone.
     * Returns the vendor if found, null otherwise.
     */
    public Optional<Vendor> checkAccountExists(String phoneOrEmail) {
        if (phoneOrEmail == null || phoneOrEmail.trim().isEmpty()) {
            return Optional.empty();
        }
        
        String normalized = phoneOrEmail.trim();
        
        // Check if it's an email
        if (normalized.contains("@")) {
            String email = ValidationUtils.normalizeEmail(normalized);
            return vendorRepository.findByEmail(email);
        } else {
            // It's a phone number
            String phone = ValidationUtils.normalizePhone(normalized);
            return vendorRepository.findByPhone(phone);
        }
    }

    /**
     * Save Account Details to vendors table.
     * This is called when user fills Account Details form.
     */
    public AccountDetailsResponse saveAccountDetails(AccountDetailsRequest request) {
        // Collect all validation errors
        Map<String, String> fieldErrors = new HashMap<>();
        
        // Validate full name
        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            fieldErrors.put("fullName", "Full name is required. " + ValidationRulesHelper.getFieldRules("fullName"));
        } else if (!ValidationUtils.isValidName(request.getFullName())) {
            fieldErrors.put("fullName", "Invalid full name. " + ValidationRulesHelper.getFieldRules("fullName"));
        }
        
        // Validate business name
        if (request.getBusinessName() == null || request.getBusinessName().trim().isEmpty()) {
            fieldErrors.put("businessName", "Business name is required. " + ValidationRulesHelper.getFieldRules("businessName"));
        } else if (!ValidationUtils.isValidBusinessName(request.getBusinessName())) {
            fieldErrors.put("businessName", "Invalid business name. " + ValidationRulesHelper.getFieldRules("businessName"));
        }
        
        // Validate phone or email
        if (request.getPhoneOrEmail() == null || request.getPhoneOrEmail().trim().isEmpty()) {
            fieldErrors.put("phoneOrEmail", "Phone or email is required. " + ValidationRulesHelper.getFieldRules("phoneOrEmail"));
        }
        
        // Validate password
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            fieldErrors.put("password", "Password is required. " + ValidationRulesHelper.getFieldRules("password"));
        } else if (!ValidationUtils.isValidPassword(request.getPassword())) {
            fieldErrors.put("password", "Invalid password. " + ValidationRulesHelper.getFieldRules("password"));
        }
        
        // If there are validation errors, throw exception with field-specific errors
        if (!fieldErrors.isEmpty()) {
            throw new ValidationException("Validation failed. Please check the input fields.", fieldErrors);
        }

        // Extract and validate email or phone from phoneOrEmail field
        String phoneOrEmail = request.getPhoneOrEmail().trim();
        String email = null;
        String phone = null;

        if (phoneOrEmail.contains("@")) {
            // It's an email - validate format
            if (!ValidationUtils.isValidEmail(phoneOrEmail)) {
                throw new ValidationException("phoneOrEmail", 
                    "Invalid email format. " + ValidationRulesHelper.getFieldRules("phoneOrEmail"));
            }
            email = ValidationUtils.normalizeEmail(phoneOrEmail);
            // Check if vendor already exists with this email
            Optional<Vendor> existingVendor = vendorRepository.findByEmail(email);
            if (existingVendor.isPresent()) {
                // Update existing vendor
                Vendor vendor = existingVendor.get();
                vendor.setFullName(request.getFullName().trim());
                vendor.setBusinessName(request.getBusinessName().trim());
                vendor.setEmail(email);
                if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                    // Hash password before storing
                    String hashedPassword = passwordEncoder.encode(request.getPassword());
                    vendor.setPassword(hashedPassword);
                }
                Vendor savedVendor = vendorRepository.save(vendor);
                
                // Clear password from response for security
                Vendor vendorResponse = new Vendor();
                vendorResponse.setVendorId(savedVendor.getVendorId());
                vendorResponse.setFullName(savedVendor.getFullName());
                vendorResponse.setBusinessName(savedVendor.getBusinessName());
                vendorResponse.setPhone(savedVendor.getPhone());
                vendorResponse.setEmail(savedVendor.getEmail());
                vendorResponse.setStatus(savedVendor.getStatus());
                vendorResponse.setCreatedAt(savedVendor.getCreatedAt());
                vendorResponse.setUpdatedAt(savedVendor.getUpdatedAt());
                // Password is NOT set in response
                
                return new AccountDetailsResponse(true, "Account details updated successfully", savedVendor.getVendorId(), vendorResponse);
            }
        } else {
            // It's a phone number - validate format
            if (!ValidationUtils.isValidPhone(phoneOrEmail)) {
                throw new ValidationException("phoneOrEmail", 
                    "Invalid phone number format. " + ValidationRulesHelper.getFieldRules("phoneOrEmail"));
            }
            phone = ValidationUtils.normalizePhone(phoneOrEmail);
            // Check if vendor already exists with this phone
            Optional<Vendor> existingVendor = vendorRepository.findByPhone(phone);
            if (existingVendor.isPresent()) {
                // Update existing vendor
                Vendor vendor = existingVendor.get();
                vendor.setFullName(request.getFullName().trim());
                vendor.setBusinessName(request.getBusinessName().trim());
                vendor.setPhone(phone);
                if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                    // Hash password before storing
                    String hashedPassword = passwordEncoder.encode(request.getPassword());
                    vendor.setPassword(hashedPassword);
                }
                Vendor savedVendor = vendorRepository.save(vendor);
                
                // Clear password from response for security
                Vendor vendorResponse = new Vendor();
                vendorResponse.setVendorId(savedVendor.getVendorId());
                vendorResponse.setFullName(savedVendor.getFullName());
                vendorResponse.setBusinessName(savedVendor.getBusinessName());
                vendorResponse.setPhone(savedVendor.getPhone());
                vendorResponse.setEmail(savedVendor.getEmail());
                vendorResponse.setStatus(savedVendor.getStatus());
                vendorResponse.setCreatedAt(savedVendor.getCreatedAt());
                vendorResponse.setUpdatedAt(savedVendor.getUpdatedAt());
                // Password is NOT set in response
                
                return new AccountDetailsResponse(true, "Account details updated successfully", savedVendor.getVendorId(), vendorResponse);
            }
        }

        // Generate vendor ID
        String vendorId = idGeneratorService.generateVendorRegistrationId();

        // Create new vendor entity
        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorId);
        vendor.setFullName(request.getFullName().trim());
        vendor.setBusinessName(request.getBusinessName().trim());
        vendor.setPhone(phone);
        vendor.setEmail(email);
        // Password is already validated above, hash it before storing
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        vendor.setPassword(hashedPassword);
        vendor.setStatus("ACTIVE");

        // Save vendor
        Vendor savedVendor = vendorRepository.save(vendor);
        
        // Clear password from response for security
        Vendor vendorResponse = new Vendor();
        vendorResponse.setVendorId(savedVendor.getVendorId());
        vendorResponse.setFullName(savedVendor.getFullName());
        vendorResponse.setBusinessName(savedVendor.getBusinessName());
        vendorResponse.setPhone(savedVendor.getPhone());
        vendorResponse.setEmail(savedVendor.getEmail());
        vendorResponse.setStatus(savedVendor.getStatus());
        vendorResponse.setCreatedAt(savedVendor.getCreatedAt());
        vendorResponse.setUpdatedAt(savedVendor.getUpdatedAt());
        // Password is NOT set in response

        return new AccountDetailsResponse(
            true,
            "Account details saved successfully",
            savedVendor.getVendorId(),
            vendorResponse
        );
    }
}
