package com.ionut.easydriverentals.services;

import com.ionut.easydriverentals.models.dtos.ClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO getClientById(Long id);
    List<ClientDTO> getAllClients();
}