package com.ionut.easydriverentals.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.models.enums.CarStatus;
import com.ionut.easydriverentals.exceptions.EmptyInputException;
import com.ionut.easydriverentals.models.dtos.CarDTO;
import com.ionut.easydriverentals.models.entities.Car;
import com.ionut.easydriverentals.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final ObjectMapper objectMapper;

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        if (carDTO.getBrand().isEmpty() ||
                carDTO.getModel().isEmpty() ||
                carDTO.getCapacity() == 0 ||
                carDTO.getProductYear() == 0 ||
                carDTO.getPricePerDay() == 0) {
            throw new EmptyInputException("All fields need to be completed!");
        }

        carDTO.setCarStatus(CarStatus.AVAILABLE);
        Car carEntity = objectMapper.convertValue(carDTO, Car.class);
        Car carResponseEntity = carRepository.save(carEntity);
        return objectMapper.convertValue(carResponseEntity, CarDTO.class);
    }
}