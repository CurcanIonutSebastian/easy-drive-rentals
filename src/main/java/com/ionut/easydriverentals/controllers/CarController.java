package com.ionut.easydriverentals.controllers;

import com.ionut.easydriverentals.models.dtos.CarDTO;
import com.ionut.easydriverentals.services.CarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody @Valid CarDTO carDTO) {
        return ResponseEntity.ok(carService.createCar(carDTO));
    }
}