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
    Optional<UserAccount> findByMobile(String mobile);
    boolean existsByMobile(String mobile);

    @Query("SELECT u FROM UserAccount u WHERE u.mobile = :mobile OR u.mobile = :mobileWithoutPlus OR u.mobile = :mobileWithoutCountry")
    List<UserAccount> findByMobileVariations(
        @Param("mobile") String mobile,
        @Param("mobileWithoutPlus") String mobileWithoutPlus,
        @Param("mobileWithoutCountry") String mobileWithoutCountry
    );

    Optional<Object> findByEmailIgnoreCase(String email);

}

