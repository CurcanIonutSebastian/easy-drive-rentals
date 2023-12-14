package com.ionut.easydriverentals.unit_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.models.dtos.CarDTO;
import com.ionut.easydriverentals.models.entities.Car;
import com.ionut.easydriverentals.models.enums.CarStatus;
import com.ionut.easydriverentals.repositories.CarRepository;
import com.ionut.easydriverentals.services.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void testCreateCar() {

        CarDTO carDTO = createCarDTO();
        Car car = createCarEntity();
        Car saveCar = createCarEntity();

        when(objectMapper.convertValue(carDTO, Car.class)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(saveCar);
        when(objectMapper.convertValue(saveCar, CarDTO.class)).thenReturn(carDTO);

        CarDTO saveCarDTO = carService.createCar(carDTO);

        verify(carRepository, times(1)).save(car);
        assertEquals(carDTO, saveCarDTO);
    }

    @Test
    void testCreateCarShouldThrowDataIntegrityViolationException() {
        CarDTO invalidCarDTO = CarDTO.builder().build();
        Car invalidCarEntity = new Car();

        when(objectMapper.convertValue(invalidCarDTO, Car.class)).thenReturn(invalidCarEntity);
        when(carRepository.save(invalidCarEntity)).thenThrow(new DataIntegrityViolationException("Test exception"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            carService.createCar(invalidCarDTO);
        });
    }

    private CarDTO createCarDTO() {
        return CarDTO.builder()
                .id(1L)
                .brand("BMW")
                .model("X5")
                .pricePerDay(350)
                .capacity(2498)
                .productYear(2008)
                .carStatus(CarStatus.AVAILABLE)
                .build();
    }

    private Car createCarEntity() {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("BMW");
        car.setBrand("X5");
        car.setProductYear(2008);
        car.setCapacity(2498);
        car.setCarStatus(CarStatus.AVAILABLE);
        car.setPricePerDay(350);
        return car;
    }
}