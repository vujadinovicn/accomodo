package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.dtos.BookingRejectionNoticeDTO;
import com.ftn.sbnz.service.services.interfaces.IBookingService;


@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private final IBookingService bookingService;

	public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
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
}