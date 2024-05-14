package com.ftn.sbnz.model.models;

import java.util.Date;
import jakarta.persistence.OneToOne;

public class CancelBookingNotice {
    private Date createdAt;
    @OneToOne()
    public Booking booking;

    public CancelBookingNotice(Date date, Booking booking) {
        this.createdAt = date;
        this.booking = booking;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "CancelBookingNotice [createdAt=" + createdAt + "]";
    }
}
