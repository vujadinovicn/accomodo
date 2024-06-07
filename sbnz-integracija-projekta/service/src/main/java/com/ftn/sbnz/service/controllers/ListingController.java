package com.ftn.sbnz.service.controllers;

import java.util.List;

import javax.print.attribute.standard.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddListingDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.dtos.ListingDestinationDTO;
import com.ftn.sbnz.service.services.interfaces.IListingService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/listing")
public class ListingController {
    @Autowired
    private final IListingService listingService;

	public ListingController(IListingService listingService) {
        this.listingService = listingService;
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> addListing(@Valid @RequestBody ListingDestinationDTO dto) {
		System.out.println("e");
        // this.listingService.addListing(dto);
		return null;
	}

	@GetMapping(value = "/owner")
	public List<AddListingDTO> getListingsForOwner() {
        return this.listingService.getListingsForOwner();
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

	@RequestMapping(path = "/backward", method = RequestMethod.GET)
	public void backward() {
        this.listingService.backward();
	}
}
