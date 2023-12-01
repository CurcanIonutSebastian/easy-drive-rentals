package com.ionut.easydriverentals.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private ClientDetailsDTO clientDetailsDTO;
}