package com.ftn.sbnz.service.dtos;

import java.util.List;

import com.ftn.sbnz.model.models.Listing;

public class RecommendedListingsDTO {
    private List<Listing> listings;

    public RecommendedListingsDTO(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

}
