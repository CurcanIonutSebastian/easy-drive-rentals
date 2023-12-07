package com.ionut.easydriverentals.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "client_details")
public class ClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;
    @NotBlank
    @Column(name = "country")
    private String country;
    @NotBlank
    @Column(name = "city")
    private String city;
    @NotBlank
    @Column(name = "street")
    private String street;
    @Column(name = "block")
    private String block;
    @Column(name = "stair")
    private String stair;
    @Column(name = "floor")
    private int floor;
    @Column(name = "apartment")
    private int apartment;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
}