package com.ftn.sbnz.service.services;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
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
import com.ftn.sbnz.model.models.Booking;
import com.ftn.sbnz.model.models.Discount;
import com.ftn.sbnz.model.models.Listing;
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
import com.ftn.sbnz.service.dtos.ReturnedListingDTO;
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
		System.out.println("nemanjaaaa");
		Destination destination = new Destination(dto.getDestination().getName(), "21000");
		Location location = new Location(dto.getLocation().getLat(), dto.getLocation().getLng(),
								dto.getLocation().getAddress(), destination);
		// Owner owner = allOwners.findById(userService.getCurrentUser().getId()).get();
		Owner owner = allOwners.findById(2L).get();
		
		Listing listing = new Listing(dto.getTitle(), dto.getDescription(), dto.getPrice(), 0, owner);
		listing.setLocation(location);

		allListings.save(listing);
		allLocations.save(location);
		allDestinations.save(destination);
		allListings.flush();
		allLocations.flush();
		allDestinations.flush();

		AddedListingEvent addedListingEvent = new AddedListingEvent(listing);
		cepKieSession.insert(addedListingEvent);
		cepKieSession.insert(listing);
		cepKieSession.insert(allTravelers.findById(1L).get());

		Map<String, String> hierarchy = this.googleMapsService.reverseGeocode(dto.getLocation().getLat(), dto.getLocation().getLng());
		String state = hierarchy.get("state");
		String county = hierarchy.get("county");
		String city = hierarchy.get("city");
		String street = hierarchy.get("street");
		String streetNo = hierarchy.get("streetno");

		cepKieSession.insert( new LocationBackward(county, state));
		cepKieSession.insert( new LocationBackward(city, county));
		cepKieSession.insert( new LocationBackward(street, city));
		cepKieSession.insert( new LocationBackward(streetNo, street));

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
		
		if (allReviews.findByTravelerIdAndListingId(traveler.getId(), listing.getId()).orNull() != null) {
			throw new RuntimeException("Traveler already reviewed this listing.");
		}

		if (dto.getRating() < 1 || dto.getRating() > 5) {
			throw new RuntimeException("Wrong rating value. Rating must be between 1 and 5.");
		}

		LocalDateTime timestamp = LocalDateTime.now();
		Review review = new Review(dto.getRating(), dto.getComment(), timestamp, listing, traveler);

		updateListingRating(listing);

		allReviews.save(review);
		allReviews.flush();

		cepKieSession.insert(review);

		int n = cepKieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		//TODO: sta sa ovim
		// traveler = (Traveler) cepKieSession.getObject(cepKieSession.getFactHandle(traveler));
		allTravelers.save(traveler);
		allTravelers.flush();
	}

	private void updateListingRating(Listing listing) {
		System.out.println("Inside");
		List<Review> reviews = allReviews.findAllByListingId(listing.getId());

		Collection<?> newEvents = cepKieSession.getObjects(new ClassObjectFilter(Listing.class));
        for (Object event : newEvents) {
            if (event instanceof Listing) {
                Listing listingSess = (Listing) event;
                if (listingSess.getId() == listing.getId()) {
					listing = listingSess;
					break;
				}
            }
        }
		cepKieSession.delete(cepKieSession.getFactHandle(listing));

		System.out.println("Fetched");
		if (reviews.size() > 0) {
			double averageRating = reviews.stream()
										.mapToDouble(Review::getRating)
										.average()
										.orElse(0.0);  
			listing.setRating(averageRating);
		} else {
			listing.setRating(0);
		}

		System.out.println("Updated v1");

		cepKieSession.insert(listing);
		System.out.println("Updated");
		
		allListings.save(listing);
		allListings.flush();
	}

	@Override
	public void backward(){
		// cepKieSession.insert( new LocationBackward("Vojvodina", "Serbia") );
		// cepKieSession.insert( new LocationBackward("Južnobački okrug", "Vojvodina") );
		// cepKieSession.insert( new LocationBackward("Novi Sad", "Južnobački okrug") );
		// cepKieSession.insert( new LocationBackward("Zmaj Ognjena Vuka", "Novi Sad") );
		// cepKieSession.insert( new LocationBackward("Vojvodina", "Serbia") );
		// cepKieSession.insert( new LocationBackward("Južnobački okrug", "Vojvodina") );
		// cepKieSession.insert( new LocationBackward("Novi Sad", "Južnobački okrug") );
		// cepKieSession.insert( new LocationBackward("Jirecekova", "Novi Sad") );

		cepKieSession.setGlobal("backwardLocation", "Serbia");
		cepKieSession.insert( "go4" );
		cepKieSession.fireAllRules();
		System.out.println("---");
	}

	@Override
	public List<ReturnedListingDTO> getListingsForOwner() {
		List<ReturnedListingDTO> dtos = new ArrayList<>();
		System.out.println(userService.getCurrentUser());
		// List<Listing> listings = allListings.findAllByOwnerId(userService.getCurrentUser().getId());
		List<Listing> listings = allListings.findAllByOwnerId(2L);
		for (int i = 0; i < listings.size(); i ++){
			ListingDestinationDTO destinationDto = new ListingDestinationDTO(listings.get(i).getLocation().getDestination().getName());
			ListingLocationDTO locationDto = new ListingLocationDTO(listings.get(i).getLocation().getLat(), listings.get(i).getLocation().getLng(), listings.get(i).getLocation().getAddress());
			ReturnedListingDTO dto = new ReturnedListingDTO(listings.get(i).getId(), listings.get(i).getTitle(), listings.get(i).getPrice(), listings.get(i).getDescription(),
					 destinationDto, locationDto, "", listings.get(i).getRating());
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
	public List<ReturnedListingDTO> getAll() {
		List<ReturnedListingDTO> dtos = new ArrayList<>();
		List<Listing> listings = allListings.findAll();
		for (int i = 0; i < listings.size(); i ++){
			ListingDestinationDTO destinationDto = new ListingDestinationDTO(listings.get(i).getLocation().getDestination().getName());
			ListingLocationDTO locationDto = new ListingLocationDTO(listings.get(i).getLocation().getLat(), listings.get(i).getLocation().getLng(), listings.get(i).getLocation().getAddress());
			ReturnedListingDTO dto = new ReturnedListingDTO(listings.get(i).getId(), listings.get(i).getTitle(), listings.get(i).getPrice(), listings.get(i).getDescription(),
					 destinationDto, locationDto, "", listings.get(i).getRating());
			dtos.add(dto);
		}
		return dtos;
	}

	
}
