package com.ftn.sbnz.model.models;

import java.util.Set;

public class ReviewableListings {
    private Long travelerId;
    private Set<Listing> listings;

    public ReviewableListings() {
    }

    public ReviewableListings(Long travelerId, Set<Listing> listings) {
        this.travelerId = travelerId;
        this.listings = listings;
    }

    public Long getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(Long travelerId) {
        this.travelerId = travelerId;
    }

    public Set<Listing> getListings() {
        return listings;
    }

    public void setListings(Set<Listing> listings) {
        this.listings = listings;
    }

    @Override
    public String toString() {
        return "ReviewableListings [travelerId=" + travelerId + ", listings=" + listings + "]";
    }

}
