package com.ftn.sbnz.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.Traveler;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    public Traveler findByEmail(String email);
}
