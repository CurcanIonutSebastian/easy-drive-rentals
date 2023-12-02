package com.ionut.easydriverentals.repositories;

import com.ionut.easydriverentals.models.entities.ClientDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long> {

    @Modifying
    @Query(value = "UPDATE client_details set client_id = :clientId WHERE client_id IS NULL;", nativeQuery = true)
    void assignClientDetailsToClient(Long clientId);
}