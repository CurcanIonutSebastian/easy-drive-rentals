package com.ionut.easydriverentals.models.entities;

import com.ionut.easydriverentals.models.enums.CarStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @Column(name = "brand")
    private String brand;
    @NotBlank
    @Column(name = "model")
    private String model;
    @Min(0)
    @Column(name = "capacity")
    private int capacity;
    @Min(1900)
    @Column(name = "product_year")
    private int productYear;
    @Min(0)
    @Column(name = "price_per_day")
    private double pricePerDay;
    @Column(name = "status")
    private CarStatus carStatus;

    @OneToOne(mappedBy = "car")
    private Rental rental;

    @ManyToMany(mappedBy = "clientFavoriteCars")
    private List<Client> clientsWithFavorite = new ArrayList<>();
}