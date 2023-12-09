package com.ionut.easydriverentals.models.dtos;

import lombok.Data;

@Data
public class UpdateClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private UpdateClientDetailsDTO updateClientDetailsDTO;
}
