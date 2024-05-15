package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.events.Item;
import com.ftn.sbnz.service.SampleAppService;
import com.ftn.sbnz.service.dtos.BookingDTO;
import com.ftn.sbnz.service.services.interfaces.IBookingService;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private final IBookingService bookingService;

	public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public void bookListing(@RequestBody BookingDTO dto) {
		System.out.println("rezervisano!");
        this.bookingService.bookListing(dto);
	}
}
