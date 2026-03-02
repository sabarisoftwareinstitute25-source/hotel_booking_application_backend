package com.hotelbooking.mobileapp.util;

import com.hotelbooking.mobileapp.user.UserAccount;
import com.hotelbooking.mobileapp.user.UserAccountRepository;
import com.hotelbooking.mobileapp.hotel.Vendor;
import com.hotelbooking.mobileapp.hotel.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class IdGeneratorService {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static int hotelCounter = 1;
    private static int bookingCounter = 1;
    
    @Autowired(required = false)
    private UserAccountRepository userAccountRepository;
    
    @Autowired(required = false)
    private VendorRepository vendorRepository;
    
    /**
     * Generates a user account ID in format: EIHyyyyCmm000001
     * Format: EIH (prefix) + yyyy (year) + C + mm (month) + 000001 (6-digit sequential number)
     * Example: EIH2026C01000001
     */
    public String generateUserId() {
        // If repository is not available, use simple counter (fallback)
        if (userAccountRepository == null) {
            YearMonth currentYearMonth = YearMonth.now();
            String year = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy"));
            String month = currentYearMonth.format(DateTimeFormatter.ofPattern("MM"));
            return "EIH" + year + "C" + month + "000001";
        }
        
        // Get current year and month
        YearMonth currentYearMonth = YearMonth.now();
        String year = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy"));
        String month = currentYearMonth.format(DateTimeFormatter.ofPattern("MM"));
        String prefix = "EIH" + year + "C" + month;
        
        // Find the highest sequence number for this month
        int maxSequence = 0;
        List<UserAccount> allUsers = userAccountRepository.findAll();
        
        // Pattern: EIHyyyyCmm000001
        Pattern pattern = Pattern.compile("^EIH(\\d{4})C(\\d{2})(\\d{6})$");
        
        for (UserAccount user : allUsers) {
            String userId = user.getId();
            if (userId != null && userId.startsWith("EIH")) {
                Matcher matcher = pattern.matcher(userId);
                if (matcher.matches()) {
                    try {
                        String userYear = matcher.group(1);
                        String userMonth = matcher.group(2);
                        String currentYear = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy"));
                        String currentMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("MM"));
                        
                        // Only process if it's the same year and month
                        if (userYear.equals(currentYear) && userMonth.equals(currentMonth)) {
                            int sequence = Integer.parseInt(matcher.group(3));
                            if (sequence > maxSequence) {
                                maxSequence = sequence;
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid format
                    }
                }
            }
        }
        
        // Increment sequence number
        int nextSequence = maxSequence + 1;
        
        // Format as 6-digit number (000001, 000002, etc.)
        String sequenceStr = String.format("%06d", nextSequence);
        
        return prefix + sequenceStr;
    }
    
    public String generateHotelId() {
        String date = LocalDate.now().format(DATE_FORMAT);
        String counter = String.format("%04d", hotelCounter++);
        return "H" + date + counter;
    }
    
    public String generateBookingId() {
        String date = LocalDate.now().format(DATE_FORMAT);
        String counter = String.format("%04d", bookingCounter++);
        return "B" + date + counter;
    }
    
    /**
     * Generates a vendor ID for vendors table in format: EIHyyyyVmm0001
     * Format: EIH (prefix) + yyyy (year) + V + mm (month) + 0001 (sequential number)
     * Example: EIH2026V010001
     * Used for Account Details (vendors table)
     */
    public String generateVendorRegistrationId() {
        // If repository is not available, use simple counter (fallback)
        if (vendorRepository == null) {
            YearMonth currentYearMonth = YearMonth.now();
            String year = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy"));
            String month = currentYearMonth.format(DateTimeFormatter.ofPattern("MM"));
            return "EIH" + year + "V" + month + "0001";
        }
        
        // Get current year and month
        YearMonth currentYearMonth = YearMonth.now();
        String year = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy"));
        String month = currentYearMonth.format(DateTimeFormatter.ofPattern("MM"));
        String prefix = "EIH" + year + "V" + month;
        
        // Find the highest sequence number for this month
        int maxSequence = 0;
        List<Vendor> allVendors = vendorRepository.findAll();
        
        // Pattern: EIHyyyyVmm0001
        Pattern pattern = Pattern.compile("^EIH(\\d{4})V(\\d{2})(\\d{4})$");
        
        for (Vendor vendor : allVendors) {
            String vendorId = vendor.getVendorId();
            if (vendorId != null && vendorId.startsWith("EIH")) {
                Matcher matcher = pattern.matcher(vendorId);
                if (matcher.matches()) {
                    try {
                        String vendorYear = matcher.group(1);
                        String vendorMonth = matcher.group(2);
                        String currentYear = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy"));
                        String currentMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("MM"));
                        
                        // Only process if it's the same year and month
                        if (vendorYear.equals(currentYear) && vendorMonth.equals(currentMonth)) {
                            int sequence = Integer.parseInt(matcher.group(3));
                            if (sequence > maxSequence) {
                                maxSequence = sequence;
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid format
                    }
                }
            }
        }
        
        // Increment sequence number
        int nextSequence = maxSequence + 1;
        
        // Format as 4-digit number (0001, 0002, etc.)
        String sequenceStr = String.format("%04d", nextSequence);
        
        return prefix + sequenceStr;
    }
    
    /**
     * Generates a hotel vendor registration ID based on property type.
     * Format: HVyyyyMM0001, VVyyyyMM0001, AVyyyyMM0001, RVyyyyMM0001
     * Used for full registration form (hotel_vendors table)
     */
    public String generateHotelVendorRegistrationId(String propertyType) {
        YearMonth currentYearMonth = YearMonth.now();
        String yearMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
        
        // Determine prefix based on property type
        String prefix;
        if (propertyType == null || propertyType.trim().isEmpty()) {
            prefix = "HV"; // Default to Hotel
        } else {
            switch (propertyType.toUpperCase()) {
                case "HOTEL":
                    prefix = "HV";
                    break;
                case "VILLA":
                    prefix = "VV";
                    break;
                case "APARTMENT":
                    prefix = "AV";
                    break;
                case "RESORT":
                    prefix = "RV";
                    break;
                default:
                    prefix = "HV"; // Default to Hotel
            }
        }
        
        prefix = prefix + yearMonth;
        
        // Simple counter-based approach (can be enhanced with database lookup)
        int nextSequence = 1; // This should be fetched from database in production
        
        String sequenceStr = String.format("%04d", nextSequence);
        return prefix + sequenceStr;
    }
}

