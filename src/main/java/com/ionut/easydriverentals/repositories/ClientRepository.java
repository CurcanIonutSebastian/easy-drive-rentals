package com.ionut.easydriverentals.repositories;

import com.ionut.easydriverentals.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}