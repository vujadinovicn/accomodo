package com.ftn.sbnz.model.models;

import java.util.Date;
import jakarta.persistence.OneToOne;

public class BookingRejectionNotice {
    private String reason;
    private Date date;
    @OneToOne()
    public Booking booking;

    public BookingRejectionNotice(String reason, Date date, Booking booking) {
        this.reason = reason;
        this.date = date;
        this.booking = booking;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "BookingRejectionNotice [reason=" + reason + ", date=" + date + "]";
    }
    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    
}
