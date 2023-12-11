package com.ionut.easydriverentals.models.dtos;

import com.ionut.easydriverentals.models.enums.CarStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDTO {

    private Long id;
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @Min(0)
    private int capacity;
    @Min(1900)
    private int productYear;
    @Min(0)
    private double pricePerDay;
    private CarStatus carStatus;
}