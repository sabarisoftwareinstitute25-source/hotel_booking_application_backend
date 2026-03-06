package com.hotelbooking.mobileapp.resort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResortRegistrationRepository extends JpaRepository<ResortRegistration, String> {

    @Query("SELECT r.resortId FROM ResortRegistration r")
    List<String> findAllResortIds();
}