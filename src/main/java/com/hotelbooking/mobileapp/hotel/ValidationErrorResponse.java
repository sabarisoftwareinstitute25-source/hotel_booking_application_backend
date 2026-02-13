package com.hotelbooking.mobileapp.hotel;

import java.util.HashMap;
import java.util.Map;

/**
 * Response DTO for validation errors with field-specific error messages.
 */
public class ValidationErrorResponse {
    private boolean success = false;
    private String message;
    private Map<String, String> fieldErrors; // Field name -> Error message
    private Map<String, String> fieldRules; // Field name -> Validation rules (for display)
    private Map<String, String> fieldHints; // Field name -> Hint text (for placeholders)

    public ValidationErrorResponse() {
        this.fieldErrors = new HashMap<>();
        this.fieldRules = ValidationRulesHelper.getAllRules();
        this.fieldHints = ValidationRulesHelper.getAllHints();
    }

    public ValidationErrorResponse(String message) {
        this();
        this.message = message;
    }

    public ValidationErrorResponse(String message, Map<String, String> fieldErrors) {
        this.message = message;
        this.fieldErrors = fieldErrors != null ? fieldErrors : new HashMap<>();
        this.fieldRules = ValidationRulesHelper.getAllRules();
        this.fieldHints = ValidationRulesHelper.getAllHints();
    }

    public void addFieldError(String field, String errorMessage) {
        this.fieldErrors.put(field, errorMessage);
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

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
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

