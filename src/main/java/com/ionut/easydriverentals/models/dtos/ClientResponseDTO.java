package com.ionut.easydriverentals.models.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private ClientDetailsDTO clientDetails;
    private List<HistoryClientResponseDTO> clientHistory;
}