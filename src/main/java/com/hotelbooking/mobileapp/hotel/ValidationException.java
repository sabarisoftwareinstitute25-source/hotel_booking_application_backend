package com.hotelbooking.mobileapp.hotel;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom exception for validation errors with field-specific error messages.
 */
public class  ValidationException extends RuntimeException {
    private Map<String, String> fieldErrors;

    public ValidationException(String message) {
        super(message);
        this.fieldErrors = new HashMap<>();
    }

    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors != null ? fieldErrors : new HashMap<>();
    }

    public ValidationException(String field, String errorMessage) {
        super("Validation failed for field: " + field);
        this.fieldErrors = new HashMap<>();
        this.fieldErrors.put(field, errorMessage);
    }

    public void addFieldError(String field, String errorMessage) {
        this.fieldErrors.put(field, errorMessage);
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}

