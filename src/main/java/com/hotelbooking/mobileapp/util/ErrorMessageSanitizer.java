package com.hotelbooking.mobileapp.util;

/**
 * Utility class to sanitize error messages for user-friendly display.
 * Removes technical details and stack traces from error messages.
 */
public class ErrorMessageSanitizer {

    /**
     * Sanitizes an error message to be user-friendly.
     * Removes technical details like exception class names, stack traces, etc.
     * 
     * @param errorMessage The raw error message
     * @return User-friendly error message
     */
    public static String sanitize(String errorMessage) {
        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            return "An unexpected error occurred. Please try again.";
        }

        String sanitized = errorMessage;

        // Remove exception class names
        sanitized = sanitized.replaceAll("(?i)(org\\.|com\\.|java\\.|jakarta\\.|springframework\\.)[a-zA-Z0-9_.]+\\.", "");
        sanitized = sanitized.replaceAll("[A-Z][a-zA-Z0-9]*Exception:?", "");
        sanitized = sanitized.replaceAll("[A-Z][a-zA-Z0-9]*Error:?", "");

        // Remove stack trace indicators
        sanitized = sanitized.replaceAll("(?i)at\\s+[a-zA-Z0-9_.]+\\.[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+\\([^)]+\\)", "");
        sanitized = sanitized.replaceAll("Caused by:.*", "");
        sanitized = sanitized.replaceAll("Stack trace:.*", "");

        // Remove technical error patterns
        sanitized = sanitized.replaceAll("(?i)Could not find a FormatMapper.*", "Unable to process the request. Please try again.");
        sanitized = sanitized.replaceAll("(?i)Failed to save vendor registration:.*", "Unable to save registration. Please check your information and try again.");
        sanitized = sanitized.replaceAll("(?i)Database.*error.*", "Unable to save data. Please try again.");
        sanitized = sanitized.replaceAll("(?i)Constraint.*violation.*", "Invalid data provided. Please check your input.");
        sanitized = sanitized.replaceAll("(?i)Validation failed:.*", "Please check the form fields and correct any errors.");

        // Clean up multiple spaces and newlines
        sanitized = sanitized.replaceAll("\\s+", " ");
        sanitized = sanitized.replaceAll("\\n+", " ");
        sanitized = sanitized.trim();

        // If message is too technical or empty after sanitization, return generic message
        if (sanitized.isEmpty() || sanitized.length() < 10 || 
            sanitized.matches(".*[A-Z]{3,}.*") || // Contains acronyms like SQL, JSON, etc.
            sanitized.contains("Exception") || sanitized.contains("Error") ||
            sanitized.contains("at ") || sanitized.contains("Caused by")) {
            return "An unexpected error occurred. Please try again.";
        }

        return sanitized;
    }

    /**
     * Gets a user-friendly error message based on exception type.
     * 
     * @param exception The exception that occurred
     * @return User-friendly error message
     */
    public static String getUserFriendlyMessage(Exception exception) {
        if (exception == null) {
            return "An unexpected error occurred. Please try again.";
        }

        String className = exception.getClass().getSimpleName();
        String message = exception.getMessage();

        // Handle specific exception types
        if (className.contains("ConstraintViolation") || className.contains("DataIntegrity")) {
            return "Invalid data provided. Please check your input and try again.";
        }
        
        if (className.contains("JpaSystem") || className.contains("Hibernate")) {
            return "Unable to save data. Please try again.";
        }
        
        if (className.contains("MethodArgumentNotValid")) {
            return "Please check the form fields and correct any errors.";
        }
        
        if (className.contains("IllegalArgument")) {
            return sanitize(message);
        }

        // For other exceptions, sanitize the message
        return sanitize(message);
    }
}

