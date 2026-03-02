package com.hotelbooking.mobileapp.booking;

import com.hotelbooking.mobileapp.user.UserAccount;
import com.hotelbooking.mobileapp.user.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserAccountRepository userAccountRepository;

    public Booking createBooking(String userId, Booking booking) {

        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        booking.setUser(user);
        booking.setStatus("CONFIRMED");

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void cancelBooking(String id) {
        Booking booking = getBookingById(id);
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }
}