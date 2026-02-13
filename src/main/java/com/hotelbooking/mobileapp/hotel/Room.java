package com.hotelbooking.mobileapp.hotel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Room entity matching frontend Room model exactly.
 * Frontend fields: id?, hotelId, roomNumber, roomType, pricePerNight, description?, available
 */
@Entity
@Table(name = "rooms")
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnore
    private Hotel hotel;
    
    @NotBlank
    @Column(name = "room_number", nullable = false, length = 100)
    private String roomNumber;
    
    @NotBlank
    @Column(name = "room_type", nullable = false, length = 50)
    private String roomType;
    
    @NotNull
    @Positive
    @Column(name = "price_per_night", nullable = false)
    private BigDecimal pricePerNight;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    private Boolean available = true;

    public Room() {
    }

    public Room(Hotel hotel, String roomNumber, String roomType, BigDecimal pricePerNight) {
        this.hotel = hotel;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.available = true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    // Getter for hotelId to match frontend expectation
    @JsonProperty("hotelId")
    public String getHotelId() {
        return hotel != null ? hotel.getId() : null;
    }
}

