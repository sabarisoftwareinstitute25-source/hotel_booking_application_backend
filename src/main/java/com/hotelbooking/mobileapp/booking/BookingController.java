package com.hotelbooking.mobileapp.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    public Booking createBooking(
            @RequestParam String userId,
            @RequestBody Booking booking
    ) {
        return bookingService.createBooking(userId, booking);
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable String id) {
        bookingService.cancelBooking(id);
        return "Booking cancelled successfully";
    }
}