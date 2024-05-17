package com.ftn.sbnz.service.services.interfaces;

import com.ftn.sbnz.model.models.Booking;
import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.dtos.BookingRejectionNoticeDTO;

public interface IBookingService {

    public Booking getById(long id);

    public void bookListing(BookingDTO dto);

    public void acceptBooking(Long id);

    public void denyBooking(BookingRejectionNoticeDTO dto);
}
