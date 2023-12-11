package com.ionut.easydriverentals.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ionut.easydriverentals.models.enums.UserHistoryStatus;
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
    @Column(name = "car_with_id")
    private Long carId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    @Column(name = "start_rental_date")
    private LocalDate startRentalDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    @Column(name = "end_rental_date")
    private LocalDate endRentalDate;
    @Column(name = "returned_car")
    private LocalDate returnedCar;
    @Column(name = "user_history_status")
    private UserHistoryStatus userHistoryStatus;
    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;
}