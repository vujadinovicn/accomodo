package com.ftn.sbnz.model.models;

import java.util.HashSet;
import java.util.Set;

public class ListingAccumulator {
    private Set<Long> listings = new HashSet<>();

    public Set<Long> getListings() {
        return listings;
    }

    public void addListing(Long id) {
        this.listings.add(id);
    }
}

