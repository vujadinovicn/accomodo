package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;

public class AccommodationRecommendation {
    private Listing listing;
    private double value;
    private LocalDateTime loginTimestamp;

    public AccommodationRecommendation() {
    }

    public AccommodationRecommendation(Listing listing, double value, LocalDateTime loginTimestamp) {
        this.listing = listing;
        this.value = value;
        this.loginTimestamp = loginTimestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(LocalDateTime loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return "AccommodationRecommendation [listing=" + listing.getId() + ", value=" + value + ", loginTimestamp="
                + loginTimestamp + "]";
    }

}
