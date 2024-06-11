package com.ftn.sbnz.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("2m")
public class ReservationDeniedEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long bookingId;
    private String reason;

    public ReservationDeniedEvent(Long bookingId, String reason) {
        super();
        this.bookingId = bookingId;
        this.reason = reason;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    
    
}