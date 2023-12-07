package com.ionut.easydriverentals.services;

import com.ionut.easydriverentals.models.dtos.ClientDTO;
import com.ionut.easydriverentals.models.dtos.UpdateClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO getClientById(Long id);
    List<ClientDTO> getAllClients();
    ClientDTO editClientById(Long id, UpdateClientDTO updateClientDTO);
    String deleteClientById(Long id);
}