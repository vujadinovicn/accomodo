package com.ftn.sbnz.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("2m")
public class BookingAcceptedEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long bookingId;

    public BookingAcceptedEvent(Long bookingId) {
        super();
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long id) {
        this.bookingId = id;
    }
}
