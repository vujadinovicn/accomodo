package com.ftn.sbnz.service.dtos;

import java.time.LocalDateTime;

public class ReturnedReviewDTO {
    private Long id;

    private double rating;
    private String comment;
    private LocalDateTime date;

    private Long listingId;
    private Long travelerId;
    private String travelerFullName;

    public ReturnedReviewDTO() {
    }

    public ReturnedReviewDTO(Long id, double rating, String comment, LocalDateTime date, Long listingId,
            Long travelerId, String travelerFullName) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.listingId = listingId;
        this.travelerId = travelerId;
        this.travelerFullName = travelerFullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public Long getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(Long travelerId) {
        this.travelerId = travelerId;
    }

    public String getTravelerFullName() {
        return travelerFullName;
    }

    public void setTravelerFullName(String travelerFullName) {
        this.travelerFullName = travelerFullName;
    }

}
