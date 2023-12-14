package com.ionut.easydriverentals.unit_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.exceptions.EmailOrPhoneExistsException;
import com.ionut.easydriverentals.models.dtos.ClientDTO;
import com.ionut.easydriverentals.models.dtos.ClientDetailsDTO;
import com.ionut.easydriverentals.models.entities.Client;
import com.ionut.easydriverentals.models.entities.ClientDetails;
import com.ionut.easydriverentals.repositories.ClientDetailsRepository;
import com.ionut.easydriverentals.repositories.ClientRepository;
import com.ionut.easydriverentals.services.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientDetailsRepository clientDetailsRepository;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void testCreateClientAndClientDetails() {
        ClientDTO inputClientDTO = createClientDTO();
        Client clientEntity = createClientEntity();
        ClientDetails clientDetailsEntity = createClientDetailsEntity();

        when(clientRepository.save(objectMapper.convertValue(inputClientDTO, Client.class))).thenReturn(clientEntity);
        when(clientDetailsRepository.save(objectMapper.convertValue(inputClientDTO.getClientDetailsDTO(), ClientDetails.class))).thenReturn(clientDetailsEntity);

        ClientDTO result = clientService.createClient(inputClientDTO);

        assertNotNull(result);
        verify(clientRepository, times(1)).save(objectMapper.convertValue(inputClientDTO, Client.class));
        verify(clientDetailsRepository, times(1)).save(objectMapper.convertValue(inputClientDTO.getClientDetailsDTO(), ClientDetails.class));
    }

    @Test
    void testCreateClientAndClientDetailsShouldThrowDataExistsException() {
        ClientDTO inputClientDTO = createClientDTO();

        when(clientRepository.save(objectMapper.convertValue(inputClientDTO, Client.class))).thenReturn(createClientEntity());
        when(clientDetailsRepository.save(objectMapper.convertValue(inputClientDTO.getClientDetailsDTO(), ClientDetails.class))).thenThrow(new DataIntegrityViolationException("Test exception"));

        assertThrows(EmailOrPhoneExistsException.class, () -> {
            clientService.createClient(inputClientDTO);
        });
    }

    private ClientDTO createClientDTO() {
        return ClientDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .clientDetailsDTO(createClientDetailsDTO())
                .build();
    }

    private Client createClientEntity() {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setClientDetails(createClientDetailsEntity());
        return client;
    }

    private ClientDetails createClientDetailsEntity() {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setId(1L);
        clientDetails.setPhoneNumber("123456789");
        clientDetails.setEmail("john.doe@example.com");
        clientDetails.setCountry("Country");
        clientDetails.setCity("City");
        clientDetails.setStreet("Street");
        return clientDetails;
    }

    private ClientDetailsDTO createClientDetailsDTO() {
        return ClientDetailsDTO.builder()
                .id(1L)
                .phoneNumber("123456789")
                .email("john.doe@example.com")
                .country("Country")
                .city("City")
                .street("Street")
                .build();
    }

}