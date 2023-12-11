package com.ionut.easydriverentals.services;

import com.ionut.easydriverentals.models.enums.CarStatus;
import com.ionut.easydriverentals.exceptions.DataNotFoundException;
import com.ionut.easydriverentals.models.dtos.RentalDTO;
import com.ionut.easydriverentals.models.dtos.RentalResponseDTO;
import com.ionut.easydriverentals.models.entities.Car;
import com.ionut.easydriverentals.models.entities.Client;
import com.ionut.easydriverentals.models.entities.Rental;
import com.ionut.easydriverentals.models.enums.UserHistoryStatus;
import com.ionut.easydriverentals.repositories.CarRepository;
import com.ionut.easydriverentals.repositories.ClientRepository;
import com.ionut.easydriverentals.repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;


    @Override
    public RentalResponseDTO createRental(Long clientId, Long carId, RentalDTO rentalDTO) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        Optional<Car> carOptional = carRepository.findById(carId);
        if (clientOptional.isEmpty() || carOptional.isEmpty()) {
            throw new DataNotFoundException("Invalid client or car id!");
        }

        rentalDTO.setStartRentalDate(LocalDate.now());
        Car carEntity = carOptional.get();
        if (rentalDTO.getRentalDays() != 0) {
            rentalDTO.setTotalPrice(carEntity.getPricePerDay() * rentalDTO.getRentalDays());
        } else {
            rentalDTO.setTotalPrice(carEntity.getPricePerDay());
        }

        Rental rentalEntity = new Rental();
        rentalEntity.setId(rentalDTO.getId());
        rentalEntity.setStartRentalDate(rentalDTO.getStartRentalDate());
        rentalEntity.setEndRentalDate(rentalDTO.getStartRentalDate().plusDays(rentalDTO.getRentalDays()));
        rentalEntity.setTotalPrice(rentalDTO.getTotalPrice());
        rentalEntity.setCarId(carEntity.getId());

        rentalRepository.save(rentalEntity);
        rentalRepository.assignClientAndCarIdToRental(clientId, carId);

        carEntity.setCarStatus(CarStatus.RENTED);
        carRepository.save(carEntity);

        return RentalResponseDTO.builder()
                .id(rentalEntity.getId())
                .startRentalDate(rentalEntity.getStartRentalDate())
                .endRentalDate(rentalEntity.getEndRentalDate())
                .totalPrice(rentalEntity.getTotalPrice())
                .clientId(clientId)
                .carId(carId)
                .build();
    }

    @Override
    public List<RentalResponseDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream()
                .filter(rental -> rental.getCar() != null)
                .map(this::matRentalToRentalResponseDTO).toList();
    }

    @Override
    public String returnCarByRentalId(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Rental does not exist!"));
        Car car = carRepository.findById(rental.getCarId()).orElseThrow(() -> new DataNotFoundException("Car does not exist!"));
        rental.setReturnedCar(LocalDate.now());
        rental.setCar(null);
        car.setCarStatus(CarStatus.AVAILABLE);

        int compareResult = rental.getReturnedCar().compareTo(rental.getEndRentalDate());
        if (compareResult <= 0) {
            rental.setUserHistoryStatus(UserHistoryStatus.AT_TIME);
        } else {
            rental.setUserHistoryStatus(UserHistoryStatus.DELAY);
        }

        carRepository.save(car);
        rentalRepository.save(rental);
        return "The car was returned!";
    }

    private RentalResponseDTO matRentalToRentalResponseDTO(Rental rental) {
        return RentalResponseDTO.builder()
                .id(rental.getId())
                .startRentalDate(rental.getStartRentalDate())
                .endRentalDate(rental.getEndRentalDate())
                .totalPrice(rental.getTotalPrice())
                .carId(rental.getCar().getId())
                .clientId(rental.getClient().getId())
                .build();
    }
}