package com.hotelbooking.mobileapp.user;

import com.hotelbooking.mobileapp.config.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpTestService {

    @Autowired
    private TwilioConfig twilioConfig;

    public void sendTestOtp(String mobile) {
        String otp = "123456"; // Fixed OTP for testing
        twilioConfig.sendOtpSms(mobile, "Your OTP is: " + otp);
        System.out.println("OTP sent to " + mobile);
    }
}