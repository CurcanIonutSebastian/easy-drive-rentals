package com.ionut.easydriverentals.services;

import com.ionut.easydriverentals.models.dtos.ClientDTO;
import com.ionut.easydriverentals.models.dtos.ClientResponseDTO;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    ClientResponseDTO getClientById(Long id);
}