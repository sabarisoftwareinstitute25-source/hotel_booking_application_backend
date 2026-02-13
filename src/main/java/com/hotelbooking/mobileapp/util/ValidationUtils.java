package com.hotelbooking.mobileapp.util;

import java.util.regex.Pattern;

/**
 * Utility class for validation logic.
 */
public class ValidationUtils {
    
    // Email pattern: standard email format
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    // International phone number pattern (E.164 format)
    // Format: +[country code][number] (e.g., +919876543210, +1234567890, +441234567890)
    // Accepts: + followed by 1-3 digit country code, then 7-15 digits
    // Also accepts: 10-digit Indian number without country code (for backward compatibility)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^(\\+[1-9]\\d{0,2})?[1-9]\\d{6,14}$"
    );
    
    // Pattern for Indian phone numbers (10 digits starting with 6-9)
    private static final Pattern INDIAN_PHONE_PATTERN = Pattern.compile(
        "^[6-9]\\d{9}$"
    );
    
    // Name pattern: letters, spaces, dots, apostrophes, hyphens
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[a-zA-Z\\s.'-]{2,100}$"
    );
    
    // Business name pattern: letters, numbers, spaces, common punctuation
    private static final Pattern BUSINESS_NAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9\\s.,&'-]{2,150}$"
    );
    
    // Password pattern: at least 8 chars, uppercase, lowercase, digit, special char
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$"
    );
    
    /**
     * Validates email format.
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim().toLowerCase()).matches();
    }
    
    /**
     * Validates international phone number format (E.164 format).
     * Accepts:
     * - International format: +919876543210, +1234567890, +441234567890
     * - Indian format (backward compatibility): 9876543210
     * @param phone Phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Remove spaces, dashes, and parentheses
        String cleaned = phone.replaceAll("[\\s-()]", "");
        
        // Check if it's a 10-digit Indian number (backward compatibility)
        if (INDIAN_PHONE_PATTERN.matcher(cleaned).matches()) {
            return true;
        }
        
        // Check international format: +[country code][number]
        // Must start with +, followed by 1-3 digit country code, then 7-15 digits
        if (cleaned.startsWith("+")) {
            // Remove + for pattern matching
            String withoutPlus = cleaned.substring(1);
            // Total digits should be 8-15 (country code + number)
            if (withoutPlus.length() >= 8 && withoutPlus.length() <= 15) {
                // First 1-3 digits are country code, rest is number
                // Country code should start with 1-9, number should start with 1-9
                return PHONE_PATTERN.matcher(cleaned).matches();
            }
        }
        
        return false;
    }
    
    /**
     * Validates name format (full name).
     * @param name Name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.length() < 2 || trimmed.length() > 100) {
            return false;
        }
        return NAME_PATTERN.matcher(trimmed).matches();
    }
    
    /**
     * Validates business name format.
     * @param businessName Business name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidBusinessName(String businessName) {
        if (businessName == null || businessName.trim().isEmpty()) {
            return false;
        }
        String trimmed = businessName.trim();
        if (trimmed.length() < 2 || trimmed.length() > 150) {
            return false;
        }
        return BUSINESS_NAME_PATTERN.matcher(trimmed).matches();
    }
    
    /**
     * Validates password strength.
     * Requirements:
     * - At least 8 characters
     * - At least one uppercase letter
     * - At least one lowercase letter
     * - At least one digit
     * - At least one special character (@$!%*?&)
     * @param password Password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.length() < 8 || password.length() > 50) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }
    
    /**
     * Normalizes phone number to E.164 format (with country code).
     * Format: +[country code][number]
     * Examples:
     * - "9876543210" -> "+919876543210" (assumes India if no country code)
     * - "+919876543210" -> "+919876543210" (already in E.164 format)
     * - "+1234567890" -> "+1234567890" (US number)
     * @param phone Phone number to normalize
     * @return Normalized phone number in E.164 format
     */
    public static String normalizePhone(String phone) {
        if (phone == null) {
            return null;
        }
        // Remove spaces, dashes, and parentheses
        String cleaned = phone.replaceAll("[\\s-()]", "");
        
        // If it starts with +, it's already in international format
        if (cleaned.startsWith("+")) {
            return cleaned;
        }
        
        // If it's a 10-digit number starting with 6-9, assume it's Indian and add +91
        if (INDIAN_PHONE_PATTERN.matcher(cleaned).matches()) {
            return "+91" + cleaned;
        }
        
        // If it starts with 91 and is 12 digits, it's Indian with country code
        if (cleaned.startsWith("91") && cleaned.length() == 12) {
            return "+" + cleaned;
        }
        
        // For other cases, try to detect country code or default to +91
        // If it's 10 digits, assume Indian
        if (cleaned.length() == 10 && cleaned.matches("^[1-9]\\d{9}$")) {
            return "+91" + cleaned;
        }
        
        // Return as is if we can't determine (shouldn't happen if validation passed)
        return cleaned.startsWith("+") ? cleaned : "+" + cleaned;
    }
    
    /**
     * Extracts country code from phone number.
     * @param phone Phone number in E.164 format (e.g., +919876543210)
     * @return Country code (e.g., "91") or null if not found
     */
    public static String extractCountryCode(String phone) {
        if (phone == null || !phone.startsWith("+")) {
            return null;
        }
        String withoutPlus = phone.substring(1);
        // Country codes are 1-3 digits
        if (withoutPlus.length() >= 8) {
            // Try 1-digit country code (US/Canada)
            if (withoutPlus.length() == 11 && withoutPlus.startsWith("1")) {
                return "1";
            }
            // Try 2-digit country code (most common)
            if (withoutPlus.length() >= 10) {
                String twoDigit = withoutPlus.substring(0, 2);
                if (twoDigit.matches("^[1-9]\\d$")) {
                    return twoDigit;
                }
            }
            // Try 3-digit country code
            if (withoutPlus.length() >= 11) {
                String threeDigit = withoutPlus.substring(0, 3);
                if (threeDigit.matches("^[1-9]\\d{2}$")) {
                    return threeDigit;
                }
            }
        }
        return null;
    }
    
    /**
     * Normalizes email to lowercase and trims whitespace.
     * @param email Email to normalize
     * @return Normalized email
     */
    public static String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        return email.trim().toLowerCase();
    }
}

