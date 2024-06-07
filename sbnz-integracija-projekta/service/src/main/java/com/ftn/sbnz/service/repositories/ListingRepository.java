package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.Listing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findAllByOwnerId(Long ownerId);
}


