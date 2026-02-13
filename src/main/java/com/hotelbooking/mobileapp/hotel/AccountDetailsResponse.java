package com.hotelbooking.mobileapp.hotel;

import java.util.Map;

/**
 * Response DTO for Account Details registration.
 */
public class AccountDetailsResponse {
    private boolean success;
    private String message;
    private String vendorId; // ID in vendors table
    private Vendor vendor; // Optional
    private Map<String, String> fieldRules; // Validation rules for display in input fields
    private Map<String, String> fieldHints; // Hints for placeholders

    public AccountDetailsResponse() {
        // Include validation rules in all responses
        this.fieldRules = ValidationRulesHelper.getAllRules();
        this.fieldHints = ValidationRulesHelper.getAllHints();
    }

    public AccountDetailsResponse(boolean success, String message, String vendorId, Vendor vendor) {
        this();
        this.success = success;
        this.message = message;
        this.vendorId = vendorId;
        this.vendor = vendor;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Map<String, String> getFieldRules() {
        return fieldRules;
    }

    public void setFieldRules(Map<String, String> fieldRules) {
        this.fieldRules = fieldRules;
    }

    public Map<String, String> getFieldHints() {
        return fieldHints;
    }

    public void setFieldHints(Map<String, String> fieldHints) {
        this.fieldHints = fieldHints;
    }
}

