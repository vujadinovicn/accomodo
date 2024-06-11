package com.ftn.sbnz.service.controllers;

import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddListingDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.dtos.ListingDestinationDTO;
import com.ftn.sbnz.service.dtos.MessageDTO;
import com.ftn.sbnz.service.dtos.RecommendedListingsDTO;
import com.ftn.sbnz.service.dtos.ReturnedDiscountDTO;
import com.ftn.sbnz.service.dtos.ReturnedListingDTO;
import com.ftn.sbnz.service.dtos.ReturnedReviewDTO;
import com.ftn.sbnz.service.services.interfaces.IListingService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/listing")
public class ListingController {
    @Autowired
    private final IListingService listingService;

	// @Autowired
	// private KieBase kieBase;

	public ListingController(IListingService listingService) {
        this.listingService = listingService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> addListing(@RequestBody AddListingDTO dto) {
		// System.out.println("e");
        this.listingService.addListing(dto);
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
	public void getListing(@RequestParam Long listingId, @RequestParam Long userId) {
		GetListingDTO dto = new GetListingDTO(userId, listingId);
        this.listingService.getById(dto);
	}

    @RequestMapping(path = "/discount", method = RequestMethod.POST)
	public ResponseEntity<?>  addDiscount(@RequestBody AddDiscountDTO dto) {
		try {
		return new ResponseEntity<ReturnedDiscountDTO>(this.listingService.addDiscount(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(path = "/discount", method = RequestMethod.DELETE)
	public ResponseEntity<?>  addDiscount(@RequestParam Long id) {
		try {
			this.listingService.deleteDiscount(id);
			return new ResponseEntity<MessageDTO>(new MessageDTO("Discount deleted successfully."), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
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

	@RequestMapping(path = "/review", method = RequestMethod.GET)
	public ResponseEntity<?> getReviews(@RequestParam Long id) {
		try {
			List<ReturnedReviewDTO> reviews = this.listingService.getReviews(id);
			return new ResponseEntity<List<ReturnedReviewDTO>>(reviews, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(path = "/backward", method = RequestMethod.GET)
	public List<ReturnedListingDTO> backward(@RequestParam String location) {
		// KieSession forwardKsession = kieBase.newKieSession();
		// Location l = new Location();
		// l.setAddress("Zmaj Ognjena Vuka");
		// int n = forwardKsession.fireAllRules();
        return this.listingService.backward(location);
		// return null;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestParam Long id) {
        this.listingService.delete(id);
	}

	@RequestMapping(path = "/recommendations", method = RequestMethod.GET)
	public ResponseEntity<?> getRecommendations(@RequestParam Long id) {
		try {
			List<ReturnedListingDTO> recs = this.listingService.getListingRecommendations(id);
			return new ResponseEntity<RecommendedListingsDTO>(new RecommendedListingsDTO(recs), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/filter-rating", method = RequestMethod.GET)
	public List<ReturnedListingDTO> filterByRating(@RequestParam int rating) {
		return listingService.filterByRating(rating);
	}

	@RequestMapping(path = "/filter-price", method = RequestMethod.GET)
	public List<ReturnedListingDTO> filterByPrice(@RequestParam double min, @RequestParam double max) {
		return listingService.filterByPrice(min, max);
	}
}
