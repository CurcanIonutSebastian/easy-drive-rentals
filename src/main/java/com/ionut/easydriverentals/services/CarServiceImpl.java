package com.ionut.easydriverentals.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.models.enums.CarStatus;
import com.ionut.easydriverentals.models.dtos.CarDTO;
import com.ionut.easydriverentals.models.entities.Car;
import com.ionut.easydriverentals.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final ObjectMapper objectMapper;

    @Override
    public CarDTO createCar(CarDTO carDTO) {
            carDTO.setCarStatus(CarStatus.AVAILABLE);
            Car carEntity = objectMapper.convertValue(carDTO, Car.class);
            Car carResponseEntity = carRepository.save(carEntity);
            return objectMapper.convertValue(carResponseEntity, CarDTO.class);
    }

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(this::mapCarToCarDTO).toList();
    }

    @Override
    public List<CarDTO> getCarsByBrandYearPrice(String brand, int year, double price) {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> car.getProductYear() <= year)
                .filter(car -> car.getPricePerDay() <= price)
                .map(this::mapCarToCarDTO)
                .toList();
    }

    private CarDTO mapCarToCarDTO(Car car) {
        return CarDTO.builder()
                .id(car.getId())
                .model(car.getModel())
                .brand(car.getBrand())
                .capacity(car.getCapacity())
                .productYear(car.getProductYear())
                .pricePerDay(car.getPricePerDay())
                .carStatus(car.getCarStatus())
                .build();
    }
}