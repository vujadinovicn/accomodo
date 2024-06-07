package com.ftn.sbnz.service.services;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.enums.UserRole;
import com.ftn.sbnz.model.events.AddedListingEvent;
import com.ftn.sbnz.model.events.DiscountEmailEvent;
import com.ftn.sbnz.model.events.ListingViewedEvent;
import com.ftn.sbnz.model.events.FetchListingRecomendationEvent;
import com.ftn.sbnz.model.models.AccommodationRecommendationResult;
import com.ftn.sbnz.model.models.Discount;
import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.Owner;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.dtos.ListingDTO;
import com.ftn.sbnz.service.mail.IMailService;
import com.ftn.sbnz.service.repositories.DiscountRepository;
import com.ftn.sbnz.service.repositories.ListingRepository;
import com.ftn.sbnz.service.repositories.ListingViewedEventRepository;
import com.ftn.sbnz.service.repositories.LocationRepository;
import com.ftn.sbnz.service.repositories.OwnerRepository;
import com.ftn.sbnz.service.repositories.ReviewRepository;
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
	@Autowired
	private ReviewRepository allReviews;
	@Autowired
    private OwnerRepository allOwners;
	@Autowired
    private LocationRepository allLocations;
	@Autowired
    private IMailService mailService;

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
		// for (Object object : kieSession.getObjects(new ClassObjectFilter(Traveler.class))) {
        //     Traveler t = (Traveler) object;
        //     if (t.getId() == traveler.getId()) {
        //    		kieSession.delete(kieSession.getFactHandle(t));
        //         break;
        //     }
        // }
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
	public void addListing(ListingDTO dto) {
		Location location = allLocations.findById(dto.getLocationId()).get();
		Owner owner = allOwners.findById(dto.getOwnerId()).get();
		
		Listing listing = new Listing(dto.getTitle(), dto.getDescription(), dto.getPrice(), 0, owner);
		listing.setLocation(location);

		allListings.save(listing);
		allLocations.save(location);
		allListings.flush();
		allLocations.flush();

		AddedListingEvent addedListingEvent = new AddedListingEvent(listing);
		kieSession.insert(addedListingEvent);
		kieSession.insert(allTravelers.findById(1L).get());

		int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(DiscountEmailEvent.class));
        for (Object event : newEvents) {
            if (event instanceof DiscountEmailEvent) {
                DiscountEmailEvent emailEvent = (DiscountEmailEvent) event;
                mailService.sendDiscountEmail(emailEvent);
            }
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

		Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(DiscountEmailEvent.class));
        for (Object event : newEvents) {
            if (event instanceof DiscountEmailEvent) {
                DiscountEmailEvent emailEvent = (DiscountEmailEvent) event;
                mailService.sendDiscountEmail(emailEvent);
            }
        }
	}

	@Override
	public Listing findById(long id) {
		Listing listing = allListings.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing does not exist!"));
		return listing;
	}

	@Override
	public void addReview(AddReviewDTO dto) {
		Listing listing = findById(dto.getListingId());
		Traveler traveler = allTravelers.findById(dto.getTravelerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!"));
		
		LocalDateTime timestamp = LocalDateTime.now();
		Review review = new Review(dto.getRating(), dto.getComment(), timestamp, listing, traveler);

		allReviews.save(review);
		allReviews.flush();

		kieSession.insert(review);
		kieSession.insert(traveler);

		int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		allTravelers.save(traveler);
		allTravelers.flush();
	}

	@Override
	public Review getReview(long listingId, long travelerId) {
		Review review = allReviews.findByTravelerIdAndListingId(travelerId, listingId).orNull();

		return review;
	}

	@Override
	public List<Listing> getListingRecommendations(Long id) {
		Traveler traveler = allTravelers.findById(id).orElseThrow();

		FetchListingRecomendationEvent event = new FetchListingRecomendationEvent(traveler, LocalDateTime.now());		
		
		kieSession.insert(event);
		// kieSession.insert(traveler);
        kieSession.setGlobal("listingService", this); 

        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

        Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(AccommodationRecommendationResult.class));
        for (Object obj : newEvents) {
            if (obj instanceof AccommodationRecommendationResult) {
                AccommodationRecommendationResult result = (AccommodationRecommendationResult) obj;
                for (Listing listing: result.getListings()) {
                    System.out.println(listing);
                }
                return result.getListings();
            }
        }

        return new ArrayList<>();
	}
}
