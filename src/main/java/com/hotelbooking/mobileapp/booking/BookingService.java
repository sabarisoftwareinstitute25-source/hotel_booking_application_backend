package com.hotelbooking.mobileapp.booking;

import com.hotelbooking.mobileapp.hotel.Hotel;
import com.hotelbooking.mobileapp.hotel.HotelRepository;
import com.hotelbooking.mobileapp.user.UserAccount;
import com.hotelbooking.mobileapp.user.UserAccountRepository;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    public Booking create(Booking booking) {
        // Ensure user and hotel are loaded
        if (booking.getUser() != null && booking.getUser().getId() != null) {
            Optional<UserAccount> userOpt = userAccountRepository.findById(booking.getUser().getId());
            if (userOpt.isPresent()) {
                booking.setUser(userOpt.get());
            }
        }

        if (booking.getHotel() != null && booking.getHotel().getId() != null) {
            Optional<Hotel> hotelOpt = hotelRepository.findById(booking.getHotel().getId());
            if (hotelOpt.isPresent()) {
                booking.setHotel(hotelOpt.get());
            }
        }

        if (booking.getId() == null || booking.getId().isEmpty()) {
            booking.setId(idGeneratorService.generateBookingId());
        }

        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> findByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Optional<Booking> findById(String id) {
        return bookingRepository.findById(id);
    }

    public Booking update(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void cancelBooking(String bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
        }
    }
}

