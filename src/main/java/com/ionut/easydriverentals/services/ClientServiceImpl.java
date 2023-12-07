package com.ionut.easydriverentals.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.exceptions.DataExistsException;
import com.ionut.easydriverentals.exceptions.DataNotFoundException;
import com.ionut.easydriverentals.models.dtos.ClientDTO;
import com.ionut.easydriverentals.models.dtos.ClientDetailsDTO;
import com.ionut.easydriverentals.models.dtos.UpdateClientDTO;
import com.ionut.easydriverentals.models.entities.Client;
import com.ionut.easydriverentals.models.entities.ClientDetails;
import com.ionut.easydriverentals.repositories.ClientDetailsRepository;
import com.ionut.easydriverentals.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientDetailsRepository clientDetailsRepository;
    private final ObjectMapper objectMapper;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        try {
            Client clientEntity = clientRepository.save(objectMapper.convertValue(clientDTO, Client.class));
            ClientDetails clientDetailsEntity = clientDetailsRepository.save(objectMapper.convertValue(clientDTO.getClientDetailsDTO(), ClientDetails.class));
            clientDetailsRepository.assignClientDetailsToClient(clientEntity.getId());

            return ClientDTO.builder()
                    .id(clientEntity.getId())
                    .firstName(clientEntity.getFirstName())
                    .lastName(clientEntity.getLastName())
                    .clientDetailsDTO(ClientDetailsDTO.builder()
                            .id(clientDetailsEntity.getId())
                            .email(clientDetailsEntity.getEmail())
                            .phoneNumber(clientDetailsEntity.getPhoneNumber())
                            .country(clientDetailsEntity.getCountry())
                            .city(clientDetailsEntity.getCity())
                            .street(clientDetailsEntity.getStreet())
                            .block(clientDetailsEntity.getBlock())
                            .apartment(clientDetailsEntity.getApartment())
                            .stair(clientDetailsEntity.getStair())
                            .floor(clientDetailsEntity.getFloor())
                            .build())
                    .build();
        } catch (DataIntegrityViolationException exception) {
            throw new DataExistsException("Invalid email ore phone number!");
        }
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new DataNotFoundException("Client does not exist!");
        }

        Client client = clientOptional.get();
        return mapClientToClientDTO(client);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(this::mapClientToClientDTO).toList();
    }

    @Override
    public ClientDTO editClientById(Long id, UpdateClientDTO updateClientDTO) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Client does not exist!"));
        try {
            client.setFirstName(updateClientDTO.getFirstName() != null ? updateClientDTO.getFirstName() : client.getFirstName());
            client.setLastName(updateClientDTO.getLastName() != null ? updateClientDTO.getLastName() : client.getLastName());
            client.setClientDetails(updateClientDetails(updateClientDTO, client));

            clientRepository.save(client);
            return mapClientToClientDTO(client);
        } catch (DataIntegrityViolationException e) {
            throw new DataExistsException("Invalid email ore phone number!");
        }
    }

    @Override
    public String deleteClientById(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return "Client deleted successfully!";
        } else {
            throw new DataNotFoundException("Client does not exist!");
        }
    }

    private static ClientDetails updateClientDetails(UpdateClientDTO newClientDTO, Client client) {
        ClientDetails clientDetails = client.getClientDetails();

        clientDetails.setEmail(newClientDTO.getUpdateClientDetailsDTO().getEmail() != null ?
                newClientDTO.getUpdateClientDetailsDTO().getEmail() :
                clientDetails.getEmail());

        clientDetails.setPhoneNumber(newClientDTO.getUpdateClientDetailsDTO().getPhoneNumber() != null ?
                newClientDTO.getUpdateClientDetailsDTO().getPhoneNumber() :
                clientDetails.getPhoneNumber());

        clientDetails.setCountry(newClientDTO.getUpdateClientDetailsDTO().getCountry() != null ?
                newClientDTO.getUpdateClientDetailsDTO().getCountry() :
                clientDetails.getCountry());

        clientDetails.setCity(newClientDTO.getUpdateClientDetailsDTO().getCity() != null ?
                newClientDTO.getUpdateClientDetailsDTO().getCity() :
                clientDetails.getCity());

        clientDetails.setStreet(newClientDTO.getUpdateClientDetailsDTO().getStreet() != null ?
                newClientDTO.getUpdateClientDetailsDTO().getStreet() :
                clientDetails.getStreet());

        clientDetails.setBlock(newClientDTO.getUpdateClientDetailsDTO().getBlock() != null ?
                newClientDTO.getUpdateClientDetailsDTO().getBlock() :
                clientDetails.getBlock());

        clientDetails.setStair(newClientDTO.getUpdateClientDetailsDTO().getStair() != null ?
                newClientDTO.getUpdateClientDetailsDTO().getStair() :
                clientDetails.getStair());

        clientDetails.setFloor(newClientDTO.getUpdateClientDetailsDTO().getFloor() != 0 ?
                newClientDTO.getUpdateClientDetailsDTO().getFloor() :
                clientDetails.getFloor());

        clientDetails.setApartment(newClientDTO.getUpdateClientDetailsDTO().getApartment() != 0 ?
                newClientDTO.getUpdateClientDetailsDTO().getApartment() :
                clientDetails.getApartment());

        return clientDetails;
    }

    private ClientDTO mapClientToClientDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .clientDetailsDTO(mapClientToClientDetailsDTO(client))
                .build();
    }

    private ClientDetailsDTO mapClientToClientDetailsDTO(Client client) {
        if (client.getClientDetails() == null) {
            return null;
        } else {
            return ClientDetailsDTO.builder()
                    .id(client.getClientDetails().getId())
                    .email(client.getClientDetails().getEmail())
                    .phoneNumber(client.getClientDetails().getPhoneNumber())
                    .country(client.getClientDetails().getCountry())
                    .city(client.getClientDetails().getCity())
                    .street(client.getClientDetails().getStreet())
                    .block(client.getClientDetails().getBlock())
                    .stair(client.getClientDetails().getStair())
                    .floor(client.getClientDetails().getFloor())
                    .apartment(client.getClientDetails().getApartment()).build();
        }
    }
}