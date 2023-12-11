package com.ionut.easydriverentals.controllers;

import com.ionut.easydriverentals.models.dtos.CarDTO;
import com.ionut.easydriverentals.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody @Valid CarDTO carDTO) {
        return ResponseEntity.ok(carService.createCar(carDTO));
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> getCarsByBrandYearPrice(@RequestParam("brand") String brand,
                                                                @RequestParam("year") int year,
                                                                @RequestParam("price") double price) {
        return ResponseEntity.ok(carService.getCarsByBrandYearPrice(brand, year, price));
    }
}