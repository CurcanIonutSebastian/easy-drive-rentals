package com.ionut.easydriverentals.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_rental_date")
    private LocalDate startRentalDate;
    @Column(name = "end_rental_date")
    private LocalDate endRentalDate;
    @Column(name = "total_price")
    private double totalPrice;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;
}