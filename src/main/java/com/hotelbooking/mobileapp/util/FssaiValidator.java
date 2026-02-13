package com.hotelbooking.mobileapp.util;

/**
 * Utility class for FSSAI (Food Safety and Standards Authority of India) License Number validation.
 * Validates FSSAI numbers according to FSSAI format:
 * - 14 digits total
 * - Digit 1: Indicates if the business is registered (1) or licensed (2)
 * - Digits 2-3: State code where the business is registered (e.g., 01 for Andhra Pradesh, 07 for Gujarat)
 * - Digits 4-5: Year of license issuance (e.g., 24 for 2024)
 * - Digits 6-8: Enrolling Master/Quantity (identifies licensing authority)
 * - Digits 9-14: Unique 6-digit license number for the Food Business Operator (FBO)
 * 
 * Example: 10724000123456
 * - 1: Registered (2 would be Licensed)
 * - 07: State code (Gujarat)
 * - 24: Year (2024)
 * - 000: Enrolling Master/Quantity
 * - 123456: Unique license number
 */
public class FssaiValidator {

    // Valid state codes (01-36, excluding some invalid codes)
    private static final String[] VALID_STATE_CODES = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
        "31", "32", "33", "34", "35", "36", "37"
    };

    /**
     * Validates an FSSAI license number.
     * 
     * @param fssaiNumber The FSSAI number to validate (can contain spaces for formatting)
     * @return true if the FSSAI number is valid, false otherwise
     */
    public static boolean isValid(String fssaiNumber) {
        if (fssaiNumber == null || fssaiNumber.trim().isEmpty()) {
            return false;
        }

        // Remove spaces and hyphens for validation
        String cleaned = fssaiNumber.replaceAll("[\\s-]", "");

        // Check if it contains only digits
        if (!cleaned.matches("^\\d+$")) {
            return false;
        }

        // Check length (must be exactly 14 digits)
        if (cleaned.length() != 14) {
            return false;
        }

        // Validate first digit (must be 1 or 2)
        char firstDigit = cleaned.charAt(0);
        if (firstDigit != '1' && firstDigit != '2') {
            return false;
        }

        // Validate state code (digits 2-3)
        String stateCode = cleaned.substring(1, 3);
        if (!isValidStateCode(stateCode)) {
            return false;
        }

        // Validate year (digits 4-5): Should be a reasonable year (00-99, typically 10-99 for 2010-2099)
        String yearStr = cleaned.substring(3, 5);
        int year = Integer.parseInt(yearStr);
        // Accept years from 10 to 99 (representing 2010-2099)
        // You can adjust this range based on your requirements
        if (year < 10 || year > 99) {
            return false;
        }

        // Validate Enrolling Master/Quantity (digits 6-8): Should be 3 digits (000-999)
        String enrollingMaster = cleaned.substring(5, 8);
        if (!enrollingMaster.matches("^\\d{3}$")) {
            return false;
        }

        // Validate unique license number (digits 9-14): Should be 6 digits
        String licenseNumber = cleaned.substring(8, 14);
        if (!licenseNumber.matches("^\\d{6}$")) {
            return false;
        }

        return true;
    }

    /**
     * Validates the state code (digits 2-3).
     * 
     * @param stateCode The 2-digit state code
     * @return true if valid
     */
    private static boolean isValidStateCode(String stateCode) {
        if (stateCode == null || stateCode.length() != 2) {
            return false;
        }
        
        // Check if it's a valid numeric state code
        if (!stateCode.matches("^\\d{2}$")) {
            return false;
        }
        
        // Check against known valid state codes
        for (String validCode : VALID_STATE_CODES) {
            if (validCode.equals(stateCode)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Formats an FSSAI number with spaces for better readability.
     * Format: X XX XX XXX XXXXXX
     * Example: 1 07 24 000 123456
     * 
     * @param fssaiNumber The FSSAI number to format
     * @return Formatted FSSAI number with spaces, or original if invalid
     */
    public static String format(String fssaiNumber) {
        if (fssaiNumber == null || fssaiNumber.trim().isEmpty()) {
            return fssaiNumber;
        }

        String cleaned = fssaiNumber.replaceAll("[\\s-]", "");
        
        if (cleaned.length() != 14 || !cleaned.matches("^\\d+$")) {
            return fssaiNumber; // Return original if invalid
        }

        // Format as: X XX XX XXX XXXXXX
        // Digit 1 + State(2) + Year(2) + Enrolling(3) + License(6)
        return cleaned.substring(0, 1) + " " + 
               cleaned.substring(1, 3) + " " + 
               cleaned.substring(3, 5) + " " + 
               cleaned.substring(5, 8) + " " + 
               cleaned.substring(8, 14);
    }
}

