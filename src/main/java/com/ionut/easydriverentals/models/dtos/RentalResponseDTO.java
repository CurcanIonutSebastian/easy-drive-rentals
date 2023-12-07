package com.ionut.easydriverentals.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RentalResponseDTO {

    private Long id;
    private LocalDate startRentalDate;
    private LocalDate endRentalDate;
    private double totalPrice;
    private Long clientId;
    private Long carId;
}