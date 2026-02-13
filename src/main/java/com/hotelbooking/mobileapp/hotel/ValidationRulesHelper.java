package com.hotelbooking.mobileapp.hotel;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to provide validation rules and hints for each input field.
 * These can be used to display helpful messages to users.
 */
public class ValidationRulesHelper {
    
    // Validation rules for each field
    public static final Map<String, String> FIELD_RULES = new HashMap<>();
    
    static {
        FIELD_RULES.put("fullName", 
            "Full name must be 2-100 characters. Only letters, spaces, dots (.), apostrophes ('), and hyphens (-) are allowed. Examples: John Doe, Mary O'Brien, Dr. Smith");
        
        FIELD_RULES.put("businessName", 
            "Business name must be 2-150 characters. Letters, numbers, spaces, and common punctuation (., &, ', -) are allowed. Examples: ABC Hotels, Hotel & Resorts, 123 Hotels Pvt. Ltd.");
        
        FIELD_RULES.put("phoneOrEmail", 
            "Enter either a valid email address or phone number with country code. Email: user@example.com | Phone: +919876543210 or 9876543210 (10-digit Indian number)");
        
        FIELD_RULES.put("password", 
            "Password must be 8-50 characters and include: at least one uppercase letter (A-Z), one lowercase letter (a-z), one number (0-9), and one special character (@$!%*?&). Example: SecurePass123!");
    }
    
    // Placeholder/hint text for each field
    public static final Map<String, String> FIELD_HINTS = new HashMap<>();
    
    static {
        FIELD_HINTS.put("fullName", "e.g., John Doe, Mary O'Brien");
        FIELD_HINTS.put("businessName", "e.g., ABC Hotels & Resorts");
        FIELD_HINTS.put("phoneOrEmail", "e.g., john@example.com or +919876543210");
        FIELD_HINTS.put("password", "Must have uppercase, lowercase, number & special char");
    }
    
    // Short validation rules (for tooltips or inline hints)
    public static final Map<String, String> FIELD_SHORT_RULES = new HashMap<>();
    
    static {
        FIELD_SHORT_RULES.put("fullName", "2-100 chars, letters only");
        FIELD_SHORT_RULES.put("businessName", "2-150 chars, alphanumeric");
        FIELD_SHORT_RULES.put("phoneOrEmail", "Valid email or phone with country code");
        FIELD_SHORT_RULES.put("password", "8-50 chars: A-Z, a-z, 0-9, special char");
    }
    
    /**
     * Get validation rules for a specific field.
     * @param fieldName Field name (fullName, businessName, phoneOrEmail, password)
     * @return Validation rules description
     */
    public static String getFieldRules(String fieldName) {
        return FIELD_RULES.getOrDefault(fieldName, "Please enter a valid value");
    }
    
    /**
     * Get hint text for a specific field.
     * @param fieldName Field name
     * @return Hint text
     */
    public static String getFieldHint(String fieldName) {
        return FIELD_HINTS.getOrDefault(fieldName, "");
    }
    
    /**
     * Get short validation rules for a specific field.
     * @param fieldName Field name
     * @return Short validation rules
     */
    public static String getShortRules(String fieldName) {
        return FIELD_SHORT_RULES.getOrDefault(fieldName, "");
    }
    
    /**
     * Get all validation rules as a map.
     * @return Map of field name to validation rules
     */
    public static Map<String, String> getAllRules() {
        return new HashMap<>(FIELD_RULES);
    }
    
    /**
     * Get all hints as a map.
     * @return Map of field name to hint text
     */
    public static Map<String, String> getAllHints() {
        return new HashMap<>(FIELD_HINTS);
    }
    
    /**
     * Get all short rules as a map.
     * @return Map of field name to short validation rules
     */
    public static Map<String, String> getAllShortRules() {
        return new HashMap<>(FIELD_SHORT_RULES);
    }
}

