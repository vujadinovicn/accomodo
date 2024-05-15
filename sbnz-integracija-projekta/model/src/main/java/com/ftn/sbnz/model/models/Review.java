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

    public Review(double rating, String comment, LocalDateTime date) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
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

    @Override
    public String toString() {
        return "Review [rating=" + rating + ", comment=" + comment + ", date=" + date + "]";
    }

}
