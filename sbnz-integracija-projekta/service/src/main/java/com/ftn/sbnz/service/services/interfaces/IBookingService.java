package com.ftn.sbnz.service.services.interfaces;

import java.util.List;

import com.ftn.sbnz.model.models.Booking;
import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.dtos.ReturnedBookingDTO;
import com.ftn.sbnz.service.dtos.BookingRejectionNoticeDTO;

public interface IBookingService {

    public Booking getById(long id);

    public void bookListing(BookingDTO dto);

    public void acceptBooking(Long id);

    public void denyBooking(BookingRejectionNoticeDTO dto);

    public void cancelBookingByTraveler(Long bookingId);

    public List<ReturnedBookingDTO> getByOwner();

    public List<ReturnedBookingDTO> getByTraveler();
}
