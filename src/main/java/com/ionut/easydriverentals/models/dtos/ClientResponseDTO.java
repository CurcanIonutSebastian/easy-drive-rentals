package com.ionut.easydriverentals.models.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientResponseDTO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private ClientDetailsDTO clientDetails;
}