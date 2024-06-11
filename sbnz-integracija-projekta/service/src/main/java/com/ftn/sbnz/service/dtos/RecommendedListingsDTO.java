package com.ftn.sbnz.service.dtos;

import java.util.List;


public class RecommendedListingsDTO {
    private List<ReturnedListingDTO> listings;

    public RecommendedListingsDTO(List<ReturnedListingDTO> listings) {
        this.listings = listings;
    }

    public List<ReturnedListingDTO> getListings() {
        return listings;
    }

    public void setListings(List<ReturnedListingDTO> listings) {
        this.listings = listings;
    }

}
