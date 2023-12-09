package com.ionut.easydriverentals.models.dtos;

import lombok.Data;

@Data
public class UpdateClientDetailsDTO {

    private Long id;
    private String phoneNumber;
    private String email;
    private String country;
    private String city;
    private String street;
    private String block;
    private String stair;
    private int floor;
    private int apartment;
}
