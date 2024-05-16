package com.ftn.sbnz.service.dtos;

import java.time.LocalDateTime;

public class BookingRejectionNoticeDTO {
    private String reason;
    private LocalDateTime date;
    public Long bookingId;

    public BookingRejectionNoticeDTO(Long bookingId, String reason) {
        this.reason = reason;
        this.bookingId = bookingId;
        this.date = LocalDateTime.now();
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    
}
