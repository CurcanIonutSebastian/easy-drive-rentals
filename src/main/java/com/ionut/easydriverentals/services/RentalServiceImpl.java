package com.ionut.easydriverentals.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.enums.CarStatus;
import com.ionut.easydriverentals.exceptions.DataNotFoundException;
import com.ionut.easydriverentals.models.dtos.RentalDTO;
import com.ionut.easydriverentals.models.dtos.RentalResponseDTO;
import com.ionut.easydriverentals.models.entities.Car;
import com.ionut.easydriverentals.models.entities.Client;
import com.ionut.easydriverentals.models.entities.History;
import com.ionut.easydriverentals.models.entities.Rental;
import com.ionut.easydriverentals.repositories.CarRepository;
import com.ionut.easydriverentals.repositories.ClientRepository;
import com.ionut.easydriverentals.repositories.HistoryRepository;
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
    private final HistoryRepository historyRepository;
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
        Client clientEntity = clientOptional.get();
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

        rentalRepository.save(rentalEntity);
        rentalRepository.assignClientAndCarIdToRental(clientId, carId);

        carEntity.setCarStatus(CarStatus.RENTED);
        carRepository.save(carEntity);

        History historyEntity = new History();
        historyEntity.setStartRentalDate(rentalEntity.getStartRentalDate());
        historyEntity.setEndRentalDate(rentalEntity.getEndRentalDate());
        historyEntity.setTotalPrice(rentalEntity.getTotalPrice());
        historyEntity.setCar(carEntity);
        historyEntity.setClient(clientEntity);

        historyRepository.save(historyEntity);

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
        return rentals.stream().map(this::matRentalToRentalResponseDTO).toList();
    }

    private RentalResponseDTO matRentalToRentalResponseDTO(Rental rental) {
        return RentalResponseDTO.builder()
                .id(rental.getId())
                .startRentalDate(rental.getStartRentalDate())
                .endRentalDate(rental.getEndRentalDate())
                .carId(rental.getCar().getId())
                .clientId(rental.getClient().getId())
                .build();
    }
}