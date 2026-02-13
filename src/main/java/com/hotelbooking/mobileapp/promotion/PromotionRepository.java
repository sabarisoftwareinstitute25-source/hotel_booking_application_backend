package com.hotelbooking.mobileapp.promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {

    /**
     * Find all active promotions that haven't expired.
     */
    @Query("SELECT p FROM Promotion p WHERE p.isActive = true AND (p.expiresAt IS NULL OR p.expiresAt > :now) ORDER BY p.createdAt DESC")
    List<Promotion> findActivePromotions(Instant now);

    /**
     * Find promotions by type.
     */
    List<Promotion> findByPromotionTypeAndIsActiveTrue(String promotionType);
}

