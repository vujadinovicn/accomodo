package com.ftn.sbnz.service.services;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.enums.UserRole;
import com.ftn.sbnz.model.events.AddedListingEvent;
import com.ftn.sbnz.model.events.DiscountEmailEvent;
import com.ftn.sbnz.model.events.ListingViewedEvent;
import com.ftn.sbnz.model.models.Destination;
import com.ftn.sbnz.model.events.FetchListingRecomendationEvent;
import com.ftn.sbnz.model.models.AccommodationRecommendationResult;
import com.ftn.sbnz.model.models.Discount;
import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.ListingAccumulator;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.LocationBackward;
import com.ftn.sbnz.model.models.Owner;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddListingDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.dtos.ListingDestinationDTO;
import com.ftn.sbnz.service.dtos.ListingLocationDTO;
import com.ftn.sbnz.service.mail.IMailService;
import com.ftn.sbnz.service.repositories.DestinationRepository;
import com.ftn.sbnz.service.repositories.DiscountRepository;
import com.ftn.sbnz.service.repositories.ListingRepository;
import com.ftn.sbnz.service.repositories.ListingViewedEventRepository;
import com.ftn.sbnz.service.repositories.LocationRepository;
import com.ftn.sbnz.service.repositories.OwnerRepository;
import com.ftn.sbnz.service.repositories.ReviewRepository;
import com.ftn.sbnz.service.repositories.TravelerRepository;
import com.ftn.sbnz.service.repositories.UserRepository;
import com.ftn.sbnz.service.services.interfaces.IGoogleMapsService;
import com.ftn.sbnz.service.services.interfaces.IListingService;
import com.ftn.sbnz.service.services.interfaces.IUserService;

@Service
public class ListingService implements IListingService{
    
    @Autowired
    private ListingRepository allListings;
	@Autowired
    private UserRepository allUsers;
	@Autowired
    private KieSession cepKieSession;
	// @Autowired
    // @Qualifier("backwardSession") // Specify the bean name here
    // private KieSession bwKieSession;
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
    private DestinationRepository allDestinations;
	@Autowired
    private IMailService mailService;
	@Autowired
	private IGoogleMapsService googleMapsService;
	@Autowired
	private IUserService userService;

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
		
		cepKieSession.insert(viewedEvent);
		// for (Object object : cepKieSession.getObjects(new ClassObjectFilter(Traveler.class))) {
        //     Traveler t = (Traveler) object;
        //     if (t.getId() == traveler.getId()) {
        //    		cepKieSession.delete(cepKieSession.getFactHandle(t));
        //         break;
        //     }
        // }
		cepKieSession.insert(traveler);
        int n = cepKieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		System.out.println(traveler.getFavoriteListings().size());

