package com.ionut.easydriverentals.controllers;

import com.ionut.easydriverentals.models.dtos.*;
import com.ionut.easydriverentals.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.createClient(clientDTO));
    }

    @PostMapping("/{clientId}/favorite-cars")
    public ResponseEntity<String> addFavoriteCar(@PathVariable Long clientId,
                                                 @RequestBody ClientFavoriteCarDTO clientFavoriteCarDTO) {
        return ResponseEntity.ok(clientService.addFavoriteCar(clientId, clientFavoriteCarDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/{id}/favorite-cars")
    public ResponseEntity<ClientWithFavoritesDTO> getClientWithFavorites(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientWithFavorites(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}/client-histories")
    public ResponseEntity<List<HistoryClientResponseDTO>> getAllHistoryByClientId(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getAllHistoryByClientId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> editClientById(@PathVariable Long id, @RequestBody UpdateClientDTO updateClientDTO) {
        return ResponseEntity.ok(clientService.editClientById(id, updateClientDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.deleteClientById(id));
    }
}