package com.ftn.sbnz.model.models;

import java.util.Date;
import com.ftn.sbnz.model.enums.BookingStatus;

public class Booking {
    private Date startDate;
    private Date endDate;
    private BookingStatus status;
    private boolean isReservation;
    private Date createdAt;

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

    @Override
    public String toString() {
        return "Booking [startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", isReservation="
                + isReservation + ", createdAt=" + createdAt + "]";
    }
}