		if (n >= 1) {
			allTravelers.save(traveler);
			allTravelers.flush();
		}
	}

	@Override
	public void addListing(AddListingDTO dto) {
		Map<String, String> hierarchy = this.googleMapsService.reverseGeocode(dto.getLocation().getLat(), dto.getLocation().getLng());
		// String state = hierarchy.get("state");
		String county = hierarchy.get("county");
		String city = hierarchy.get("city");
		String street = hierarchy.get("street");
		String streetNo = hierarchy.get("streetno");

		// cepKieSession.insert( new LocationBackward(county, state));
		cepKieSession.insert( new LocationBackward(city, county));
		cepKieSession.insert( new LocationBackward(street, city));
		cepKieSession.insert( new LocationBackward(streetNo, street));

		Destination destination = new Destination(dto.getDestination().getName(), "21000");
		List<Destination> destinations = allDestinations.findAll();
		for (Destination d : destinations) {
			if (d.getName() == dto.getDestination().getName()){
				destination = d;
				break;
			}
		}
		Location location = new Location(dto.getLocation().getLat(), dto.getLocation().getLng(),
								streetNo, destination);
		// Owner owner = allOwners.findById(userService.getCurrentUser().getId()).get();
		Owner owner = allOwners.findById(userService.getCurrentUser().getId()).get();
		
		Listing listing = new Listing(dto.getTitle(), dto.getDescription(), dto.getPrice(), 0, owner);
		listing.setLocation(location);

		
		allDestinations.save(destination);
		allLocations.save(location);
		allListings.save(listing);

		allDestinations.flush();
		allLocations.flush();
		allListings.flush();

		AddedListingEvent addedListingEvent = new AddedListingEvent(listing);
		cepKieSession.insert(addedListingEvent);
		// cepKieSession.insert(listing);
		// cepKieSession.insert(allTravelers.findById(1L).get());

		int n = cepKieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		Collection<?> newEvents = cepKieSession.getObjects(new ClassObjectFilter(DiscountEmailEvent.class));
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

		cepKieSession.insert(discount);
		cepKieSession.insert(traveler);
		int n = cepKieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		Collection<?> newEvents = cepKieSession.getObjects(new ClassObjectFilter(DiscountEmailEvent.class));
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

		cepKieSession.insert(review);
		cepKieSession.insert(traveler);

		int n = cepKieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		allTravelers.save(traveler);
		allTravelers.flush();
	}

	@Override
	public List<AddListingDTO> backward(String location){
		System.out.println(location);
		cepKieSession.setGlobal("backwardLocation", location);
		ListingAccumulator accumulator = new ListingAccumulator();
        cepKieSession.insert(accumulator);
		
        // cepKieSession.insert("go4");
        cepKieSession.fireUntilHalt();
		// System.out.println(n);

        Set<Long> matchedListings = accumulator.getListings();
		List<AddListingDTO> dtos = new ArrayList<>();
		Iterator<Long> iterator = matchedListings.iterator();
		while (iterator.hasNext()) {
			Listing listing = allListings.findById(iterator.next()).get();
			ListingDestinationDTO destinationDto = new ListingDestinationDTO(listing.getLocation().getDestination().getName());
			ListingLocationDTO locationDto = new ListingLocationDTO(listing.getLocation().getLat(), listing.getLocation().getLng(), listing.getLocation().getAddress());
			AddListingDTO dto = new AddListingDTO(listing.getId(), listing.getTitle(), listing.getPrice(), listing.getDescription(),
					 destinationDto, locationDto, "");
			dtos.add(dto);
		}
		cepKieSession.delete(cepKieSession.getFactHandle(accumulator));
		Collection<?> newEvents = cepKieSession.getObjects(new ClassObjectFilter(LocationBackward.class));
        for (Object event : newEvents) {
            if (event instanceof LocationBackward) {
                LocationBackward lb = (LocationBackward) event;
				// cepKieSession.delete(cepKieSession.getFactHandle(lb));
				cepKieSession.insert(lb);
            }
        }
		return dtos;
	}

	@Override
	public List<AddListingDTO> getListingsForOwner() {
		List<AddListingDTO> dtos = new ArrayList<>();
		// System.out.println(userService.getCurrentUser());
		// List<Listing> listings = allListings.findAllByOwnerId(userService.getCurrentUser().getId());
		List<Listing> listings = allListings.findAllByOwnerId(userService.getCurrentUser().getId());
		for (int i = 0; i < listings.size(); i ++){
			
			ListingDestinationDTO destinationDto = new ListingDestinationDTO(listings.get(i).getLocation().getDestination().getName());
			ListingLocationDTO locationDto = new ListingLocationDTO(listings.get(i).getLocation().getLat(), listings.get(i).getLocation().getLng(), listings.get(i).getLocation().getAddress());
			AddListingDTO dto = new AddListingDTO(listings.get(i).getId(), listings.get(i).getTitle(), listings.get(i).getPrice(), listings.get(i).getDescription(),
					 destinationDto, locationDto, "");
			dtos.add(dto);
		}
		return dtos;
	}

	
	public Review getReview(long listingId, long travelerId) {
		Review review = allReviews.findByTravelerIdAndListingId(travelerId, listingId).orNull();

		return review;
	}

	@Override
	public List<Listing> getListingRecommendations(Long id) {
		Traveler traveler = allTravelers.findById(id).orElseThrow();

		FetchListingRecomendationEvent event = new FetchListingRecomendationEvent(traveler, LocalDateTime.now());		
		
		cepKieSession.insert(event);
		// kieSession.insert(traveler);
        cepKieSession.setGlobal("listingService", this); 

        int n = cepKieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

        Collection<?> newEvents = cepKieSession.getObjects(new ClassObjectFilter(AccommodationRecommendationResult.class));
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

	@Override
	public void delete(Long id) {
		allListings.deleteById(id);
		for (Object object : cepKieSession.getObjects(new ClassObjectFilter(Listing.class))) {
            Listing listing = (Listing) object;
            if (id == listing.getId()) {
                cepKieSession.delete(cepKieSession.getFactHandle(listing));
            }
        }
	}

	@Override
	public List<AddListingDTO> filterByRating(int rating) {
		QueryResults results = cepKieSession.getQueryResults("Listings with specific rating", rating);

        List<AddListingDTO> dtos = new ArrayList<>();
        for (QueryResultsRow row : results) {
            Listing listing = (Listing) row.get("$listing");
            ListingDestinationDTO destinationDto = new ListingDestinationDTO(listing.getLocation().getDestination().getName());
			ListingLocationDTO locationDto = new ListingLocationDTO(listing.getLocation().getLat(), listing.getLocation().getLng(), listing.getLocation().getAddress());
			AddListingDTO dto = new AddListingDTO(listing.getId(), listing.getTitle(), listing.getPrice(), listing.getDescription(),
					 destinationDto, locationDto, "");
			dtos.add(dto);
        }
        return dtos;
	}

	@Override
	public List<AddListingDTO> filterByPrice(double min, double max) {
		QueryResults results = cepKieSession.getQueryResults("Listings with min max price", min, max);

        List<AddListingDTO> dtos = new ArrayList<>();
        for (QueryResultsRow row : results) {
            Listing listing = (Listing) row.get("$listing");
            ListingDestinationDTO destinationDto = new ListingDestinationDTO(listing.getLocation().getDestination().getName());
			ListingLocationDTO locationDto = new ListingLocationDTO(listing.getLocation().getLat(), listing.getLocation().getLng(), listing.getLocation().getAddress());
			AddListingDTO dto = new AddListingDTO(listing.getId(), listing.getTitle(), listing.getPrice(), listing.getDescription(),
					 destinationDto, locationDto, "");
			dtos.add(dto);
        }
        return dtos;
	}
}
