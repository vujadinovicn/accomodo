package com.ftn.sbnz.service.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.services.interfaces.IListingService;

@RestController
@RequestMapping("/api/listing")
public class ListingController {
    @Autowired
    private final IListingService listingService;

	public ListingController(IListingService listingService) {
        this.listingService = listingService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void getListing(@RequestBody GetListingDTO dto) {
        this.listingService.getById(dto);
	}

    @RequestMapping(path = "/discount", method = RequestMethod.POST)
	public void addDiscount(@RequestBody AddDiscountDTO dto) {
        this.listingService.addDiscount(dto);
	}

    @RequestMapping(path = "/review", method = RequestMethod.POST)
	public void addDiscount(@RequestBody AddReviewDTO dto) {
        this.listingService.addReview(dto);
	}
}
