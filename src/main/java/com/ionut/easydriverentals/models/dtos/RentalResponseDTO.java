package com.ionut.easydriverentals.models.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentalResponseDTO {

    private Long id;
    private LocalDate startRentalDate;
    private LocalDate endRentalDate;
    private double totalPrice;
    private Long clientId;
    private Long carId;
}