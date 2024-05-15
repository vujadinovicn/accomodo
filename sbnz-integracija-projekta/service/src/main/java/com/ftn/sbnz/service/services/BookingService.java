package com.ftn.sbnz.service.services;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.Booking;
import com.ftn.sbnz.model.models.Owner;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.repositories.UserRepository;
import com.ftn.sbnz.service.services.interfaces.IBookingService;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private KieSession kieSession;

    // @Autowired
    // private final BookingRepository allBookings;

    @Autowired
    private UserRepository allUsers;


    @Override
    public void bookListing(BookingDTO dto) {
        Booking booking = new Booking();
        booking.setId((long)64);
        // allBookings.save(booking);
        // allBookings.flush();
        Owner owner = new Owner("vujadinovic74@gmail.com", "123", "Miodrag", "Vujadinovic");
        Traveler traveler = new Traveler("vujadinovic01@gmail.com", "123", "Nemanja", "Vujadinovic");
        allUsers.save(owner);
        allUsers.save(traveler);
        allUsers.flush();
        kieSession.insert(booking);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);
    }

}
