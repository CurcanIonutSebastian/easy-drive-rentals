package com.ionut.easydriverentals.services;

import com.ionut.easydriverentals.models.dtos.RentalDTO;
import com.ionut.easydriverentals.models.dtos.RentalResponseDTO;

import java.util.List;

public interface RentalService {

    RentalResponseDTO createRental(Long clientId, Long carId, RentalDTO rentalDTO);

    List<RentalResponseDTO> getAllRentals();
}