package com.hotelbooking.mobileapp.util;

/**
 * Utility class for GSTIN (GST Identification Number) validation.
 * Validates GSTIN according to Indian GST format:
 * - 15 characters total
 * - Digits 1-2: State code (2 digits)
 * - Digits 3-12: PAN (10 alphanumeric characters)
 * - Digit 13: Entity number (1 character: 1-9 or A-Z)
 * - Digit 14: Default 'Z' (1 character)
 * - Digit 15: Checksum (1 character: digit or letter)
 * 
 * Example: 27ABCDE1234F1Z5
 */
public class GstinValidator {

    // State codes (00-37, excluding some invalid codes)
    private static final String[] VALID_STATE_CODES = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
        "31", "32", "33", "34", "35", "36", "37"
    };

    /**
     * Validates a GSTIN number.
     * 
     * @param gstin The GSTIN to validate (can contain spaces for formatting)
     * @return true if the GSTIN is valid, false otherwise
     */
    public static boolean isValid(String gstin) {
        if (gstin == null || gstin.trim().isEmpty()) {
            return false;
        }

        // Remove spaces and convert to uppercase for validation
        String cleaned = gstin.replaceAll("\\s", "").toUpperCase();

        // Check length (must be exactly 15 characters)
        if (cleaned.length() != 15) {
            return false;
        }

        // Check if it contains only alphanumeric characters
        if (!cleaned.matches("^[A-Z0-9]{15}$")) {
            return false;
        }

        // Validate state code (digits 1-2)
        String stateCode = cleaned.substring(0, 2);
        if (!isValidStateCode(stateCode)) {
            return false;
        }

        // Validate PAN format (digits 3-12): 5 letters + 4 digits + 1 letter
        String pan = cleaned.substring(2, 12);
        if (!pan.matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")) {
            return false;
        }

        // Validate entity number (digit 13): 1-9 or A-Z
        char entityNumber = cleaned.charAt(12);
        if (!isValidEntityNumber(entityNumber)) {
            return false;
        }

        // Validate default character (digit 14): Should be 'Z'
        char defaultChar = cleaned.charAt(13);
        if (defaultChar != 'Z') {
            return false;
        }

        // Validate checksum (digit 15)
        char checksum = cleaned.charAt(14);
        if (!isValidChecksum(cleaned, checksum)) {
            return false;
        }

        return true;
    }

    /**
     * Validates the state code (first 2 digits).
     * 
     * @param stateCode The 2-digit state code
     * @return true if valid
     */
    private static boolean isValidStateCode(String stateCode) {
        if (stateCode == null || stateCode.length() != 2) {
            return false;
        }
        
        // Check if it's a valid numeric state code (00-37)
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
     * Validates the entity number (13th character).
     * 
     * @param entityNumber The entity number character
     * @return true if valid (1-9 or A-Z)
     */
    private static boolean isValidEntityNumber(char entityNumber) {
        // Entity number can be 1-9 or A-Z
        return (entityNumber >= '1' && entityNumber <= '9') || 
               (entityNumber >= 'A' && entityNumber <= 'Z');
    }

    /**
     * Validates the checksum digit using the GSTIN checksum algorithm.
     * 
     * @param gstin The full GSTIN (15 characters)
     * @param checksum The checksum character to validate
     * @return true if checksum is valid
     */
    private static boolean isValidChecksum(String gstin, char checksum) {
        // GSTIN checksum algorithm
        int[] factor = {1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1};
        int sum = 0;
        
        for (int i = 0; i < 14; i++) {
            char ch = gstin.charAt(i);
            int digit = Character.isDigit(ch) ? (ch - '0') : (ch - 'A' + 10);
            int product = digit * factor[i];
            sum += (product / 10) + (product % 10);
        }
        
        int checkDigit = (36 - (sum % 36)) % 36;
        char expectedChecksum = (checkDigit < 10) ? (char)('0' + checkDigit) : (char)('A' + checkDigit - 10);
        
        return checksum == expectedChecksum;
    }

    /**
     * Formats a GSTIN number with spaces (e.g., "27ABCDE1234F 1Z5").
     * 
     * @param gstin The GSTIN to format
     * @return Formatted GSTIN with spaces, or original if invalid
     */
    public static String format(String gstin) {
        if (gstin == null || gstin.trim().isEmpty()) {
            return gstin;
        }

        String cleaned = gstin.replaceAll("\\s", "").toUpperCase();
        
        if (cleaned.length() != 15 || !cleaned.matches("^[A-Z0-9]{15}$")) {
            return gstin; // Return original if invalid
        }

        // Format as: XX XXXXX XXXX X X X
        // State(2) + PAN(10) + Entity(1) + Default(1) + Checksum(1)
        return cleaned.substring(0, 2) + " " + 
               cleaned.substring(2, 12) + " " + 
               cleaned.substring(12, 13) + 
               cleaned.substring(13, 14) + 
               cleaned.substring(14, 15);
    }
}

