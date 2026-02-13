package com.hotelbooking.mobileapp.util;

/**
 * Utility class for Aadhar number validation.
 * Validates Aadhar numbers according to UIDAI specifications:
 * - 12 numeric digits
 * - First digit cannot be 0 or 1
 * - 12th digit is a checksum calculated using Verhoeff algorithm
 */
public class AadharValidator {

    // Verhoeff algorithm tables
    private static final int[][] MULTIPLICATION_TABLE = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 2, 3, 4, 0, 6, 7, 8, 9, 5},
        {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
        {3, 4, 0, 1, 2, 8, 9, 5, 6, 7},
        {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
        {5, 9, 8, 7, 6, 0, 4, 3, 2, 1},
        {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
        {7, 6, 5, 9, 8, 2, 1, 0, 4, 3},
        {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
        {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
    };

    private static final int[][] PERMUTATION_TABLE = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
        {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
        {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
        {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
        {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
        {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
        {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}
    };


    /**
     * Validates an Aadhar number.
     * 
     * @param aadharNumber The Aadhar number to validate (can contain spaces for formatting)
     * @return true if the Aadhar number is valid, false otherwise
     */
    public static boolean isValid(String aadharNumber) {
        if (aadharNumber == null || aadharNumber.trim().isEmpty()) {
            return false;
        }

        // Remove spaces and hyphens for validation
        String cleaned = aadharNumber.replaceAll("[\\s-]", "");

        // Check if it contains only digits
        if (!cleaned.matches("^\\d+$")) {
            return false;
        }

        // Check length (must be exactly 12 digits)
        if (cleaned.length() != 12) {
            return false;
        }

        // Check first digit (cannot be 0 or 1)
        char firstDigit = cleaned.charAt(0);
        if (firstDigit == '0' || firstDigit == '1') {
            return false;
        }

        // Validate checksum using Verhoeff algorithm
        return validateVerhoeffChecksum(cleaned);
    }

    /**
     * Validates the checksum digit using Verhoeff algorithm.
     * 
     * @param aadharNumber 12-digit Aadhar number
     * @return true if checksum is valid
     */
    private static boolean validateVerhoeffChecksum(String aadharNumber) {
        int check = 0;
        int length = aadharNumber.length();

        for (int i = 0; i < length; i++) {
            int digit = Character.getNumericValue(aadharNumber.charAt(length - 1 - i));
            check = MULTIPLICATION_TABLE[check][PERMUTATION_TABLE[((i + 1) % 8)][digit]];
        }

        return check == 0;
    }

    /**
     * Formats an Aadhar number with spaces (e.g., "1234 5678 9012").
     * 
     * @param aadharNumber The Aadhar number to format
     * @return Formatted Aadhar number with spaces, or original if invalid
     */
    public static String format(String aadharNumber) {
        if (aadharNumber == null || aadharNumber.trim().isEmpty()) {
            return aadharNumber;
        }

        String cleaned = aadharNumber.replaceAll("[\\s-]", "");
        
        if (cleaned.length() != 12 || !cleaned.matches("^\\d+$")) {
            return aadharNumber; // Return original if invalid
        }

        // Format as: XXXX XXXX XXXX
        return cleaned.substring(0, 4) + " " + cleaned.substring(4, 8) + " " + cleaned.substring(8, 12);
    }
}

