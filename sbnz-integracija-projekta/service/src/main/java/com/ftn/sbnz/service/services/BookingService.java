package com.ftn.sbnz.service.services;

import java.util.Optional;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.enums.BookingStatus;
import com.ftn.sbnz.model.events.BookingAcceptedEvent;
import com.ftn.sbnz.model.events.BookingDeniedEvent;
import com.ftn.sbnz.model.models.Booking;
import com.ftn.sbnz.model.models.BookingRejectionNotice;
import com.ftn.sbnz.model.models.Owner;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.dtos.BookingRejectionNoticeDTO;
import com.ftn.sbnz.service.repositories.BookingRejectionNoticeRepository;
import com.ftn.sbnz.service.repositories.BookingRepository;
import com.ftn.sbnz.service.repositories.UserRepository;
import com.ftn.sbnz.service.services.interfaces.IBookingService;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private KieSession kieSession;

    @Autowired
    private BookingRepository allBookings;

    @Autowired
    private BookingRejectionNoticeRepository allRejectionNotices;

    @Autowired
    private UserRepository allUsers;

    @Override
	public Booking getById(long id) {
		Optional<Booking> found = allBookings.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking does not exist!");

		}
		return found.get();
	}

    @Override
    public void bookListing(BookingDTO dto) {
        Booking booking = new Booking(dto.getStartDate(), dto.getEndDate(), BookingStatus.PENDING, false);
        allBookings.save(booking);
        allBookings.flush();
        Owner owner = new Owner("vujadinovic74@gmail.com", "123", "Miodrag", "Vujadinovic");
        Traveler traveler = new Traveler("vujadinovic01@gmail.com", "123", "Nemanja", "Vujadinovic");
        allUsers.save(owner);
        allUsers.save(traveler);
        allUsers.flush();
        kieSession.insert(booking);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);
    }


    @Override
    public void acceptBooking(Long id) {
        Booking booking = getById(id);
        if (booking.getStatus() == BookingStatus.PENDING){
            booking.setStatus(BookingStatus.ACCEPTED);
            allBookings.save(booking);
            allBookings.flush();

            BookingAcceptedEvent baevent = new BookingAcceptedEvent(id);
            kieSession.insert(baevent);
            int n = kieSession.fireAllRules();
            System.out.println("Number of rules fired AFTER ACCEPTENCE: " + n);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking is not in pending status!");
        }
    }


    @Override
    public void denyBooking(BookingRejectionNoticeDTO dto) {
        Booking booking = getById(dto.getBookingId());
        if (booking.getStatus() == BookingStatus.PENDING){
            booking.setStatus(BookingStatus.DENIED);
            allBookings.save(booking);
            allBookings.flush();

            BookingRejectionNotice notice = new BookingRejectionNotice(dto.getReason(), booking);
            allRejectionNotices.save(notice);
            allRejectionNotices.flush();

            BookingDeniedEvent baevent = new BookingDeniedEvent(dto.getBookingId(), dto.getReason());
            kieSession.insert(baevent);
            int n = kieSession.fireAllRules();
            System.out.println("Number of rules fired AFTER DENIAL: " + n);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking is not in pending status!");
        }
    }

}
