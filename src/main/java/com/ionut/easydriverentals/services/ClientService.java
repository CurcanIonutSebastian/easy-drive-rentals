package com.ionut.easydriverentals.services;

import com.ionut.easydriverentals.models.dtos.*;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO getClientById(Long id);

    List<ClientDTO> getAllClients();

    ClientDTO editClientById(Long id, UpdateClientDTO updateClientDTO);

    String deleteClientById(Long id);

    List<HistoryClientResponseDTO> getAllHistoryByClientId(Long id);

    String addFavoriteCar(Long clientId, ClientFavoriteCarDTO clientFavoriteCarDTO);

    ClientWithFavoritesDTO getClientWithFavorites(Long id);
}