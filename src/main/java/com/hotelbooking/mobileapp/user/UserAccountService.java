package com.hotelbooking.mobileapp.user;

import com.hotelbooking.mobileapp.config.TwilioConfig;
import com.hotelbooking.mobileapp.otp.MobileOtp;
import com.hotelbooking.mobileapp.otp.MobileOtpRepository;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private MobileOtpRepository mobileOtpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TwilioConfig twilioConfig;

    // ✅ STEP 1: Send OTP
    public void sendOtp(String fullName, String mobile) {

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        // ✅ FIXED: use mobile (not mobileNumber)
        MobileOtp mobileOtp = mobileOtpRepository.findByMobile(mobile)
                .orElse(new MobileOtp());

        mobileOtp.setFullName(fullName);
        mobileOtp.setMobile(mobile); // ✅ FIXED
        mobileOtp.setOtp(otp);
        mobileOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        mobileOtp.setVerified(false);

        mobileOtpRepository.save(mobileOtp);

        // Send OTP via Twilio
        twilioConfig.sendOtpSms(mobile, "Your OTP is: " + otp);
    }

    // ✅ STEP 2: Verify OTP
    public boolean verifyOtp(String mobile, String otp) {

        // ✅ FIXED
        Optional<MobileOtp> record =
                mobileOtpRepository.findByMobileAndOtp(mobile, otp);

        if (record.isPresent() &&
                record.get().getExpiryTime().isAfter(LocalDateTime.now())) {

            MobileOtp mobileOtp = record.get();
            mobileOtp.setVerified(true);
            mobileOtpRepository.save(mobileOtp);
            return true;
        }

        return false;
    }

    // ✅ STEP 3: Complete Registration
    public void completeRegistration(UserAccount userAccount) {

        // ✅ FIXED
        Optional<MobileOtp> otpRecord =
                mobileOtpRepository.findByMobile(userAccount.getMobile());

        if (otpRecord.isEmpty() || !otpRecord.get().isVerified()) {
            throw new RuntimeException("Mobile not verified yet!");
        }

        // ✅ FIXED
        if (userAccountRepository
                .findByMobile(userAccount.getMobile())
                .isPresent()) {

            throw new RuntimeException("User already registered with this mobile!");
        }

        // Generate ID
        userAccount.setUserId(idGeneratorService.generateUserId());

        // Hash password
        userAccount.setPassword(
                passwordEncoder.encode(userAccount.getPassword())
        );

        userAccount.setActive(true);

        userAccountRepository.save(userAccount);
    }
}