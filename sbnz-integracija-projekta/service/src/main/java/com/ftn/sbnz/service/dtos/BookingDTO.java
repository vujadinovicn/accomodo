package com.ftn.sbnz.service.dtos;

import java.util.Date;
import com.ftn.sbnz.model.enums.BookingStatus;

public class BookingDTO {
    private Date startDate;
    private Date endDate;
    private BookingStatus status;
    private boolean isReservation;
    private Long listingId;
    private Long travelerId;

    public BookingDTO(Date startDate, Date endDate, BookingStatus status, boolean isReservation,
            Long listingId, Long travelerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.isReservation = isReservation;
        this.listingId = listingId;
        this.travelerId = travelerId;
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

    
}
