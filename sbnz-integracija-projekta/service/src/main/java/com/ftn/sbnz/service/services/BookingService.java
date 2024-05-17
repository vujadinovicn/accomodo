package com.ftn.sbnz.service.services;

import java.util.Optional;
import java.util.Collection;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.enums.BookingStatus;
import com.ftn.sbnz.model.enums.EmailNotificationType;
import com.ftn.sbnz.model.events.BookingAcceptedEvent;
import com.ftn.sbnz.model.events.BookingDeniedEvent;
import com.ftn.sbnz.model.events.BookingEvent;
import com.ftn.sbnz.model.events.DiscountEmailEvent;
import com.ftn.sbnz.model.events.BookingEmailEvent;
import com.ftn.sbnz.model.models.Booking;
import com.ftn.sbnz.model.models.BookingRejectionNotice;
import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.dtos.BookingRejectionNoticeDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.mail.IMailService;
import com.ftn.sbnz.service.repositories.BookingRejectionNoticeRepository;
import com.ftn.sbnz.service.repositories.BookingRepository;
import com.ftn.sbnz.service.services.interfaces.IBookingService;
import com.ftn.sbnz.service.services.interfaces.IListingService;
import com.ftn.sbnz.service.services.interfaces.ITravelerService;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private KieSession kieSession;

    @Autowired
    private BookingRepository allBookings;

    @Autowired
    private BookingRejectionNoticeRepository allRejectionNotices;

    @Autowired
    private ITravelerService travelerService;

    @Autowired
    private IListingService listingService;

    @Autowired
    private IMailService mailService;

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
        // Owner owner = new Owner("vujadinovic74@gmail.com", "123", "Miodrag", "Vujadinovic");
        // Traveler traveler = new Traveler("vujadinovic01@gmail.com", "123", "Nemanja", "Vujadinovic");
        // allUsers.save(owner);
        // allUsers.save(traveler);
        // allUsers.flush();

        Listing listing = listingService.findById(dto.getListingId());
        Traveler traveler = travelerService.getById(dto.getTravelerId());

        Booking booking = new Booking(dto.getStartDate(), dto.getEndDate(), BookingStatus.PENDING, false);
        booking.setTraveler(traveler);
        booking.setListing(listing);
        allBookings.save(booking);
        allBookings.flush();

        BookingEvent bevent = new BookingEvent(booking.getId());
        kieSession.insert(bevent);
        int n = kieSession.fireAllRules();

        // Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(EmailNotificationEvent.class));
        // for (Object event : newEvents) {
        //     if (event instanceof EmailNotificationEvent) {
        //         EmailNotificationEvent emailEvent = (EmailNotificationEvent) event;
        //         System.out.println(emailEvent.getListingName());
        //     }
        // }

        System.out.println("Number of rules fired: " + n);
    }


    @Override
    public void acceptBooking(Long id) {
        Booking booking = getById(id);

        BookingAcceptedEvent baevent = new BookingAcceptedEvent(id);
        kieSession.insert(baevent);
        kieSession.insert(booking);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired AFTER ACCEPTENCE: " + n);

        Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(BookingEmailEvent.class));
        for (Object event : newEvents) {
            if (event instanceof BookingEmailEvent) {
                BookingEmailEvent emailEvent = (BookingEmailEvent) event;
                if (emailEvent.getType() == EmailNotificationType.BOOKING_ACCEPTED){
                    mailService.sendBookingEmail(emailEvent);
                    System.out.println("Email has been sent!");
                }
            }
        }

        allBookings.save(booking);
        allBookings.flush();
        System.out.println(booking.getStatus());
    }


    @Override
    public void denyBooking(BookingRejectionNoticeDTO dto) {
        Booking booking = getById(dto.getBookingId());

        BookingRejectionNotice notice = new BookingRejectionNotice(dto.getReason(), booking);
        allRejectionNotices.save(notice);
        allRejectionNotices.flush();

        BookingDeniedEvent baevent = new BookingDeniedEvent(dto.getBookingId(), dto.getReason());
        kieSession.insert(baevent);
        kieSession.insert(booking);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired AFTER DENIAL: " + n);

        Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(BookingEmailEvent.class));
        for (Object event : newEvents) {
            if (event instanceof BookingEmailEvent) {
                BookingEmailEvent emailEvent = (BookingEmailEvent) event;
                if (emailEvent.getType() == EmailNotificationType.BOOKING_DENIED){
                    mailService.sendBookingEmail(emailEvent);
                    System.out.println("Email has been sent!");
                }
            }
        }

        

        allBookings.save(booking);
        allBookings.flush();
    }

}
