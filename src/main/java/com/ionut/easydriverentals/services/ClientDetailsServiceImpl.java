package com.ionut.easydriverentals.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.exceptions.DataExistsException;
import com.ionut.easydriverentals.exceptions.EmptyInputException;
import com.ionut.easydriverentals.models.dtos.ClientDetailsDTO;
import com.ionut.easydriverentals.models.entities.Client;
import com.ionut.easydriverentals.models.entities.ClientDetails;
import com.ionut.easydriverentals.repositories.ClientDetailsRepository;
import com.ionut.easydriverentals.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@Slf4j
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final ClientDetailsRepository clientDetailsRepository;

    private final ClientRepository clientRepository;

    private final ObjectMapper objectMapper;

    public ClientDetailsServiceImpl(ClientDetailsRepository clientDetailsRepository, ClientRepository clientRepository, ObjectMapper objectMapper) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ClientDetailsDTO createClientDetails(Long clientId, ClientDetailsDTO clientDetailsDTO) throws DataFormatException {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isEmpty()) {
            throw new DataFormatException("Invalid client id");
        }

        if (clientDetailsDTO.getPhoneNumber() == 0 ||
                clientDetailsDTO.getEmail().isEmpty() ||
                clientDetailsDTO.getCity().isEmpty() ||
                clientDetailsDTO.getCountry().isEmpty() ||
                clientDetailsDTO.getStreet().isEmpty()) {
            throw new EmptyInputException("The fields phoneNumber, email, country, city, street must not be empty!");
        }

//        if (clientDetailsDTO.getBlock().isEmpty()) {
//            clientDetailsDTO.setBlock(null);
//            clientDetailsDTO.setStair(null);
//        }
        try {
            ClientDetails clientDetailsEntity = objectMapper.convertValue(clientDetailsDTO, ClientDetails.class);
            ClientDetails clientDetailsResponseEntity = clientDetailsRepository.save(clientDetailsEntity);
            clientDetailsRepository.assignClientDetailsToClient(clientId);

            return objectMapper.convertValue(clientDetailsResponseEntity, ClientDetailsDTO.class);
        } catch (DataIntegrityViolationException exception) {
            throw new DataExistsException("Invalid email ore phone number!");
        }
    }
}