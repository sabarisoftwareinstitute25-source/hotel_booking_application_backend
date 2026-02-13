package com.hotelbooking.mobileapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<UserAccount> findByPhone(String phone);
    boolean existsByPhone(String phone);
    
    /**
     * Find user by phone with normalization handling.
     * Tries exact match first, then tries variations.
     */
    @Query("SELECT u FROM UserAccount u WHERE u.phone = :phone OR u.phone = :phoneWithoutPlus OR u.phone = :phoneWithoutCountry")
    List<UserAccount> findByPhoneVariations(
        @Param("phone") String phone,
        @Param("phoneWithoutPlus") String phoneWithoutPlus,
        @Param("phoneWithoutCountry") String phoneWithoutCountry
    );

    Optional<Object> findByEmailIgnoreCase(String email);
}

