package com.hotelbooking.mobileapp.util;

import java.util.UUID;

/**
 * Utility class for generating authentication tokens.
 * For production, consider using JWT tokens.
 */
public class TokenUtil {
    
    /**
     * Generate a simple authentication token.
     * In production, use JWT tokens with proper expiration and signing.
     */
    public static String generateToken() {
        return "token_" + UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * Generate a token with user ID prefix for easier identification.
     */
    public static String generateToken(String userId) {
        return "token_" + userId + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}

