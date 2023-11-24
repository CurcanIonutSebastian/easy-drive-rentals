package com.ionut.easydriverentals.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @OneToOne(mappedBy = "client")
    private ClientDetails clientDetails;

    @OneToOne(mappedBy = "client")
    private Rental rental;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> histories;

    @ManyToMany
    @JoinTable(
            name = "client_favorite_cars",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> clientFavoriteCars= new ArrayList<>();
}