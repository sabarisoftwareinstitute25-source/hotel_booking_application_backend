package com.hotelbooking.mobileapp.auth;

import com.hotelbooking.mobileapp.user.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Service for generating, sending, and verifying OTPs.
 */
@Service
@Transactional
public class OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 1; // Changed to 1 minute
    private static final Random random = new Random();

    @Autowired
    private OtpRepository otpRepository;

    /**
     * Generate and send OTP to phone number.
     * In production, integrate with SMS service (Twilio, AWS SNS, etc.)
     * Updates existing OTP if found, otherwise creates new one.
     */
    @Transactional(rollbackFor = Exception.class)
    public String generateAndSendOtp(String phone) {
        return generateAndSendOtp(phone, null, null);
    }

    /**
     * Generate and send OTP to phone number with name and user account.
     * Updates existing OTP if found (keeps same ID, only updates code and attempts),
     * otherwise creates new one.
     */
    @Transactional(rollbackFor = Exception.class)
    public String generateAndSendOtp(String phone, String name, UserAccount user) {
        // Normalize phone number before storing
        String normalizedPhone = normalizePhone(phone);
        logger.info("Starting OTP generation for phone: '{}' (normalized: '{}'), name: '{}'", phone, normalizedPhone, name);
        
        try {
            // Generate 6-digit OTP
            String otpCode = generateOtp();
            logger.debug("Generated OTP code: {}", otpCode);
            
            // Calculate expiry time (1 minute from now)
            Instant now = Instant.now();
            Instant expiresAt = now.plusSeconds(OTP_EXPIRY_MINUTES * 60);
            logger.debug("OTP expires at: {} (in {} minutes)", expiresAt, OTP_EXPIRY_MINUTES);
            
            // Determine OTP ID: Use UserAccount ID if user exists, otherwise use phone-based ID
            String otpId;
            if (user != null && user.getId() != null) {
                // Existing user: OTP ID = UserAccount ID
                otpId = user.getId();
                logger.info("User exists - OTP ID will be UserAccount ID: {}", otpId);
            } else {
                // New user: Use phone as ID (or generate one)
                otpId = "OTP_" + normalizedPhone.replaceAll("[^0-9]", "");
                logger.info("New user - OTP ID will be phone-based: {}", otpId);
            }
            
            // Find existing OTP by ID (for existing users) or by phone (for new users)
            Optional<Otp> existingOtpOpt = Optional.empty();
            if (user != null && user.getId() != null) {
                // Try to find by user ID first
                existingOtpOpt = otpRepository.findById(user.getId());
            }
            if (existingOtpOpt.isEmpty()) {
                // Fallback: find by phone
                existingOtpOpt = otpRepository.findLatestByPhone(normalizedPhone);
            }
            
            Otp otp;
            if (existingOtpOpt.isPresent()) {
                // Update existing OTP (keep same ID, only update code and reset attempts)
                otp = existingOtpOpt.get();
                logger.info("Found existing OTP with ID: {}, updating instead of creating new", otp.getId());
                otp.resetOtp(otpCode, expiresAt);
                if (name != null && !name.isEmpty()) {
                    otp.setName(name);
                }
                if (user != null) {
                    // Link OTP to existing user but DO NOT change primary key (ID).
                    // Hibernate does not allow changing the identifier of a managed entity.
                    otp.setUser(user);
                }
            } else {
                // Create new OTP entity
                logger.info("No existing OTP found, creating new one with ID: {}", otpId);
                otp = new Otp();
                otp.setId(otpId); // Set ID to UserAccount ID or phone-based ID
                otp.setPhone(normalizedPhone);
                otp.setCode(otpCode);
                otp.setExpiresAt(expiresAt);
                otp.setCreatedAt(now);
                otp.setUsed(false);
                otp.setAttempts(0);
                if (name != null && !name.isEmpty()) {
                    otp.setName(name);
                }
                if (user != null) {
                    otp.setUser(user);
                }
            }
            
            logger.info("Saving OTP to database - Phone: {}, Code: {}, ExpiresAt: {}", 
                normalizedPhone, otpCode, expiresAt);
            
            // Save OTP
            Otp savedOtp = otpRepository.save(otp);
            logger.info("OTP entity saved with ID: {}", savedOtp.getId());
            
            // Force flush to database
            otpRepository.flush();
            logger.info("Database flushed - OTP committed to database");
            
            // Verify it was saved by querying immediately
            Optional<Otp> verifyOtp = otpRepository.findById(savedOtp.getId());
            if (verifyOtp.isPresent()) {
                Otp found = verifyOtp.get();
                logger.info("✅ OTP VERIFIED IN DATABASE!");
                logger.info("   ID: {}", found.getId());
                logger.info("   Phone: {}", found.getPhone());
                logger.info("   Code: {}", found.getCode());
                logger.info("   Created: {}", found.getCreatedAt());
                logger.info("   Expires: {}", found.getExpiresAt());
                logger.info("   Used: {}", found.isUsed());
                logger.info("   Attempts: {}", found.getAttempts());
            } else {
                logger.error("❌ CRITICAL: OTP was NOT found in database after save!");
                logger.error("   Expected ID: {}", savedOtp.getId());
                throw new RuntimeException("OTP was not persisted to database");
            }
            
            // Also verify by phone query
            Optional<Otp> phoneOtp = otpRepository.findLatestValidOtpByPhone(normalizedPhone, now);
            if (phoneOtp.isPresent()) {
                logger.info("✅ OTP also found by phone query - ID: {}", phoneOtp.get().getId());
            } else {
                logger.warn("⚠️ OTP not found by phone query (might be timing issue)");
            }
            
            // Console output for visibility
            System.out.println("\n========================================");
            System.out.println("✅ OTP SAVED TO DATABASE");
            System.out.println("========================================");
            System.out.println("Database ID: " + savedOtp.getId());
            System.out.println("Phone: " + normalizedPhone);
            System.out.println("OTP Code: " + otpCode);
            System.out.println("Created At: " + now);
            System.out.println("Expires At: " + expiresAt);
            System.out.println("Expires In: " + OTP_EXPIRY_MINUTES + " minutes");
            System.out.println("========================================\n");
            
            return otpCode;
            
        } catch (Exception e) {
            logger.error("❌ FAILED to generate and save OTP for phone: {}", normalizedPhone, e);
            logger.error("Exception type: {}", e.getClass().getName());
            logger.error("Exception message: {}", e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: {}", e.getCause().getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to generate OTP: " + e.getMessage(), e);
        }
    }

    /**
     * Normalize phone number to consistent format.
     * Handles: "+91 9876543210", "+919876543210", "9876543210", "919876543210", "09876543210"
     * Always returns: "+919876543210" format (no spaces, exactly 13 characters)
     * 
     * This method matches the frontend PhoneUtils.normalizePhone() logic exactly
     */
    public String normalizePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return "";
        
        // Remove ALL whitespace (spaces, tabs, etc.) and special characters
        String cleaned = phone.trim()
            .replaceAll("\\s+", "")  // Remove all spaces
            .replaceAll("-", "")     // Remove dashes
            .replaceAll("[()]", ""); // Remove parentheses
        
        // If starts with +91 and is 13 characters, return as is (target format)
        if (cleaned.startsWith("+91") && cleaned.length() == 13) {
            return cleaned;
        }
        // If starts with 91 and is 12 characters, add +
        if (cleaned.startsWith("91") && cleaned.length() == 12) {
            return "+" + cleaned;
        }
        // If 10 digits without prefix, add +91
        if (cleaned.length() == 10 && !cleaned.startsWith("+") && !cleaned.startsWith("91")) {
            return "+91" + cleaned;
        }
        // If 11 digits starting with 0, remove 0 and add +91
        if (cleaned.length() == 11 && cleaned.startsWith("0")) {
            return "+91" + cleaned.substring(1);
        }
        
        // Default: ensure it starts with + (best effort)
        return cleaned.startsWith("+") ? cleaned : "+" + cleaned;
    }
    
    /**
     * Get phone number variations for lookup (backward compatibility).
     * Since frontend now normalizes phone numbers before sending,
     * we primarily use normalized format. But we still try variations
     * for old OTPs that might have been stored differently.
     */
    private List<String> getPhoneVariations(String phone) {
        List<String> variations = new ArrayList<>();
        String normalized = normalizePhone(phone);
        
        // Primary: normalized format (frontend now sends this)
        variations.add(normalized);
        
        // For backward compatibility: try original format if different
        String trimmed = phone.trim();
        if (!trimmed.equals(normalized)) {
            variations.add(trimmed);
        }
        
        // Only add other variations if normalized is +91 format (for old OTPs)
        if (normalized.startsWith("+91") && normalized.length() == 13) {
            // Without +91 prefix (for old OTPs stored without prefix)
            variations.add(normalized.substring(3));
        }
        
        // Remove duplicates
        return variations.stream().distinct().collect(java.util.stream.Collectors.toList());
    }

    /**
     * Verify OTP for phone number and return the name if available.
     * @return Name from OTP record, or null if not found/verified
     */
    public String verifyOtpAndGetName(String phone, String code) {
        Otp otp = verifyOtpInternal(phone, code);
        return otp != null ? otp.getName() : null;
    }

    /**
     * Verify OTP for phone number.
     */
    public boolean verifyOtp(String phone, String code) {
        return verifyOtpInternal(phone, code) != null;
    }

    /**
     * Internal method to verify OTP and return the Otp entity.
     * Made public so AuthController can access it to get name.
     */
    public Otp verifyOtpInternal(String phone, String code) {
        // Normalize inputs
        String normalizedPhone = normalizePhone(phone);
        String normalizedCode = code.trim();
        
        logger.info("========================================");
        logger.info("VERIFYING OTP");
        logger.info("Original Phone: '{}'", phone);
        logger.info("Normalized Phone: '{}'", normalizedPhone);
        logger.info("Code: '{}'", normalizedCode);
        logger.info("========================================");
        
        try {
            // Get all possible phone number variations (based on normalized phone)
            List<String> phoneVariations = getPhoneVariations(normalizedPhone);
            logger.info("Phone variations to try: {}", phoneVariations);
            
            // Try direct lookup with all variations
            Optional<Otp> directOtpOpt = Optional.empty();
            
            for (String phoneVar : phoneVariations) {
                logger.info("Trying direct lookup - Phone: '{}', Code: '{}'", phoneVar, normalizedCode);
                directOtpOpt = otpRepository.findByPhoneAndCodeAndUsedFalse(phoneVar, normalizedCode);
                if (directOtpOpt.isPresent()) {
                    logger.info("✅ Found OTP with phone variation: '{}'", phoneVar);
                    break;
                }
            }
            
            // If not found with used=false check, try ignoring used status (for debugging)
            if (directOtpOpt.isEmpty()) {
                logger.info("Not found with used=false, trying without used check...");
                for (String phoneVar : phoneVariations) {
                    Optional<Otp> otpIgnoreUsed = otpRepository.findByPhoneAndCodeIgnoreUsed(phoneVar, normalizedCode);
                    if (otpIgnoreUsed.isPresent()) {
                        Otp otp = otpIgnoreUsed.get();
                        logger.warn("Found OTP but status - Used: {}, Expired: {}", otp.isUsed(), otp.isExpired());
                        if (otp.isUsed()) {
                            logger.warn("OTP was already used!");
                        }
                        if (otp.isExpired()) {
                            logger.warn("OTP has expired!");
                        }
                        // Still try to use it if not expired and not used
                        if (!otp.isUsed() && !otp.isExpired()) {
                            directOtpOpt = otpIgnoreUsed;
                            break;
                        }
                    }
                }
            }
            
            // List all OTPs in database for debugging
            List<Otp> allOtps = otpRepository.findAll();
            logger.info("Total OTPs in database: {}", allOtps.size());
            if (!allOtps.isEmpty()) {
                logger.info("All OTPs in database:");
                for (Otp o : allOtps) {
                    String storedPhone = o.getPhone() != null ? o.getPhone() : "null";
                    boolean phoneMatch = storedPhone.equals(normalizedPhone) || 
                                       storedPhone.equals(phone.trim()) ||
                                       (normalizedPhone.startsWith("+91") && storedPhone.equals(normalizedPhone.substring(3)));
                    logger.info("  - ID: {}, Phone: '{}', Code: '{}', Used: {}, Expired: {}, Match: {}", 
                        o.getId(), storedPhone, o.getCode(), o.isUsed(), o.isExpired(), phoneMatch);
                }
            }
            
            if (directOtpOpt.isPresent()) {
                Otp otp = directOtpOpt.get();
                logger.info("Found OTP by direct lookup - ID: {}, Phone: '{}', Code: '{}'", 
                    otp.getId(), otp.getPhone(), otp.getCode());
                
                // Verify code matches exactly (already verified by query, but double-check)
                String storedCode = otp.getCode() != null ? otp.getCode().trim() : "";
                String providedCode = normalizedCode != null ? normalizedCode.trim() : "";
                logger.info("Code verification - Stored: '{}' (length: {}), Provided: '{}' (length: {})", 
                    storedCode, storedCode.length(), providedCode, providedCode.length());
                
                if (!storedCode.equals(providedCode)) {
                    logger.warn("❌ Code mismatch in direct lookup - Stored: '{}', Provided: '{}'", storedCode, providedCode);
                    otp.incrementAttempts();
                    otpRepository.save(otp);
                    otpRepository.flush();
                    return null;
                }
                
                logger.info("✅ Code matches! Checking expiry and status...");
                
                // Check if OTP is expired
                if (otp.isExpired()) {
                    logger.warn("OTP expired - Created: {}, Expires: {}, Now: {}", 
                        otp.getCreatedAt(), otp.getExpiresAt(), Instant.now());
                    return null;
                }
                
                // Check if attempts exceeded
                if (otp.getAttempts() >= 5) {
                    logger.warn("OTP attempts exceeded - Attempts: {}", otp.getAttempts());
                    return null;
                }
                
                // Check if already used
                if (otp.isUsed()) {
                    logger.warn("OTP already used");
                    return null;
                }
                
                // OTP is valid - mark as used
                otp.markAsUsed();
                otpRepository.save(otp);
                otpRepository.flush();
                
                // Mark all other OTPs for this phone as used (try both formats)
                try {
                    otpRepository.markAllAsUsedByPhone(normalizedPhone);
                } catch (Exception e) {
                    logger.debug("Could not mark other OTPs as used: {}", e.getMessage());
                }
                
                logger.info("✅ OTP VERIFIED SUCCESSFULLY!");
                logger.info("   ID: {}", otp.getId());
                logger.info("   Phone: {}", otp.getPhone());
                logger.info("   Code: {}", otp.getCode());
                logger.info("   Name: {}", otp.getName());
                return otp;
            }
            
            // If direct lookup fails, try the query method with all variations
            logger.info("Direct lookup failed, trying query method with all phone variations...");
            Optional<Otp> otpOpt = Optional.empty();
            for (String phoneVar : phoneVariations) {
                logger.info("Trying query method - Phone: '{}'", phoneVar);
                otpOpt = otpRepository.findLatestValidOtpByPhone(phoneVar, Instant.now());
                if (otpOpt.isPresent()) {
                    logger.info("✅ Found OTP with query method using phone variation: '{}'", phoneVar);
                    break;
                }
            }
            
            if (otpOpt.isEmpty()) {
                // Try to find by phone only (ignore code, expiry, used status) for debugging
                logger.info("Query method failed, trying to find by phone only...");
                for (String phoneVar : phoneVariations) {
                    Optional<Otp> phoneOnlyOtp = otpRepository.findLatestByPhoneIgnoreStatus(phoneVar);
                    if (phoneOnlyOtp.isPresent()) {
                        Otp foundOtp = phoneOnlyOtp.get();
                        logger.warn("Found OTP by phone only - ID: {}, Phone: '{}', Code: '{}', Used: {}, Expired: {}, Attempts: {}", 
                            foundOtp.getId(), foundOtp.getPhone(), foundOtp.getCode(), 
                            foundOtp.isUsed(), foundOtp.isExpired(), foundOtp.getAttempts());
                        
                        // Check if code matches
                        String storedCode = foundOtp.getCode() != null ? foundOtp.getCode().trim() : "";
                        if (storedCode.equals(normalizedCode)) {
                            logger.warn("   Code matches! But OTP is Used: {}, Expired: {}", 
                                foundOtp.isUsed(), foundOtp.isExpired());
                            if (!foundOtp.isUsed() && !foundOtp.isExpired()) {
                                // Use this OTP
                                otpOpt = phoneOnlyOtp;
                                break;
                            }
                        } else {
                            logger.warn("   Code mismatch - Expected: '{}', Found: '{}'", normalizedCode, storedCode);
                        }
                    }
                }
            }
            
            if (otpOpt.isEmpty()) {
                // List all OTPs for debugging - try both normalized and original formats
                List<Otp> allOtpsForDebug = otpRepository.findAll();
                List<Otp> matchingOtps = allOtpsForDebug.stream()
                    .filter(o -> {
                        if (o.getPhone() == null) return false;
                        String storedPhone = o.getPhone().trim();
                        String normalizedStored = normalizePhone(storedPhone);
                        return normalizedStored.equals(normalizedPhone) || storedPhone.equals(normalizedPhone);
                    })
                    .toList();
                
                logger.warn("❌ No valid OTP found for phone: '{}' (normalized: '{}')", phone, normalizedPhone);
                logger.warn("   Total OTPs in database: {}", allOtpsForDebug.size());
                logger.warn("   Matching OTPs for this phone: {}", matchingOtps.size());
                
                // Show all OTPs for debugging
                if (!allOtpsForDebug.isEmpty()) {
                    logger.warn("   All OTPs in database:");
                    for (Otp o : allOtpsForDebug) {
                        String storedPhone = o.getPhone() != null ? o.getPhone() : "null";
                        String normalizedStored = normalizePhone(storedPhone);
                        boolean phoneMatches = normalizedStored.equals(normalizedPhone);
                        logger.warn("     - ID: {}, Phone: '{}' (normalized: '{}'), Code: '{}', Used: {}, Expired: {}, Attempts: {}, Match: {}", 
                            o.getId(), storedPhone, normalizedStored, o.getCode(), 
                            o.isUsed(), o.isExpired(), o.getAttempts(), phoneMatches);
                    }
                }
                
                if (!matchingOtps.isEmpty()) {
                    Otp latest = matchingOtps.get(0);
                    logger.warn("   Latest matching OTP - ID: {}, Phone: '{}', Code: '{}', Used: {}, Expired: {}, Attempts: {}", 
                        latest.getId(), latest.getPhone(), latest.getCode(), latest.isUsed(), latest.isExpired(), latest.getAttempts());
                    logger.warn("   Expected code: '{}', Actual code: '{}'", normalizedCode, latest.getCode());
                    logger.warn("   Phone match: '{}' == '{}' ? {}", normalizedPhone, latest.getPhone(), normalizedPhone.equals(latest.getPhone()));
                    
                    // Check why it's not valid
                    if (latest.isUsed()) {
                        logger.warn("   ❌ Reason: OTP already used");
                    }
                    if (latest.isExpired()) {
                        logger.warn("   ❌ Reason: OTP expired");
                    }
                    if (!latest.getCode().equals(normalizedCode)) {
                        logger.warn("   ❌ Reason: Code mismatch");
                    }
                }
                return null;
            }
            
            Otp otp = otpOpt.get();
            logger.info("Found OTP by query - ID: {}, Phone: '{}', Code: '{}'", otp.getId(), otp.getPhone(), otp.getCode());
            
            // Check if OTP code matches (exact match, case-sensitive)
            String storedCode = otp.getCode() != null ? otp.getCode().trim() : "";
            String providedCode = normalizedCode != null ? normalizedCode.trim() : "";
            
            logger.info("Code comparison - Stored: '{}' (length: {}), Provided: '{}' (length: {})", 
                storedCode, storedCode.length(), providedCode, providedCode.length());
            
            if (!storedCode.equals(providedCode)) {
                otp.incrementAttempts();
                otpRepository.save(otp);
                otpRepository.flush();
                logger.warn("❌ OTP code mismatch - Stored: '{}', Provided: '{}' (attempts: {})", 
                    storedCode, providedCode, otp.getAttempts());
                return null;
            }
            
            logger.info("✅ Code matches! Proceeding with verification...");
            
            // Check if OTP is expired
            if (otp.isExpired()) {
                logger.warn("OTP expired - Created: {}, Expires: {}, Now: {}", 
                    otp.getCreatedAt(), otp.getExpiresAt(), Instant.now());
                return null;
            }
            
            // Check if attempts exceeded
            if (otp.getAttempts() >= 5) {
                logger.warn("OTP attempts exceeded - Attempts: {}", otp.getAttempts());
                return null;
            }
            
            // Check if already used
            if (otp.isUsed()) {
                logger.warn("OTP already used");
                return null;
            }
            
            // Mark OTP as used
            otp.markAsUsed();
            otpRepository.save(otp);
            otpRepository.flush();
            
            // Mark all other OTPs for this phone as used
            try {
                otpRepository.markAllAsUsedByPhone(normalizedPhone);
            } catch (Exception e) {
                logger.debug("Could not mark other OTPs as used: {}", e.getMessage());
            }
            
            logger.info("✅ OTP VERIFIED SUCCESSFULLY!");
            logger.info("   ID: {}", otp.getId());
            logger.info("   Phone: {}", otp.getPhone());
            logger.info("   Code: {}", otp.getCode());
            logger.info("   Name: {}", otp.getName());
            return otp;
            
        } catch (Exception e) {
            logger.error("❌ Exception during OTP verification: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Build a user-friendly failure message explaining why OTP is not valid
     * (expired, already used, code mismatch, etc.) for the given phone and code.
     */
    public String buildUserFriendlyFailureMessage(String phone, String code) {
        try {
            String normalizedPhone = normalizePhone(phone);
            String normalizedCode = code != null ? code.trim() : "";

            // Find the latest OTP for this phone, ignoring status
            Optional<Otp> latestOpt = otpRepository.findLatestByPhoneIgnoreStatus(normalizedPhone);
            if (latestOpt.isEmpty()) {
                return "No OTP found for this phone. Please request a new verification code.";
            }

            Otp latest = latestOpt.get();
            String storedCode = latest.getCode() != null ? latest.getCode().trim() : "";

            if (latest.isExpired()) {
                return "This code has expired. Tap 'Resend Code' to get a new OTP.";
            }

            if (latest.isUsed()) {
                return "This code was already used. Tap 'Resend Code' to receive a new OTP.";
            }

            if (!storedCode.equals(normalizedCode)) {
                return "Incorrect code. Please enter the latest 6‑digit code we sent to your phone.";
            }

            // Fallback generic message
            return "Invalid OTP. Please enter the latest 6‑digit code we sent to your phone.";
        } catch (Exception e) {
            logger.error("Error building user-friendly OTP failure message: {}", e.getMessage(), e);
            return "Invalid or expired OTP. Please request a new verification code.";
        }
    }

    /**
     * Check if phone has a valid OTP (not expired, not used).
     */
    public boolean hasValidOtp(String phone) {
        Optional<Otp> otpOpt = otpRepository.findLatestValidOtpByPhone(phone, Instant.now());
        return otpOpt.isPresent() && otpOpt.get().isValid();
    }

    /**
     * Generate random 6-digit OTP.
     */
    private String generateOtp() {
        int min = (int) Math.pow(10, OTP_LENGTH - 1); // 100000
        int max = (int) Math.pow(10, OTP_LENGTH) - 1; // 999999
        int otp = min + random.nextInt(max - min + 1);
        return String.valueOf(otp);
    }

    /**
     * Cleanup expired OTPs (DISABLED BY REQUEST).
     *
     * The user wants to keep all OTP records in the database (no automatic delete),
     * so this scheduled task is now a no-op and does not remove any rows.
     */
    @Scheduled(fixedRate = 3600000) // 1 hour
    public void cleanupExpiredOtps() {
        // Intentionally disabled: do not delete any OTP rows automatically.
        logger.debug("cleanupExpiredOtps is disabled — no OTP rows are deleted.");
    }
}

