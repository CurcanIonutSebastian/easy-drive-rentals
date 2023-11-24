package com.ionut.easydriverentals.repositories;

import com.ionut.easydriverentals.models.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}