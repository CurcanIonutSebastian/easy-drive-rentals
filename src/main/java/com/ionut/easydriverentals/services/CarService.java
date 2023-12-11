package com.ionut.easydriverentals.services;

import com.ionut.easydriverentals.models.dtos.CarDTO;

import java.util.List;

public interface CarService {

    CarDTO createCar(CarDTO carDTO);

    List<CarDTO> getAllCars();

    List<CarDTO> getCarsByBrandYearPrice(String brand, int year, double price);
}