package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;

public class AccommodationPoints {
    private Listing listing;
    private double value;
    private LocalDateTime loginTimestamp;

    public AccommodationPoints() {
    }

    public AccommodationPoints(Listing listing, double value, LocalDateTime loginTimestamp) {
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
        return "AccommodationPoints [listing=" + listing.getId() + ", value=" + value + ", loginTimestamp="
                + loginTimestamp
                + "]";
    }

}
