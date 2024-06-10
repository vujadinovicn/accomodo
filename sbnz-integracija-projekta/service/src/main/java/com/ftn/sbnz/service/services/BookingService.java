package com.ftn.sbnz.service.services;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import com.ftn.sbnz.model.events.ReservationAcceptedEvent;
import com.ftn.sbnz.model.events.ReservationCanceledEvent;
import com.ftn.sbnz.model.events.ReservationDeniedEvent;
import com.ftn.sbnz.model.events.ReservationEvent;
import com.ftn.sbnz.model.events.BookingEmailEvent;
import com.ftn.sbnz.model.models.Booking;
import com.ftn.sbnz.model.models.BookingRejectionNotice;
import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.dtos.BookingRejectionNoticeDTO;
import com.ftn.sbnz.service.dtos.ReturnedBookingDTO;
import com.ftn.sbnz.service.mail.IMailService;
import com.ftn.sbnz.service.repositories.BookingRejectionNoticeRepository;
import com.ftn.sbnz.service.repositories.BookingRepository;
import com.ftn.sbnz.service.repositories.TravelerRepository;
import com.ftn.sbnz.service.services.interfaces.IBookingService;
import com.ftn.sbnz.service.services.interfaces.IListingService;
import com.ftn.sbnz.service.services.interfaces.ITravelerService;
import com.ftn.sbnz.service.services.interfaces.IUserService;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private KieSession kieSession;

    @Autowired
    private BookingRepository allBookings;

    @Autowired
    private TravelerRepository allTravelers;

    @Autowired
    private BookingRejectionNoticeRepository allRejectionNotices;

    @Autowired
    private ITravelerService travelerService;

    @Autowired
    private IListingService listingService;

    @Autowired
    private IMailService mailService;

    @Autowired
    private IUserService userService;

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
        Listing listing = listingService.findById(dto.getListingId());
        Traveler traveler = travelerService.getById(dto.getTravelerId());

        Booking booking = new Booking(dto.getStartDate(), dto.getEndDate(), BookingStatus.PENDING, dto.isReservation());
        booking.setTraveler(traveler);
        booking.setListing(listing);
        allBookings.save(booking);
        allBookings.flush();

        kieSession.setGlobal("dateNow", new Date());
        kieSession.insert(booking);

        if (dto.isReservation()){
            System.out.println("jeste reyervacija");
            ReservationEvent revent = new ReservationEvent(booking.getId());
            kieSession.insert(revent);
        } else {
            BookingEvent bevent = new BookingEvent(booking.getId());
            kieSession.insert(bevent);
        }

        int n = kieSession.fireAllRules();

        // Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(EmailNotificationEvent.class));
        // for (Object event : newEvents) {
        //     if (event instanceof EmailNotificationEvent) {
        //         EmailNotificationEvent emailEvent = (EmailNotificationEvent) event;
        //         System.out.println(emailEvent.getListingName());
        //     }
        // }
        
        Collection<?> bookings = kieSession.getObjects(new ClassObjectFilter(Booking.class));
        for (Object bb : bookings) {
            if (booking instanceof Booking) {
                Booking b = (Booking) bb;
                allBookings.save(b);
            }
        }
        allBookings.flush();
        System.out.println("Number of rules fired: " + n);
    }


    @Override
    public void acceptBooking(Long id) {
        Booking booking = getById(id);

        if (booking.isReservation()){
            ReservationAcceptedEvent revent = new ReservationAcceptedEvent(id);
            kieSession.insert(revent);
        } else {
            BookingAcceptedEvent baevent = new BookingAcceptedEvent(id);
            kieSession.insert(baevent);
        }
        // kieSession.insert(booking);
        kieSession.setGlobal("dateNow", new Date());
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
        Collection<?> bookings = kieSession.getObjects(new ClassObjectFilter(Booking.class));
        for (Object bb : bookings) {
            if (booking instanceof Booking) {
                Booking b = (Booking) bb;
                allBookings.save(b);
            }
        }
        allBookings.flush();
        // System.out.println(booking.getStatus());
    }


    @Override
    public void denyBooking(BookingRejectionNoticeDTO dto) {
        Booking booking = getById(dto.getBookingId());

        BookingRejectionNotice notice = new BookingRejectionNotice(dto.getReason(), booking);
        allRejectionNotices.save(notice);
        allRejectionNotices.flush();

        if (booking.isReservation()){
            ReservationDeniedEvent rEvent = new ReservationDeniedEvent(dto.getBookingId(), dto.getReason());
            kieSession.insert(rEvent);
        } else {
            BookingDeniedEvent baevent = new BookingDeniedEvent(dto.getBookingId(), dto.getReason());
            kieSession.insert(baevent);
        }
        
        // kieSession.insert(booking);
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

        Collection<?> bookings = kieSession.getObjects(new ClassObjectFilter(Booking.class));
        for (Object bb : bookings) {
            if (booking instanceof Booking) {
                Booking b = (Booking) bb;
                allBookings.save(b);
            }
        }
        allBookings.flush();
    }

    @Override
    public void cancelBookingByTraveler(Long bookingId) {
        Booking booking = getById(bookingId);

        ReservationCanceledEvent rCanceledEvent = new ReservationCanceledEvent(booking, new Date());
        kieSession.insert(rCanceledEvent);
        kieSession.insert(booking);

        Traveler travelerInMemory = null;
        for (Object object : kieSession.getObjects(new ClassObjectFilter(Traveler.class))) {
            Traveler traveler = (Traveler) object;
            if (traveler.getId() == booking.getTraveler().getId()) {
                travelerInMemory = traveler;
                break;
            }
        }
        if (travelerInMemory != null) {
            kieSession.delete(kieSession.getFactHandle(travelerInMemory));
        }
        kieSession.insert(booking.getTraveler());
        kieSession.setGlobal("dateNow", new Date());

        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired AFTER CANCELATION: " + n);

        Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(BookingEmailEvent.class));
        for (Object event : newEvents) {
            if (event instanceof BookingEmailEvent) {
                BookingEmailEvent emailEvent = (BookingEmailEvent) event;
                if (emailEvent.getType() == EmailNotificationType.BOOKING_RESCHEDULE){
                    mailService.sendBookingEmail(emailEvent);
                    System.out.println("Email has been sent!");
                }
            }
        }

        allBookings.save(booking);
        allBookings.flush();

        allTravelers.flush();
    }

    @Override
    public List<ReturnedBookingDTO> getByOwner() {
        List<Booking> bookings = allBookings.findBookingsByOwnerId(userService.getCurrentUser().getId());
        List<ReturnedBookingDTO> dtos = getDtos(bookings);
        return dtos;
    }

    @Override
    public List<ReturnedBookingDTO> getByTraveler() {
        List<Booking> bookings = allBookings.findBookingsByTravelerId(userService.getCurrentUser().getId());
        List<ReturnedBookingDTO> dtos = getDtos(bookings);
        return dtos;
    }

    private List<ReturnedBookingDTO> getDtos(List<Booking> bookings) {
        List<ReturnedBookingDTO> dtos = new ArrayList<>();
        for (Booking booking : bookings) {
            String status = "";
            BookingStatus bs = booking.getStatus();
            if (bs == BookingStatus.ACCEPTED)
                status = "ACCEPTED";
            if (bs == BookingStatus.CANCELED)
                status = "CANCELED";
            if (bs == BookingStatus.DENIED)
                status = "DENIED";
            if (bs == BookingStatus.PENDING)
                status = "PENDING";

            ReturnedBookingDTO dto = new ReturnedBookingDTO(booking.getId(), booking.getTraveler().getId(),
                            booking.getListing().getOwner().getId(),
                        booking.getTraveler().getName() + " " + booking.getTraveler().getLastname(),
                    booking.getListing().getOwner().getName() + " " + booking.getListing().getOwner().getLastname(),
                booking.getListing().getTitle(), status, booking.getStartDate(), booking.getEndDate());
            // (Long bookingId, Long travelerId, Long ownerId, String travelerName, String ownerName,
            // String listingName, String status, Date startDate, Date endDate)
            dtos.add(dto);
        }
        return dtos;
    }

}
