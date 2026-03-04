package com.hotelbooking.mobileapp.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwilioConfig {


    @Value("${twilio.phoneNumber}")
    private String fromPhone;

    // ✅ Initialize Twilio client after bean creation
//    @PostConstruct
//    public void init() {
//        System.out.println("Initializing Twilio with SID: " + accountSid);
//        Twilio.init(accountSid, authToken);
//    }

    // ✅ Send OTP SMS
    public void sendOtpSms(String toMobile, String messageBody) {

        // Format Indian mobile numbers
        String formattedMobile = toMobile.startsWith("+91") ? toMobile : "+91" + toMobile;

        System.out.println("Sending OTP to: " + formattedMobile);

        try {
            Message message = Message.creator(
                    new PhoneNumber(formattedMobile),
                    new PhoneNumber(fromPhone),
                    messageBody
            ).create();

            System.out.println("OTP sent successfully. Twilio SID: " + message.getSid());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send OTP: " + e.getMessage());
        }
    }
}