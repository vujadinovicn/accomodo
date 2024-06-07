package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;
import java.util.List;

public class AccommodationRecommendationResult {
    private List<Listing> listings;
    private LocalDateTime loginTimestamp;

    public AccommodationRecommendationResult(List<Listing> listings, LocalDateTime loginTimestamp) {
        this.listings = listings;
        this.loginTimestamp = loginTimestamp;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public LocalDateTime getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(LocalDateTime loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    @Override
    public String toString() {
        return "ListingRecommendationResult [listings number=" + listings.size() + ", loginTimestamp=" + loginTimestamp
                + "]";
    }

}
