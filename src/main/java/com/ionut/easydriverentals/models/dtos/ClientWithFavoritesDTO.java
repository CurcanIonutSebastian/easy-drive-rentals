package com.ionut.easydriverentals.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientWithFavoritesDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<CarDTO> favoriteCars;
}