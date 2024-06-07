package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.dtos.BookingRejectionNoticeDTO;
import com.ftn.sbnz.service.services.interfaces.IBookingService;
import java.util.List;
import com.ftn.sbnz.service.dtos.ReturnedBookingDTO;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private final IBookingService bookingService;

	public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
	}

	@RequestMapping(path = "/owner", method = RequestMethod.GET, produces = "application/json")
	public List<ReturnedBookingDTO> getByOwner() {
        return this.bookingService.getByOwner();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public void bookListing(@RequestBody BookingDTO dto) {
		System.out.println("Rezervisan (controller)!");
        this.bookingService.bookListing(dto);
	}

	@RequestMapping(path = "/accept", method = RequestMethod.PUT, produces = "application/json")
	public void acceptBooking(@RequestParam Long id) {
		System.out.println("Prihvacen booking (controller)!");
        this.bookingService.acceptBooking(id);
	}

	@RequestMapping(path = "/deny", method = RequestMethod.PUT, produces = "application/json")
	public void denyBooking(@RequestBody BookingRejectionNoticeDTO dto) {
		System.out.println("Odbijen booking (controller)!");
        this.bookingService.denyBooking(dto);
	}

	@RequestMapping(path = "/cancel", method = RequestMethod.PUT, produces = "application/json")
	public void cancelBookingByTraveler(@RequestParam Long bookingId) {
		System.out.println("Otkazan booking (controller)!");
        this.bookingService.cancelBookingByTraveler(bookingId);
	}
}
