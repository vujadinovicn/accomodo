package com.ftn.sbnz.model.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class BookingRejectionNotice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;
    private LocalDateTime date;
    @OneToOne()
    public Booking booking;

    public BookingRejectionNotice(String reason, Booking booking) {
        this.reason = reason;
        this.date = LocalDateTime.now();
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getLocalDateTime() {
        return date;
    }

    public void setLocalDateTime(LocalDateTime date) {
        this.date = date;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "BookingRejectionNotice [id=" + id + ", reason=" + reason + ", date=" + date + ", booking=" + booking
                + "]";
    }

}
