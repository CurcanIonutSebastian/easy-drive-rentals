package com.ionut.easydriverentals.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ClientDetailsDTO implements Serializable {

    private Long id;
    private long phoneNumber;
    private String email;
    private String country;
    private String city;
    private String street;
    private String block;
    private String stair;
    private int floor;
    private int apartment;
}