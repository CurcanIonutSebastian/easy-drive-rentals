package com.ionut.easydriverentals.integration_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.models.dtos.ClientDTO;
import com.ionut.easydriverentals.models.dtos.ClientDetailsDTO;
import com.ionut.easydriverentals.repositories.ClientDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateClientShouldPass() throws Exception {
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTO.builder()
                .email("VasileAndrei@gmail.com")
                .phoneNumber("0742359103")
                .country("Romania")
                .city("Suceava")
                .street("Principala nr 6")
                .build();

        ClientDTO clientDTO = ClientDTO.builder()
                .firstName("Vasile")
                .lastName("Andrei")
                .clientDetailsDTO(clientDetailsDTO)
                .build();

        MvcResult result = mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        ClientDTO clientDTOConverted = objectMapper.readValue(resultAsString, new TypeReference<>() {
        });

        assertEquals(clientDTOConverted.getFirstName(), clientDTO.getFirstName());
        assertEquals(clientDTOConverted.getLastName(), clientDTO.getLastName());
        assertEquals(clientDTOConverted.getClientDetailsDTO().getEmail(), clientDTO.getClientDetailsDTO().getEmail());
        assertEquals(clientDTOConverted.getClientDetailsDTO().getPhoneNumber(), clientDTO.getClientDetailsDTO().getPhoneNumber());
        assertEquals(clientDTOConverted.getClientDetailsDTO().getCountry(), clientDTO.getClientDetailsDTO().getCountry());
        assertEquals(clientDTOConverted.getClientDetailsDTO().getCity(), clientDTO.getClientDetailsDTO().getCity());
        assertEquals(clientDTOConverted.getClientDetailsDTO().getStreet(), clientDTO.getClientDetailsDTO().getStreet());
    }

    @Test
    void testCreateClientWithEmptyFiledShouldFail() throws Exception {
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTO.builder()
                .email("")
                .phoneNumber("0742359103")
                .country("Romania")
                .city("Suceava")
                .street("Principala nr 6")
                .build();

        ClientDTO clientDTO = ClientDTO.builder()
                .firstName("Vasile")
                .lastName("Andrei")
                .clientDetailsDTO(clientDetailsDTO)
                .build();

        MvcResult result = mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains("email"));
    }
}