package com.ionut.easydriverentals.repositories;

import com.ionut.easydriverentals.models.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}