package com.ftn.sbnz.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.dtos.ListingDTO;
import com.ftn.sbnz.service.dtos.RecommendedListingsDTO;
import com.ftn.sbnz.service.dtos.TokenDTO;
import com.ftn.sbnz.service.services.interfaces.IListingService;

@RestController
@RequestMapping("/api/listing")
public class ListingController {
    @Autowired
    private final IListingService listingService;

	public ListingController(IListingService listingService) {
        this.listingService = listingService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addListing(@RequestBody ListingDTO dto) {
        this.listingService.addListing(dto);
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
