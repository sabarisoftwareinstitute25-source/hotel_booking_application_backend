package com.hotelbooking.mobileapp.hotel;

import jakarta.validation.constraints.Min;
import java.time.LocalDate;

/**
 * Request DTO for hotel search with dates and guest information.
 * Matches frontend search form: location, check-in, check-out, rooms, adults, children.
 */
public class HotelSearchRequest {

    private String location; // "Where do you want to stay?" - can be city, country, or address

    private LocalDate checkIn; // Check-in date

    private LocalDate checkOut; // Check-out date

    @Min(1)
    private Integer rooms = 1; // Number of rooms

    @Min(1)
    private Integer adults = 1; // Number of adults

    @Min(0)
    private Integer children = 0; // Number of children

    private String city; // Optional: specific city filter

    private String country; // Optional: specific country filter

    private Integer starRating; // Optional: star rating filter

    public HotelSearchRequest() {
    }

    public HotelSearchRequest(String location, LocalDate checkIn, LocalDate checkOut, 
                              Integer rooms, Integer adults, Integer children) {
        this.location = location;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.rooms = rooms;
        this.adults = adults;
        this.children = children;
    }

    // Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }
}

