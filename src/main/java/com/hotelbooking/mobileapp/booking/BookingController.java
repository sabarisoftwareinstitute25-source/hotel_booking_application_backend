package com.hotelbooking.mobileapp.booking;

import com.hotelbooking.mobileapp.user.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Booking Controller matching frontend API endpoints.
 * Endpoints:
 * POST /api/bookings
 * GET /api/bookings
 * GET /api/bookings/user/{userId}
 * GET /api/bookings/{id}
 * POST /api/bookings/cancel
 */
@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    /**
     * POST /api/bookings
     * Frontend sends: {user: {id: string}, hotel: {id: string}, checkInDate, checkOutDate, totalPrice, status}
     * Frontend expects: Booking (wrapped in ApiResponse by frontend)
     */
    @PostMapping
    @SuppressWarnings("unchecked")
    public ResponseEntity<Booking> create(@RequestBody Map<String, Object> request) {
        try {
            // Extract nested user and hotel objects
            Map<String, Object> userMap = (Map<String, Object>) request.get("user");
            Map<String, Object> hotelMap = (Map<String, Object>) request.get("hotel");
            
            if (userMap == null || hotelMap == null) {
                return ResponseEntity.badRequest().build();
            }
            
            String userId = (String) userMap.get("id");
            String hotelId = (String) hotelMap.get("id");
            
            if (userId == null || hotelId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Create booking object
            Booking booking = new Booking();
            
            // Set user and hotel (service will load full objects)
            UserAccount userAccount = new UserAccount();
            userAccount.setId(userId);
            booking.setUser(userAccount);
            
            com.hotelbooking.mobileapp.hotel.Hotel hotel = new com.hotelbooking.mobileapp.hotel.Hotel();
            hotel.setId(hotelId);
            booking.setHotel(hotel);
            
            // Set dates
            if (request.get("checkInDate") != null) {
                booking.setCheckInDate(java.time.LocalDate.parse((String) request.get("checkInDate")));
            }
            if (request.get("checkOutDate") != null) {
                booking.setCheckOutDate(java.time.LocalDate.parse((String) request.get("checkOutDate")));
            }
            
            // Set price
            if (request.get("totalPrice") != null) {
                Object priceObj = request.get("totalPrice");
                if (priceObj instanceof Number) {
                    booking.setTotalPrice(java.math.BigDecimal.valueOf(((Number) priceObj).doubleValue()));
                }
            }
            
            // Set status
            String status = request.get("status") != null ? (String) request.get("status") : "PENDING";
            booking.setStatus(status);
            
            // Create booking
            Booking created = bookingService.create(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * GET /api/bookings
     * Frontend expects: List<Booking> (wrapped in ApiResponse by frontend)
     */
    @GetMapping
    public ResponseEntity<List<Booking>> list() {
        try {
            List<Booking> bookings = bookingService.findAll();
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/bookings/user/{userId}
     * Frontend expects: List<Booking> (wrapped in ApiResponse by frontend)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getByUser(@PathVariable String userId) {
        try {
            List<Booking> bookings = bookingService.findByUserId(userId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/bookings/{id}
     * Frontend expects: Booking (wrapped in ApiResponse by frontend)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable String id) {
        try {
            Optional<Booking> bookingOpt = bookingService.findById(id);
            return bookingOpt.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/bookings/cancel
     * Frontend sends: {bookingId: string}
     * Frontend expects: void (wrapped in ApiResponse by frontend)
     */
    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelBooking(@RequestBody Map<String, String> request) {
        try {
            String bookingId = request.get("bookingId");
            if (bookingId == null || bookingId.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

