package com.hotelbooking.mobileapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MobileappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileappApplication.class, args);
    }
}

