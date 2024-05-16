package com.ftn.sbnz.service.dtos;

import java.time.LocalDateTime;
import com.ftn.sbnz.model.enums.BookingStatus;

public class BookingDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BookingStatus status;
    private boolean isReservation;
    private Long listingId;
    private Long travelerId;

    public BookingDTO(LocalDateTime startDate, LocalDateTime endDate, BookingStatus status, boolean isReservation,
            Long listingId, Long travelerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.isReservation = isReservation;
        this.listingId = listingId;
        this.travelerId = travelerId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
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
