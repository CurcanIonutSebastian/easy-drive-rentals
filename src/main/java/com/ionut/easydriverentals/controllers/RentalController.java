package com.ionut.easydriverentals.controllers;

import com.ionut.easydriverentals.models.dtos.RentalDTO;
import com.ionut.easydriverentals.models.dtos.RentalResponseDTO;
import com.ionut.easydriverentals.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/{clientId}/{carId}")
    public ResponseEntity<RentalResponseDTO> createRental(@PathVariable Long clientId,
                                                          @PathVariable Long carId,
                                                          @RequestBody @Valid RentalDTO rentalDTO) {
        return ResponseEntity.ok(rentalService.createRental(clientId, carId, rentalDTO));
    }
}