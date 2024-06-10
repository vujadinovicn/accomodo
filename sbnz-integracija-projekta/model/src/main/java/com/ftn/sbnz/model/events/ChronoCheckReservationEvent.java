package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Role;


@Role(Role.Type.EVENT)
public class ChronoCheckReservationEvent {
    private Long bookingId;
    private boolean sentEmail;

    
    public ChronoCheckReservationEvent(Long bookingId, boolean sentEmail) {
        this.bookingId = bookingId;
        this.sentEmail = sentEmail;
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public boolean isSentEmail() {
        return sentEmail;
    }
    public void setSentEmail(boolean sentEmail) {
        this.sentEmail = sentEmail;
    }

    
}
