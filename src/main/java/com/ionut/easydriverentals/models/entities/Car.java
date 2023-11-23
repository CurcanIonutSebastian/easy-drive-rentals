package com.ionut.easydriverentals.models.entities;

import com.ionut.easydriverentals.enums.CarStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "product_year")
    private int productYear;
    @Column(name = "price_per_day")
    private double pricePerDay;
    @Column(name = "status")
    private CarStatus carStatus;

    @OneToOne(mappedBy = "car")
    private Rental rental;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> histories;

    @ManyToMany(mappedBy = "historyOfRentals")
    private List<Client> clientsWithRentals = new ArrayList<>();
}