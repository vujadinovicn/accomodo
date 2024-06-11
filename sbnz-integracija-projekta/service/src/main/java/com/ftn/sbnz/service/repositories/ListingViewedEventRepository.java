package com.ftn.sbnz.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.events.ListingViewedEvent;

@Repository
public interface ListingViewedEventRepository extends JpaRepository<ListingViewedEvent, Long> {
    
}
