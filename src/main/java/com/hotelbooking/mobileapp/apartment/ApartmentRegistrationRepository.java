package com.hotelbooking.mobileapp.apartment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApartmentRegistrationRepository
        extends JpaRepository<ApartmentRegistration,String> {

    @Query("SELECT a.apartmentId FROM ApartmentRegistration a")
    List<String> findAllApartmentIds();
}