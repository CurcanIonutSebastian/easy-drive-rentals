package com.ionut.easydriverentals.models.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentalDTO {

    private Long id;
    private LocalDate startRentalDate;
    private int rentalDays;
    private double totalPrice;
}