package com.ftn.sbnz.model.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rating;
    private String comment;
    private LocalDateTime date;

    @ManyToOne
    private Listing listing;
    @ManyToOne
    private Traveler traveler;

    public Review() {
    }

    public Review(double rating, String comment, LocalDateTime date) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Review(double rating, String comment, LocalDateTime date, Listing listing, Traveler traveler) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.listing = listing;
        this.traveler = traveler;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    @Override
    public String toString() {
        return "Review [rating=" + rating + ", comment=" + comment + ", date=" + date + "]";
    }

}
