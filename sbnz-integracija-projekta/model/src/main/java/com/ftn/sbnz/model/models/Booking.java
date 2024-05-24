package com.ftn.sbnz.model.models;

import java.io.Serializable;
import java.util.Date;

import com.ftn.sbnz.model.enums.BookingStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

    public Booking(){
        this.status = BookingStatus.PENDING;
    }
    public Booking(Date startDate, Date endDate, BookingStatus status, boolean isReservation) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.isReservation = isReservation;
        this.createdAt = new Date();
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
        System.out.println(this.id);
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
