package com.hotelbooking.mobileapp.hotel;

import java.util.Map;

/**
 * Response DTO for validation rules that can be displayed in input fields.
 */
public class ValidationRulesResponse {
    private boolean success = true;
    private String message = "Validation rules retrieved successfully";
    private Map<String, String> fieldRules; // Full validation rules
    private Map<String, String> fieldHints; // Short hints for placeholders
    private Map<String, String> fieldShortRules; // Short rules for tooltips

    public ValidationRulesResponse() {
        this.fieldRules = ValidationRulesHelper.getAllRules();
        this.fieldHints = ValidationRulesHelper.getAllHints();
        this.fieldShortRules = ValidationRulesHelper.getAllShortRules();
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

    public Map<String, String> getFieldShortRules() {
        return fieldShortRules;
    }

    public void setFieldShortRules(Map<String, String> fieldShortRules) {
        this.fieldShortRules = fieldShortRules;
    }
}

