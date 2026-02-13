package com.hotelbooking.mobileapp.auth;

import com.hotelbooking.mobileapp.user.*;
import com.hotelbooking.mobileapp.util.TokenUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Authentication Controller matching frontend API endpoints.
 * Endpoints:
 * POST /api/auth/login
 * POST /api/auth/signup
 * POST /api/auth/forgot-password
 * POST /api/auth/reset-password
 * POST /api/auth/verify-otp
 * POST /api/auth/resend-otp
 * 
 * Phone-based signup endpoints:
 * POST /api/auth/send-verification-code (Step 1: Send OTP)
 * POST /api/auth/verify-phone-otp (Step 2: Verify OTP)
 * POST /api/auth/complete-signup (Step 3: Complete signup with password)
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * POST /api/auth/login
     * Frontend expects: LoginResponse {token, user}
     * <p>
     * Security: Only allows login with credentials that exist in the database.
     * Validates both user existence and password match.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Validate input - ensure email/phone and password are provided
            String identifier = request.getEmail();
            String password = request.getPassword();

            // Additional validation beyond @Valid annotations
            if (identifier == null || identifier.trim().isEmpty()) {
                logger.warn("Login attempt with empty email/phone");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new LoginResponse(null, null));
            }

            if (password == null || password.trim().isEmpty()) {
                logger.warn("Login attempt with empty password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new LoginResponse(null, null));
            }

            identifier = identifier.trim();
            password = password.trim();

            // Validate identifier format (email or phone)
            boolean isValidEmail = identifier.contains("@") &&
                    identifier.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
            boolean isValidPhone = identifier.matches("^[+]?[0-9]{10,15}$") ||
                    identifier.matches("^[0-9]{10}$");

            if (!isValidEmail && !isValidPhone) {
                logger.warn("Login attempt with invalid identifier format: {}", identifier.substring(0, Math.min(10, identifier.length())));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new LoginResponse(null, null));
            }

            // Validate password length and basic requirements
            if (password.length() < 6) {
                logger.warn("Login attempt with password too short");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new LoginResponse(null, null));
            }

            if (password.length() > 100) {
                logger.warn("Login attempt with password too long");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new LoginResponse(null, null));
            }

            logger.info("Login attempt for identifier: {}", identifier.contains("@") ? identifier : "phone");

            // Check if user exists in database - REQUIRED: user must exist in database
            Optional<UserAccount> userOpt = Optional.empty();

            if (identifier.contains("@")) {
                // It's an email - lookup in database
                userOpt = userAccountService.findByEmail(identifier.toLowerCase());
            } else {
                // It's a phone number - lookup in database
                userOpt = userAccountService.findByPhone(identifier);
            }

            // SECURITY: Reject if user not found in database
            if (userOpt.isEmpty()) {
                logger.warn("Login failed: User not found in database for identifier: {}",
                        identifier.contains("@") ? identifier : "phone");
                // Return explicit error response - do not allow login
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(null, null)); // Explicitly return null to indicate failure
            }

            UserAccount userAccount = userOpt.get();

            // SECURITY: Verify password matches database - REQUIRED: password must match
            // Check if user has a password set (should not be null or empty)
            if (userAccount.getPassword() == null || userAccount.getPassword().isEmpty()) {
                logger.warn("Login failed: User {} has no password set", userAccount.getId());
                // Return explicit error response - do not allow login
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(null, null)); // Explicitly return null to indicate failure
            }

            // Verify password using BCrypt - compares provided password with hashed password in database
            boolean passwordMatches = passwordEncoder.matches(password, userAccount.getPassword());

            if (!passwordMatches) {
                logger.warn("Login failed: Password mismatch for user: {}",
                        identifier.contains("@") ? identifier : "phone");
                // Return explicit error response - do not allow login
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(null, null)); // Explicitly return null to indicate failure
            }

            // Only generate token if user exists in database AND password matches
            logger.info("Login successful for user: {}", userAccount.getId());
            String token = TokenUtil.generateToken(userAccount.getId());
            LoginResponse response = new LoginResponse(token, userAccount);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid login request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(null, null));
        } catch (Exception e) {
            logger.error("Login error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, null));
        }
    }

    /**
     * Handle validation errors from @Valid annotations
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<LoginResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        logger.warn("Login validation failed: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new LoginResponse(null, null));
    }

    /**
     * POST /api/auth/signup
     * Frontend expects: SignupResponse {message, user?}
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        try {
            if (userAccountService.existsByEmail(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignupResponse("Email already exists"));
            }

            UserAccount userAccount = new UserAccount();
            userAccount.setFullName(request.getName());
            userAccount.setEmail(request.getEmail());
            // Encrypt password before saving
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            UserAccount savedUserAccount = userAccountService.createUserAccount(userAccount);
            SignupResponse response = new SignupResponse("Signup successful", savedUserAccount);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SignupResponse("Signup failed: " + e.getMessage()));
        }
    }

    /**
     * POST /api/auth/forgot-password
     * Frontend expects: {message: string}
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            response.put("message", "Email is required");
            return ResponseEntity.badRequest().body(response);
        }

        // In production, send OTP email here
        response.put("message", "OTP sent to email");
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/auth/reset-password
     * Frontend expects: {message: string}
     */
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (email == null || otp == null || newPassword == null) {
            response.put("message", "Email, OTP, and new password are required");
            return ResponseEntity.badRequest().body(response);
        }

        // In production, verify OTP here
        var userOpt = userAccountService.findByEmail(email);
        if (userOpt.isPresent()) {
            UserAccount userAccount = userOpt.get();
            userAccount.setPassword(newPassword); // In production, hash this
            userAccountService.updateUserAccount(userAccount);
            response.put("message", "Password reset successful");
            return ResponseEntity.ok(response);
        }

        response.put("message", "User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * POST /api/auth/verify-otp
     * Frontend expects: {message: string}
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String email = request.get("email");
        String otp = request.get("otp");

        if (email == null || otp == null) {
            response.put("message", "Email and OTP are required");
            return ResponseEntity.badRequest().body(response);
        }

        // In production, verify OTP here
        response.put("message", "OTP verified successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/auth/resend-otp
     * Frontend expects: {message: string}
     */
    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, String>> resendOtp(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            response.put("message", "Email is required");
            return ResponseEntity.badRequest().body(response);
        }

        // In production, resend OTP email here
        response.put("message", "OTP resent to email");
        return ResponseEntity.ok(response);
    }

    // ========================================
    // Phone-based Signup Endpoints
    // ========================================

    /**
     * POST /api/auth/send-verification-code
     * Step 1: Send OTP to phone number
     * Request: {fullName: string, phone: string}
     * Response: {success: boolean, message: string, phone: string}
     */
    @PostMapping("/send-verification-code")
    public ResponseEntity<SendOtpResponse> sendVerificationCode(@Valid @RequestBody PhoneSignupRequest request) {
        try {
            String phone = request.getPhone();
            String fullName = request.getFullName();

            // Validate phone number
            if (phone == null || phone.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new SendOtpResponse(false, "Phone number is required", null));
            }

            // Normalize phone number using OtpService normalization
            String normalizedPhone = otpService.normalizePhone(phone);

            // Check if user exists by phone (for existing users, allow OTP for login)
            UserAccount existingUser = userAccountService.findByPhone(normalizedPhone).orElse(null);

            // Generate and send OTP with name and user account
            // If user exists, OTP ID will be user account ID
            // If user doesn't exist, OTP ID will be phone-based
            otpService.generateAndSendOtp(normalizedPhone, fullName, existingUser);

            return ResponseEntity.ok(new SendOtpResponse(true, "Verification code sent successfully", normalizedPhone));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SendOtpResponse(false, "Failed to send verification code: " + e.getMessage(), null));
        }
    }

    /**
     * POST /api/auth/verify-phone-otp
     * Step 2: Verify OTP
     * Request: {phone: string, otp: string}
     * Response: {success: boolean, message: string, verified: boolean}
     */
    @PostMapping("/verify-phone-otp")
    public ResponseEntity<VerifyOtpResponse> verifyPhoneOtp(@Valid @RequestBody VerifyOtpRequest request) {
        try {
            String phone = request.getPhone();
            String otp = request.getOtp();

            if (phone == null || otp == null) {
                return ResponseEntity.badRequest()
                        .body(new VerifyOtpResponse(false, "Phone and OTP are required", false));
            }

            String normalizedOtp = otp.trim();

            logger.info("Verifying OTP - Original phone: '{}', OTP: '{}'", phone, normalizedOtp);

            // Normalize phone number
            String normalizedPhone = otpService.normalizePhone(phone);

            // Verify OTP and get the Otp entity (contains name)
            com.hotelbooking.mobileapp.auth.Otp verifiedOtp = otpService.verifyOtpInternal(normalizedPhone, normalizedOtp);

            if (verifiedOtp != null) {
                String name = verifiedOtp.getName();
                logger.info("OTP verified successfully - Name: '{}'", name);

                // Check if user exists by UserAccount ID (OTP ID = UserAccount ID for existing users)
                java.util.Optional<UserAccount> existingUser =
                        java.util.Optional.empty();

                String otpId = verifiedOtp.getId();
                logger.info("Checking user existence by OTP ID (UserAccount ID): '{}'", otpId);

                // Check if OTP ID matches a UserAccount ID
                if (otpId != null && !otpId.isEmpty() && !otpId.startsWith("OTP_")) {
                    // OTP ID is not phone-based, so it should be a UserAccount ID
                    existingUser = userAccountService.findById(otpId);
                    if (existingUser.isPresent()) {
                        logger.info("✅ Found user by OTP ID (UserAccount ID): {}", otpId);
                    } else {
                        logger.info("❌ No user found with ID: {}", otpId);
                    }
                }

                // Also check if OTP has a linked user
                if (existingUser.isEmpty() && verifiedOtp.getUser() != null) {
                    existingUser = java.util.Optional.of(verifiedOtp.getUser());
                    logger.info("✅ Found user via OTP linked user relationship - UserId: {}", existingUser.get().getId());
                }

                boolean userExists = existingUser.isPresent();
                String userId = existingUser.map(UserAccount::getId).orElse(null);

                logger.info("========================================");
                logger.info("User exists check (by UserAccount ID):");
                logger.info("  OTP ID: '{}'", otpId);
                logger.info("  User Exists: {}", userExists);
                logger.info("  User ID: {}", userId);
                logger.info("  OTP has linked user: {}", verifiedOtp.getUser() != null);
                if (verifiedOtp.getUser() != null) {
                    logger.info("  Linked User ID: {}", verifiedOtp.getUser().getId());
                }
                logger.info("========================================");

                return ResponseEntity.ok(new VerifyOtpResponse(
                        true,
                        "OTP verified successfully",
                        true,
                        name,
                        userExists,
                        userId
                ));
            } else {
                // OTP not valid – build a user-friendly message explaining why
                String friendlyMessage = otpService.buildUserFriendlyFailureMessage(phone, otp);

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new VerifyOtpResponse(false, friendlyMessage, false, null, false, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new VerifyOtpResponse(false, "Failed to verify OTP: " + e.getMessage(), false));
        }
    }

    /**
     * POST /api/auth/complete-signup
     * Step 3: Complete signup with email and password after OTP verification
     * Request: {phone: string, otp: string, email: string, password: string}
     * Response: SignupResponse {message: string, user?: User}
     */
    @PostMapping("/complete-signup")
    public ResponseEntity<SignupResponse> completeSignup(@Valid @RequestBody CompleteSignupRequest request) {
        try {
            String phone = request.getPhone();
            String otp = request.getOtp();
            String email = request.getEmail();
            String password = request.getPassword();

            // Verify OTP first
            if (!otpService.verifyOtp(phone, otp)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new SignupResponse("Invalid or expired OTP. Please request a new code."));
            }

            // Use normalized phone
            String normalizedPhone = phone.trim().replaceAll("\\s+", "");

            // Check if phone already exists
            if (userAccountService.existsByPhone(normalizedPhone)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignupResponse("Phone number already registered"));
            }

            // Check if email already exists
            if (userAccountService.existsByEmail(email)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignupResponse("Email already exists"));
            }

            // Create user account
            UserAccount userAccount = new UserAccount();
            userAccount.setFullName(request.getFullName());
            userAccount.setEmail(email);
            // Encrypt password before saving
            if (password != null && !password.isEmpty()) {
                userAccount.setPassword(passwordEncoder.encode(password));
            }

            UserAccount savedUserAccount = userAccountService.createUserAccount(userAccount);
            // Token generated but not needed for signup response (frontend doesn't expect it)

            SignupResponse response = new SignupResponse("Signup successful", savedUserAccount);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SignupResponse("Signup failed: " + e.getMessage()));
        }
    }

    /**
     * POST /api/auth/resend-phone-otp
     * Resend OTP to phone number
     * Request: {phone: string}
     * Response: {success: boolean, message: string, phone: string}
     */
    @PostMapping("/resend-phone-otp")
    public ResponseEntity<SendOtpResponse> resendPhoneOtp(@RequestBody Map<String, String> request) {
        try {
            String phone = request.get("phone");

            if (phone == null || phone.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new SendOtpResponse(false, "Phone number is required", null));
            }

            // Normalize phone number
            String normalizedPhone = otpService.normalizePhone(phone);

            // Check if user exists by phone (for existing users, allow OTP for login)
            UserAccount existingUser = userAccountService.findByPhone(normalizedPhone).orElse(null);

            // Generate and send new OTP with name and user account
            otpService.generateAndSendOtp(normalizedPhone, null, existingUser);

            return ResponseEntity.ok(new SendOtpResponse(true, "Verification code resent successfully", normalizedPhone));
        } catch (Exception e) {
            logger.error("Error resending OTP for phone: {}", request.get("phone"), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SendOtpResponse(false, "Failed to resend verification code: " + e.getMessage(), null));
        }
    }

    // 🔥 ADD THIS METHOD
    @GetMapping("/test-mail")
    public String testMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("balasabarimobileapp@gmail.com"); // put your real gmail
        message.setSubject("Test Mail");
        message.setText("If you receive this, SMTP works.");
        mailSender.send(message);
        return "Test mail sent";
    }
}

