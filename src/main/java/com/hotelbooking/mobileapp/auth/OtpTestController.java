package com.hotelbooking.mobileapp.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test controller for OTP functionality.
 * This controller helps test OTP generation and storage.
 */
@RestController
@RequestMapping("/api/test/otp")
@CrossOrigin(origins = "*")
public class OtpTestController {

    private static final Logger logger = LoggerFactory.getLogger(OtpTestController.class);

    @Autowired
    private OtpService otpService;

    @Autowired
    private OtpRepository otpRepository;

    /**
     * POST /api/test/otp/send-dummy
     * Send a dummy OTP to test the system.
     */
    @PostMapping("/send-dummy")
    public ResponseEntity<Map<String, Object>> sendDummyOtp(@RequestBody(required = false) Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Use provided phone or default test phone
            String phone = (request != null && request.containsKey("phone")) 
                ? request.get("phone") 
                : "+919876543210";
            
            logger.info("========================================");
            logger.info("TEST: Generating dummy OTP for phone: {}", phone);
            logger.info("========================================");
            
            // Check database before
            long countBefore = otpRepository.count();
            logger.info("OTPs in database BEFORE: {}", countBefore);
            
            // Generate and send OTP
            String otpCode = otpService.generateAndSendOtp(phone);
            logger.info("OTP service returned code: {}", otpCode);
            
            // Wait a moment for database commit
            Thread.sleep(100);
            
            // Check database after
            long countAfter = otpRepository.count();
            logger.info("OTPs in database AFTER: {}", countAfter);
            
            // Verify it was saved by querying the database
            List<Otp> allOtps = otpRepository.findAll();
            logger.info("Total OTPs found: {}", allOtps.size());
            
            Otp latestOtp = allOtps.stream()
                .filter(o -> o.getPhone() != null && o.getPhone().equals(phone))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .findFirst()
                .orElse(null);
            
            if (latestOtp != null) {
                response.put("success", true);
                response.put("message", "Dummy OTP generated and stored successfully");
                response.put("phone", phone);
                response.put("otp", otpCode);
                response.put("databaseId", latestOtp.getId());
                response.put("storedCode", latestOtp.getCode());
                response.put("createdAt", latestOtp.getCreatedAt());
                response.put("expiresAt", latestOtp.getExpiresAt());
                response.put("used", latestOtp.isUsed());
                response.put("attempts", latestOtp.getAttempts());
                response.put("totalOtpsInDb", allOtps.size());
                response.put("countBefore", countBefore);
                response.put("countAfter", countAfter);
                
                logger.info("✅ OTP successfully stored in database with ID: {}", latestOtp.getId());
                logger.info("✅ Response: {}", response);
            } else {
                response.put("success", false);
                response.put("message", "OTP generated but not found in database");
                response.put("phone", phone);
                response.put("otp", otpCode);
                response.put("totalOtpsInDb", allOtps.size());
                response.put("countBefore", countBefore);
                response.put("countAfter", countAfter);
                response.put("allPhones", allOtps.stream().map(Otp::getPhone).toList());
                
                logger.error("❌ OTP generated but not found in database");
                logger.error("   Total OTPs in DB: {}", allOtps.size());
                logger.error("   All phones in DB: {}", allOtps.stream().map(Otp::getPhone).toList());
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to generate dummy OTP: {}", e.getMessage(), e);
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to generate OTP: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("stackTrace", e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/test/otp/list
     * List all OTPs in the database.
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listAllOtps() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Otp> otps = otpRepository.findAll();
            
            response.put("success", true);
            response.put("totalCount", otps.size());
            response.put("otps", otps.stream().map(otp -> {
                Map<String, Object> otpMap = new HashMap<>();
                otpMap.put("id", otp.getId());
                otpMap.put("phone", otp.getPhone());
                otpMap.put("code", otp.getCode());
                otpMap.put("createdAt", otp.getCreatedAt());
                otpMap.put("expiresAt", otp.getExpiresAt());
                otpMap.put("used", otp.isUsed());
                otpMap.put("attempts", otp.getAttempts());
                otpMap.put("isExpired", otp.isExpired());
                otpMap.put("isValid", otp.isValid());
                return otpMap;
            }).toList());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to list OTPs: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "Failed to list OTPs: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/test/otp/verify/{phone}/{code}
     * Verify an OTP.
     */
    @GetMapping("/verify/{phone}/{code}")
    public ResponseEntity<Map<String, Object>> verifyOtp(
            @PathVariable String phone,
            @PathVariable String code) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isValid = otpService.verifyOtp(phone, code);
            
            response.put("success", true);
            response.put("phone", phone);
            response.put("code", code);
            response.put("verified", isValid);
            response.put("message", isValid ? "OTP verified successfully" : "Invalid or expired OTP");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to verify OTP: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "Failed to verify OTP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/test/otp/health
     * Check OTP service health.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long totalOtps = otpRepository.count();
            long validOtps = otpRepository.findAll().stream()
                .filter(Otp::isValid)
                .count();
            long expiredOtps = otpRepository.findAll().stream()
                .filter(Otp::isExpired)
                .count();
            
            response.put("success", true);
            response.put("status", "healthy");
            response.put("totalOtps", totalOtps);
            response.put("validOtps", validOtps);
            response.put("expiredOtps", expiredOtps);
            response.put("timestamp", Instant.now());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Health check failed: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("status", "unhealthy");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

