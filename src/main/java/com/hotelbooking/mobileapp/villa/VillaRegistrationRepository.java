package com.hotelbooking.mobileapp.villa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VillaRegistrationRepository extends JpaRepository<VillaRegistration,String> {

    @Query("SELECT t.villaId FROM VillaRegistration t")
    List<String> findAllVillaIds();
}