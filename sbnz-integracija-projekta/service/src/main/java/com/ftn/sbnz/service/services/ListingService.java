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
import com.ftn.sbnz.model.events.NewDiscountEvent;
import com.ftn.sbnz.model.models.Destination;
import com.ftn.sbnz.model.events.FetchListingRecomendationEvent;
import com.ftn.sbnz.model.models.AccommodationRecommendationResult;
import com.ftn.sbnz.model.models.Booking;
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
import com.ftn.sbnz.service.dtos.ReturnedDiscountDTO;
import com.ftn.sbnz.service.dtos.ReturnedListingDTO;
import com.ftn.sbnz.service.dtos.ReturnedReviewDTO;
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
			addTravelerViewedListingEvent(user.getId(), listing);
		}

		return listing;
	}

	private void addTravelerViewedListingEvent(Long travelerId, Listing listing) {
		Traveler traveler = allTravelers.findById(travelerId).get();

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
        int n = cepKieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		for (Object object : cepKieSession.getObjects(new ClassObjectFilter(Traveler.class))) {
            Traveler t = (Traveler) object;
            if (t.getId() == traveler.getId()) {
				allTravelers.save(t);
				allTravelers.flush();
                break;
            }
        }

		Collection<?> newEvents = cepKieSession.getObjects(new ClassObjectFilter(DiscountEmailEvent.class));
        for (Object event : newEvents) {
            if (event instanceof DiscountEmailEvent) {
                DiscountEmailEvent emailEvent = (DiscountEmailEvent) event;
                mailService.sendDiscountEmail(emailEvent);
            }
        }
		// System.out.println(traveler.getFavoriteListings().size());

		// if (n >= 1) {
		// 	allTravelers.save(traveler);
		// 	allTravelers.flush();
		// }
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
	public ReturnedDiscountDTO addDiscount(AddDiscountDTO dto) {
		Listing listing = allListings.findById(dto.getListingId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing does not exist!"));
		
		Discount prev = allDiscounts.findByListingId(listing.getId()).orNull();
		if (prev != null) {
			throw new RuntimeException("There's already an active discount for the listing. Please delete it before adding a new one.");
		}

		if (dto.getAmount() > listing.getPrice())
			throw new RuntimeException("Discount amount can't be higher than the listing price.");

		Discount discount = new Discount(dto.getAmount(), dto.getValidTo(), listing);
		allDiscounts.save(discount);
		allDiscounts.flush();

		NewDiscountEvent discEvent = new NewDiscountEvent(discount.getId());


		cepKieSession.insert(discount);
		cepKieSession.insert(discEvent);
		int n = cepKieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

		Collection<?> newEvents = cepKieSession.getObjects(new ClassObjectFilter(DiscountEmailEvent.class));
        for (Object event : newEvents) {
            if (event instanceof DiscountEmailEvent) {
                DiscountEmailEvent emailEvent = (DiscountEmailEvent) event;
                mailService.sendDiscountEmail(emailEvent);
            }
        }

		cepKieSession.delete(cepKieSession.getFactHandle(discEvent));

		return new ReturnedDiscountDTO(discount.getId(), discount.getAmount(), discount.getValidTo());
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

		allReviews.save(review);
		allReviews.flush();

		updateListingRating(listing);

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
	public List<ReturnedListingDTO> backward(String location){
		System.out.println(location);
		cepKieSession.setGlobal("backwardLocation", location);
		ListingAccumulator accumulator = new ListingAccumulator();
        cepKieSession.insert(accumulator);
		
        // cepKieSession.insert("go4");
        int n = cepKieSession.fireAllRules();
		System.out.println(n);

        Set<Long> matchedListings = accumulator.getListings();
		List<ReturnedListingDTO> dtos = new ArrayList<>();
		List<Listing> listings = new ArrayList<>();
		Iterator<Long> iterator = matchedListings.iterator();
		while (iterator.hasNext()) {
			Listing listing = allListings.findById(iterator.next()).get();
			listings.add(listing);
		}

		dtos = parseListingToDto(listings);
		cepKieSession.delete(cepKieSession.getFactHandle(accumulator));
		// Collection<?> newEvents = cepKieSession.getObjects(new ClassObjectFilter(LocationBackward.class));
        // for (Object event : newEvents) {
        //     if (event instanceof LocationBackward) {
        //         LocationBackward lb = (LocationBackward) event;
		// 		// cepKieSession.delete(cepKieSession.getFactHandle(lb));
		// 		cepKieSession.insert(lb);
        //     }
        // }
		return dtos;
	}

	@Override
	public List<ReturnedListingDTO> getListingsForOwner() {
		System.out.println(userService.getCurrentUser());
		// List<Listing> listings = allListings.findAllByOwnerId(userService.getCurrentUser().getId());
		List<Listing> listings = allListings.findAllByOwnerId(userService.getCurrentUser().getId());
		return parseListingToDto(listings);
	}

	
	public Review getReview(long listingId, long travelerId) {
		Review review = allReviews.findByTravelerIdAndListingId(travelerId, listingId).orNull();

		return review;
	}

	@Override
	public List<ReturnedListingDTO> getListingRecommendations(Long id) {
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
                return parseListingToDto(result.getListings());
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
	public List<ReturnedListingDTO> filterByRating(int rating) {
		QueryResults results = cepKieSession.getQueryResults("Listings with specific rating", rating);

        List<ReturnedListingDTO> dtos = new ArrayList<>();
		List<Listing> listings = new ArrayList<>();
        for (QueryResultsRow row : results) {
            Listing listing = (Listing) row.get("$listing");
            listings.add(listing);
        }
		dtos = parseListingToDto(listings);
        return dtos;
	}

	@Override
	public List<ReturnedListingDTO> filterByPrice(double min, double max) {
		QueryResults results = cepKieSession.getQueryResults("Listings with min max price", min, max);

        List<ReturnedListingDTO> dtos = new ArrayList<>();
		List<Listing> listings = new ArrayList<>();
        for (QueryResultsRow row : results) {
            Listing listing = (Listing) row.get("$listing");
            listings.add(listing);
        }
		dtos = parseListingToDto(listings);
        return dtos;
	}
	public List<ReturnedListingDTO> getAll() {
		List<Listing> listings = allListings.findAll();
		return parseListingToDto(listings);
	}

	@Override
	public List<ReturnedReviewDTO> getReviews(Long listingId) {
		Listing listing = allListings.findById(listingId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing does not exist!"));
		List<Review> reviews = allReviews.findAllByListingId(listingId);

		List<ReturnedReviewDTO> dtos = new ArrayList<>();
		for (Review review: reviews) {
			ReturnedReviewDTO dto = new ReturnedReviewDTO(review.getId(), review.getRating(), review.getComment(), review.getDate(), listingId, review.getTraveler().getId(), review.getTraveler().getName() + " " + review.getTraveler().getLastname());
			dtos.add(dto);
		}

		if (userService.getCurrentUser().getRole() == UserRole.TRAVELER) {
			addTravelerViewedListingEvent(userService.getCurrentUser().getId(), listing);
		}

		return dtos;
	}

	private List<ReturnedListingDTO> parseListingToDto(List<Listing> listings) {
		List<ReturnedListingDTO> dtos = new ArrayList<>();
		for (int i = 0; i < listings.size(); i ++){
			ListingDestinationDTO destinationDto = new ListingDestinationDTO(listings.get(i).getLocation().getDestination().getName());
			ListingLocationDTO locationDto = new ListingLocationDTO(listings.get(i).getLocation().getLat(), listings.get(i).getLocation().getLng(), listings.get(i).getLocation().getAddress());
			ReturnedListingDTO dto = new ReturnedListingDTO(listings.get(i).getId(), listings.get(i).getTitle(), listings.get(i).getPrice(), listings.get(i).getDescription(),
					 destinationDto, locationDto, "", listings.get(i).getRating());

			Discount discount = allDiscounts.findByListingId(listings.get(i).getId()).orNull();
			if (discount != null)
				dto.setDiscount(new ReturnedDiscountDTO(discount.getId(), discount.getAmount(), discount.getValidTo()));
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void deleteDiscount(Long id) {
		Discount discount = this.allDiscounts.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount does not exist!"));

		Collection<?> allDiscountsFromSession = cepKieSession.getObjects(new ClassObjectFilter(Discount.class));

        for (Object obj : allDiscountsFromSession) {
            Discount discountFromSession = (Discount) obj;
            if (discountFromSession.getId().equals(id)) {
                FactHandle factHandle = cepKieSession.getFactHandle(discountFromSession);
                if (factHandle != null) {
                    cepKieSession.delete(factHandle);
                }
            }
        }

		this.allDiscounts.delete(discount);
		this.allDiscounts.flush();
	}

	
}
