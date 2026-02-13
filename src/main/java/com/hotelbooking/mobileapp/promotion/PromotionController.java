package com.hotelbooking.mobileapp.promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

/**
 * Promotion Controller for exclusive offers.
 * Endpoint: GET /api/promotions/exclusive-offers
 */
@RestController
@RequestMapping("/api/promotions")
@CrossOrigin(origins = "*")
public class PromotionController {

    @Autowired
    private PromotionRepository promotionRepository;

    /**
     * GET /api/promotions/exclusive-offers
     * Returns list of active exclusive offers for the carousel.
     * Frontend expects: List<Promotion>
     */
    @GetMapping("/exclusive-offers")
    public ResponseEntity<List<Promotion>> getExclusiveOffers() {
        try {
            List<Promotion> promotions = promotionRepository.findActivePromotions(Instant.now());
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/promotions
     * Get all promotions (admin use).
     */
    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        try {
            List<Promotion> promotions = promotionRepository.findAll();
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

