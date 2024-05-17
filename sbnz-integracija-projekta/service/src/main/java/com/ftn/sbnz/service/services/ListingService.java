package com.ftn.sbnz.service.services;


import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.enums.UserRole;
import com.ftn.sbnz.model.events.ListingViewedEvent;
import com.ftn.sbnz.model.models.Discount;
import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.repositories.DiscountRepository;
import com.ftn.sbnz.service.repositories.ListingRepository;
import com.ftn.sbnz.service.repositories.ListingViewedEventRepository;
import com.ftn.sbnz.service.repositories.TravelerRepository;
import com.ftn.sbnz.service.repositories.UserRepository;
import com.ftn.sbnz.service.services.interfaces.IListingService;

@Service
public class ListingService implements IListingService{
    
    @Autowired
    private ListingRepository allListings;
	@Autowired
    private UserRepository allUsers;
	@Autowired
    private KieSession kieSession;
	@Autowired
	private ListingViewedEventRepository allViewedListings;
	@Autowired
	private TravelerRepository allTravelers;
	@Autowired
	private DiscountRepository allDiscounts;

    @Override
	public Listing getById(GetListingDTO dto) {
		User user = allUsers.findById(dto.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!"));
		Listing listing = allListings.findById(dto.getListingId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing does not exist!"));

		if (user.getRole() == UserRole.TRAVELER) {
			addTravelerViewedListingEvent(user, listing);
		}

		return listing;
	}

	private void addTravelerViewedListingEvent(User user, Listing listing) {
		Traveler traveler = allTravelers.findById(user.getId()).get();

		ListingViewedEvent viewedEvent = new ListingViewedEvent(traveler, listing);
		
		allViewedListings.save(viewedEvent);
        allViewedListings.flush();
		
		kieSession.insert(viewedEvent);
		kieSession.insert(traveler);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		System.out.println(traveler.getFavoriteListings().size());

		if (n >= 1) {
			allTravelers.save(traveler);
			allTravelers.flush();
		}
	}

	@Override
	public void addDiscount(AddDiscountDTO dto) {
		Listing listing = allListings.findById(dto.getListingId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing does not exist!"));
		Discount discount = new Discount(dto.getAmount(), dto.getValidTo(), listing);
		Traveler traveler = allTravelers.findById(1L).get();

		allDiscounts.save(discount);
		allDiscounts.flush();

		kieSession.insert(discount);
		kieSession.insert(traveler);
		int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);
	}

	@Override
	public Listing findById(long id) {
		Listing listing = allListings.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing does not exist!"));
		return listing;
	}
}
