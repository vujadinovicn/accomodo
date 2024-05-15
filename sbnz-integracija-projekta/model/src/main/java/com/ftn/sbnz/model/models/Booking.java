package com.ftn.sbnz.model.models;

import java.io.Serializable;
import java.util.Date;

import com.ftn.sbnz.model.enums.BookingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;
    private Date endDate;
    private BookingStatus status;
    private boolean isReservation;
    private Date createdAt;

    @ManyToOne
    private Listing listing;
    @ManyToOne
    private Traveler traveler;

    public Booking(Date startDate, Date endDate, BookingStatus status, boolean isReservation, Date createdAt) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.isReservation = isReservation;
        this.createdAt = createdAt;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public boolean isReservation() {
        return isReservation;
    }

    public void setReservation(boolean isReservation) {
        this.isReservation = isReservation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        return "Booking [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status
                + ", isReservation=" + isReservation + ", createdAt=" + createdAt + ", listing=" + listing
                + ", traveler=" + traveler + "]";
    }

}
