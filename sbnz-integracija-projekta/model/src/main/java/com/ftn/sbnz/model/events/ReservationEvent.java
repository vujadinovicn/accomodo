package com.ftn.sbnz.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("2m")
public class ReservationEvent implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long bookingId;
    private String emailTo;
    private String emailMessage;

    public ReservationEvent(Long bookingId) {
        super();
        this.bookingId = bookingId;
    }

    public ReservationEvent(Long bookingId, String emailTo, String message) {
        this.bookingId = bookingId;
        this.emailTo = emailTo;
        this.emailMessage = message;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long id) {
        this.bookingId = id;
    }
    
}
