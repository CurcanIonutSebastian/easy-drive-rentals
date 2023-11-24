package com.ionut.easydriverentals.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.exceptions.DataNotFoundException;
import com.ionut.easydriverentals.exceptions.EmptyInputException;
import com.ionut.easydriverentals.models.dtos.ClientDTO;
import com.ionut.easydriverentals.models.dtos.ClientDetailsDTO;
import com.ionut.easydriverentals.models.dtos.ClientResponseDTO;
import com.ionut.easydriverentals.models.entities.Client;
import com.ionut.easydriverentals.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ObjectMapper objectMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ObjectMapper objectMapper) {
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        if (clientDTO.getFirstName().isEmpty() || clientDTO.getLastName().isEmpty()) {
            throw new EmptyInputException("Filed empty");
        }

        Client clientEntity = objectMapper.convertValue(clientDTO, Client.class);
        Client clientResponseEntity = clientRepository.save(clientEntity);
        return objectMapper.convertValue(clientResponseEntity, ClientDTO.class);
    }

    @Override
    public ClientResponseDTO getClientById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new DataNotFoundException("User does not exist!");
        }

        Client client = clientOptional.get();
        return mapClientToClientResponseDTO(client);
    }

    private ClientResponseDTO mapClientToClientResponseDTO(Client client) {
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        ClientDetailsDTO clientDetailsDTO = new ClientDetailsDTO();

        clientResponseDTO.setId(client.getId());
        clientResponseDTO.setFirstName(client.getFirstName());
        clientResponseDTO.setLastName(client.getLastName());

        if (!(client.getClientDetails() == null)) {
            clientDetailsDTO.setId(client.getClientDetails().getId());
            clientDetailsDTO.setPhoneNumber(client.getClientDetails().getPhoneNumber());
            clientDetailsDTO.setEmail(client.getClientDetails().getEmail());
            clientDetailsDTO.setCountry(client.getClientDetails().getCountry());
            clientDetailsDTO.setCity(client.getClientDetails().getCity());
            clientDetailsDTO.setStreet(client.getClientDetails().getStreet());
            clientDetailsDTO.setBlock(client.getClientDetails().getBlock());
            clientDetailsDTO.setStair(client.getClientDetails().getStair());
            clientDetailsDTO.setFloor(client.getClientDetails().getFloor());
            clientDetailsDTO.setApartment(client.getClientDetails().getApartment());

            clientResponseDTO.setClientDetails(clientDetailsDTO);
        }
        return clientResponseDTO;
    }
}