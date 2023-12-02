package com.ionut.easydriverentals.models.dtos;

import com.ionut.easydriverentals.enums.UserHistoryStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoryClientResponseDTO {

    private Long id;
    private Long carId;
    private LocalDate startRentalDate;
    private LocalDate endRentalDate;
    private LocalDate returnedCar;
    private UserHistoryStatus userHistoryStatus;
    private double totalPrice;
}
