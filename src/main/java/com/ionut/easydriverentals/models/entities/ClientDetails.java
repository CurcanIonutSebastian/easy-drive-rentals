package com.ionut.easydriverentals.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "client_details")
public class ClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone_number", unique = true)
    private long phoneNumber;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "block")
    private String block;
    @Column(name = "stair")
    private char stair;
    @Column(name = "floor")
    private int floor;
    @Column(name = "apartment")
    private int apartment;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
}