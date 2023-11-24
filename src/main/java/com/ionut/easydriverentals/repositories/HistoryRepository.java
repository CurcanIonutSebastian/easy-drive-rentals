package com.ionut.easydriverentals.repositories;

import com.ionut.easydriverentals.models.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}