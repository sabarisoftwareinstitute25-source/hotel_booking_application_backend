package com.hotelbooking.mobileapp.payment;

import com.hotelbooking.mobileapp.booking.Booking;
import com.hotelbooking.mobileapp.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Payment Controller matching frontend API endpoints.
 * Endpoints:
 * POST /api/payments/process
 * GET /api/payments/status?transactionId=
 */
@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private BookingService bookingService;

    /**
     * POST /api/payments/process
     * Frontend sends: {bookingId: string, amount: number, paymentMethod: string}
     * Frontend expects: PaymentResponse {success, message, transactionId?, amount?, status?, paymentMethod?}
     */
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> request) {
        try {
            String bookingId = (String) request.get("bookingId");
            Object amountObj = request.get("amount");
            String paymentMethod = (String) request.get("paymentMethod");
            
            if (bookingId == null || amountObj == null || paymentMethod == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Missing required fields");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Find booking
            var bookingOpt = bookingService.findById(bookingId);
            if (bookingOpt.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Booking not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            Booking booking = bookingOpt.get();
            
            // Simulate payment processing (in production, integrate with payment gateway)
            String transactionId = "TXN_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            
            // Update booking status to CONFIRMED
            booking.setStatus("CONFIRMED");
            bookingService.update(booking);
            
            // Build response matching frontend PaymentResponse
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Payment processed successfully");
            response.put("transactionId", transactionId);
            response.put("amount", amountObj);
            response.put("status", "SUCCESS");
            response.put("paymentMethod", paymentMethod);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Payment processing failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/payments/status?transactionId=
     * Frontend expects: PaymentStatusResponse {success, message, transactionId?, status?}
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getPaymentStatus(@RequestParam String transactionId) {
        try {
            if (transactionId == null || transactionId.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Transaction ID is required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // In production, query payment gateway for status
            // For now, simulate successful status
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Payment status retrieved successfully");
            response.put("transactionId", transactionId);
            response.put("status", "SUCCESS");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to retrieve payment status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

