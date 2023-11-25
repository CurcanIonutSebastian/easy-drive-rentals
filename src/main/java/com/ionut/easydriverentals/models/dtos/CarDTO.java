package com.ionut.easydriverentals.models.dtos;

import com.ionut.easydriverentals.enums.CarStatus;
import lombok.Data;

@Data
public class CarDTO {

    private Long id;
    private String brand;
    private String model;
    private int capacity;
    private int productYear;
    private double pricePerDay;
    private CarStatus carStatus;
}