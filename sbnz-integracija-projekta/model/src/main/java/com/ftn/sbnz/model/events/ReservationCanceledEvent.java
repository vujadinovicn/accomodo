package com.ftn.sbnz.model.events;

import java.util.Date;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import com.ftn.sbnz.model.models.Booking;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class ReservationCanceledEvent {
    private Booking booking;
    private Date timestamp;

    public ReservationCanceledEvent(Booking booking, Date timestamp) {
        this.booking = booking;
        this.timestamp = timestamp;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    
}
