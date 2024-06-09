package com.ftn.sbnz.service.dtos;

public class AddReviewDTO {
    private long listingId;
    private long travelerId;
    private double rating;
    private String comment;

    public AddReviewDTO() {
    }

    public AddReviewDTO(long listingId, long travelerId, double rating, String comment) {
        this.listingId = listingId;
        this.travelerId = travelerId;
        this.rating = rating;
        this.comment = comment;
    }

    public long getListingId() {
        return listingId;
    }

    public void setListingId(long listingId) {
        this.listingId = listingId;
    }

    public long getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(long travelerId) {
        this.travelerId = travelerId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "AddReviewDTO [listingId=" + listingId + ", travelerId=" + travelerId + ", rating=" + rating
                + ", comment=" + comment + "]";
    }

}
