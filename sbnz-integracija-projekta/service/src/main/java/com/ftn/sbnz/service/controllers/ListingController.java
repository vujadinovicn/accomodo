package com.ftn.sbnz.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddListingDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.dtos.ListingDestinationDTO;
import com.ftn.sbnz.service.dtos.MessageDTO;
import com.ftn.sbnz.service.dtos.RecommendedListingsDTO;
import com.ftn.sbnz.service.dtos.ReturnedListingDTO;
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
	public List<ReturnedListingDTO> getListingsForOwner() {
        return this.listingService.getListingsForOwner();
	}

	@GetMapping(value = "/all")
	public List<ReturnedListingDTO> getListingsForTraveler() {
        return this.listingService.getAll();
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
	public ResponseEntity<?> addReview(@RequestBody AddReviewDTO dto) {
        System.out.println("REVIEWWWWWWWWWWWW: " + dto);
		try {
			this.listingService.addReview(dto);
			return new ResponseEntity<MessageDTO>(new MessageDTO("Review added successfully."), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(path = "/backward", method = RequestMethod.GET)
	public void backward() {
        this.listingService.backward();
	}

	@RequestMapping(path = "/recommendations", method = RequestMethod.GET)
	public ResponseEntity<?> getRecommendations(@RequestParam Long id) {
		try {
			List<Listing> recs = this.listingService.getListingRecommendations(id);
			return new ResponseEntity<RecommendedListingsDTO>(new RecommendedListingsDTO(recs), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Fetching listing recs failed!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
