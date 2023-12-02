package com.ionut.easydriverentals.repositories;

import com.ionut.easydriverentals.models.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}