package com.ftn.sbnz.service.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.Hibernate;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.sbnz.model.models.Booking;
import com.ftn.sbnz.model.models.Destination;
import com.ftn.sbnz.model.models.Discount;
import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.LocationBackward;
import com.ftn.sbnz.model.models.Owner;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.model.models.Tag;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.service.repositories.BookingRepository;
import com.ftn.sbnz.service.repositories.DestinationRepository;
import com.ftn.sbnz.service.repositories.DiscountRepository;
import com.ftn.sbnz.service.repositories.ListingRepository;
import com.ftn.sbnz.service.repositories.LocationRepository;
import com.ftn.sbnz.service.repositories.OwnerRepository;
import com.ftn.sbnz.service.repositories.ReviewRepository;
import com.ftn.sbnz.service.repositories.TagRepository;
import com.ftn.sbnz.service.repositories.TravelerRepository;
import com.ftn.sbnz.service.services.interfaces.IGoogleMapsService;

@Component
public class DataLoaderService {
    @Autowired
    private KieSession kieSession;

    @Autowired
    private ListingRepository allListings;

    @Autowired
    private TravelerRepository allTravelers;

    @Autowired
    private TagRepository allTags;

    @Autowired
    private OwnerRepository allOwners;

    @Autowired
    private LocationRepository allLocations;

    @Autowired
    private ReviewRepository allReviews;

    @Autowired
    private DestinationRepository allDestinations;

    @Autowired
    private BookingRepository allBookings;

    @Autowired
    private DiscountRepository allDiscounts;

    @Autowired
	private IGoogleMapsService googleMapsService;

    @PostConstruct
    public void init() {
        loadDataIntoKieSession();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    @PreDestroy
    public void cleanup() {
        // kieSession.dispose();
    }


    public void loadDataIntoKieSession() {
        List<Listing> listings = allListings.findAll();
        List<Traveler> travelers = allTravelers.findAll();
        List<Tag> tags = allTags.findAll();
        List<Destination> destinations = allDestinations.findAll();
        List<Discount> discounts = allDiscounts.findAll();
        List<Booking> bookings = allBookings.findAll();
        List<Owner> owners = allOwners.findAll();
        List<Review> reviews = allReviews.findAll();
        List<Location> locations = allLocations.findAll();

        for (Listing listing : listings) {
            Map<String, String> hierarchy = this.googleMapsService.reverseGeocode(listing.getLocation().getLat(), listing.getLocation().getLng());
            String state = hierarchy.get("state");
            String county = hierarchy.get("county");
            String city = hierarchy.get("city");
            String street = hierarchy.get("street");
            String streetNo = hierarchy.get("streetno");
            // System.out.println(state);
            System.out.println("county");
            System.out.println(county);
            System.out.println("city");
            System.out.println(city);
            System.out.println("street");
            System.out.println(street);
            System.out.println("no");
            System.out.println(streetNo);

            // kieSession.insert( new LocationBackward(county, state));
            kieSession.insert( new LocationBackward(city, county));
            kieSession.insert( new LocationBackward(street, city));
            kieSession.insert( new LocationBackward(streetNo, street));
            kieSession.insert(listing);
        }

        for (Traveler traveler : travelers) {
            // Hibernate.initialize(traveler.getFavoriteListings());
            kieSession.insert(traveler);
        }

        for (Tag tag : tags) {
            kieSession.insert(tag);
        }

        for (Destination destination : destinations) {
            kieSession.insert(destination);
        }

        for (Discount discount : discounts) {
            kieSession.insert(discount);
        }

        for (Booking booking : bookings) {
            kieSession.insert(booking);
            System.out.println("BOOKINGS: " + booking.getTraveler().getId());

        }

        System.out.println("BOOKINGS: " + bookings.size());

        for (Owner owner : owners) {
            kieSession.insert(owner);
        }

        for (Review review : reviews) {
            kieSession.insert(review);
        }

        for (Location location : locations) {
            kieSession.insert(location);
        }

        kieSession.setGlobal("dateNow", new Date());


        // kieSession.fireAllRules();
    }
}
