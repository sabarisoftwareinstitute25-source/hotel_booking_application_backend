package com.hotelbooking.mobileapp.hotel;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "*")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    /**
     * GET /api/hotels/vendor/validation-rules
     * Get validation rules for all input fields.
     * This can be used to display validation rules in input fields before user submits.
     */
    @GetMapping("/validation-rules")
    public ResponseEntity<ValidationRulesResponse> getValidationRules() {
        ValidationRulesResponse response = new ValidationRulesResponse();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/hotels/vendor/check-account
     * Check if vendor account details exist by email or phone.
     * Query parameter: phoneOrEmail (required)
     * Returns: {exists: boolean, vendor: Vendor?}
     */
    @GetMapping("/check-account")
    public ResponseEntity<Map<String, Object>> checkAccountExists(
            @RequestParam String phoneOrEmail) {
        try {
            java.util.Optional<Vendor> vendorOpt = vendorService.checkAccountExists(phoneOrEmail);
            
            Map<String, Object> response = new HashMap<>();
            response.put("exists", vendorOpt.isPresent());
            
            if (vendorOpt.isPresent()) {
                Vendor vendor = vendorOpt.get();
                // Create a response map without password
                Map<String, Object> vendorData = new HashMap<>();
                vendorData.put("vendorId", vendor.getVendorId());
                vendorData.put("fullName", vendor.getFullName());
                vendorData.put("businessName", vendor.getBusinessName());
                vendorData.put("phone", vendor.getPhone());
                vendorData.put("email", vendor.getEmail());
                vendorData.put("status", vendor.getStatus());
                response.put("vendor", vendorData);
            } else {
                response.put("vendor", null);
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("exists", false);
            errorResponse.put("error", "Failed to check account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * POST /api/hotels/vendor/account-details
     * Save Account Details (Full Name, Business Name, Phone/Email) to vendors table.
     */
    @PostMapping("/account-details")
    public ResponseEntity<?> saveAccountDetails(
            @Valid @RequestBody AccountDetailsRequest request) {
        try {
            AccountDetailsResponse response = vendorService.saveAccountDetails(request);
            // Include validation rules in success response so frontend can display them
            // Note: AccountDetailsResponse doesn't have fieldRules, but we can add them if needed
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException e) {
            // Return field-specific validation errors with rules
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                e.getMessage() != null ? e.getMessage() : "Validation failed. Please check the input fields.",
                e.getFieldErrors()
            );
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (IllegalArgumentException e) {
            // Fallback for other validation errors
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                "Failed to save account details: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Handle Jakarta Bean Validation errors (@Valid annotation).
     * This catches validation errors from annotations like @NotBlank, @Size, @Pattern.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
            "Validation failed. Please check the input fields.",
            fieldErrors
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
}

