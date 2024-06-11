package com.ftn.sbnz.model.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.kie.api.definition.type.Role;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Traveler;

@Role(Role.Type.EVENT)
@Entity
public class ListingViewedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime timestamp;

    @ManyToOne
    private Traveler traveler;

    @ManyToOne
    private Listing listing;

    public ListingViewedEvent() {}

    public ListingViewedEvent(Traveler traveler, Listing listing) {
        this.traveler = traveler;
        this.listing = listing;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public Listing getListing() {
        return listing;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}