package com.ionut.easydriverentals.controllers;

import com.ionut.easydriverentals.models.dtos.ClientDetailsDTO;
import com.ionut.easydriverentals.services.ClientDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.zip.DataFormatException;

@Validated
@RestController
@RequestMapping("/clients-details")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;

    public ClientDetailsController(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<ClientDetailsDTO> createClientDetails(@PathVariable Long clientId, @RequestBody @Valid ClientDetailsDTO clientDetailsDTO) throws DataFormatException {
        return ResponseEntity.ok(clientDetailsService.createClientDetails(clientId, clientDetailsDTO));
    }
}