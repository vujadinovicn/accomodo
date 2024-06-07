package com.ftn.sbnz.model.events;

import java.time.LocalDateTime;

import org.kie.api.definition.type.Role;

import com.ftn.sbnz.model.models.Traveler;

@Role(Role.Type.EVENT)
public class FetchListingRecomendationEvent {
    private Traveler traveler;
    private LocalDateTime timestamp;

    public FetchListingRecomendationEvent(Traveler traveler, LocalDateTime timestamp) {
        this.traveler = traveler;
        this.timestamp = timestamp;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TravelerLoggedInEvent [traveler=" + traveler.getEmail() + ", timestamp=" + timestamp + "]";
    }

}
