package com.ionut.easydriverentals.repositories;

import com.ionut.easydriverentals.models.entities.Rental;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Modifying
    @Query(value = "UPDATE rentals set client_id = :clientId, car_id = :carId WHERE client_id IS NULL AND car_id IS NULL;", nativeQuery = true)
    void assignClientAndCarIdToRental(Long clientId, Long carId);
}