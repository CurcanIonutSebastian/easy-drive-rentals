package com.ionut.easydriverentals.integration_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionut.easydriverentals.models.dtos.CarDTO;
import com.ionut.easydriverentals.models.dtos.ClientDTO;
import com.ionut.easydriverentals.models.enums.CarStatus;
import jakarta.transaction.Transactional;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCarShouldPass() throws Exception {
        CarDTO carDTO = CarDTO.builder()
                .brand("X5")
                .model("BMW")
                .productYear(2008)
                .pricePerDay(350)
                .capacity(2498)
                .build();

        MvcResult result = mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        CarDTO carDTOConverted = objectMapper.readValue(resultAsString, new TypeReference<>() {
        });

        assertEquals(carDTOConverted.getBrand(), carDTO.getBrand());
        assertEquals(carDTOConverted.getModel(), carDTO.getModel());
        assertEquals(carDTOConverted.getPricePerDay(), carDTO.getPricePerDay());
        assertEquals(carDTOConverted.getProductYear(), carDTO.getProductYear());
        assertEquals(carDTOConverted.getCapacity(), carDTO.getCapacity());
        assertNotNull(carDTOConverted.getCarStatus());
    }

    @Test
    void testCreateCarWithEmptyFiledShouldFail() throws Exception {
        CarDTO carDTO = CarDTO.builder()
                .brand("")
                .model("BMW")
                .productYear(2008)
                .pricePerDay(350)
                .capacity(2498)
                .build();

        MvcResult result = mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains("brand"));
    }
}
