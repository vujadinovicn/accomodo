package com.ftn.sbnz.service.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.service.dtos.AdminUsersDTO;
import com.ftn.sbnz.service.dtos.TravelerDetailsDTO;
import com.ftn.sbnz.service.repositories.TravelerRepository;
import com.ftn.sbnz.service.repositories.UserRepository;
import com.ftn.sbnz.service.services.interfaces.IUserService;
import com.ftn.sbnz.model.events.BlockingEvent;
import com.ftn.sbnz.model.events.FetchListingRecomendationEvent;
import com.ftn.sbnz.model.models.AccommodationRecommendationResult;
import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.model.models.User;

@Service
public class UserService implements IUserService, UserDetailsService{

    @Autowired
    private UserRepository allUsers;

    @Autowired
    private TravelerRepository allTravelers;

    // @Autowired
    // private IListingService listingService;
    
	@Autowired
    private KieSession kieSession;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> ret = allUsers.findByEmail(email);
		if (!ret.isEmpty()) {
			System.out.println(ret.get().getEmail());
			return org.springframework.security.core.userdetails.User.withUsername(email).password(ret.get().getPassword()).roles(ret.get().getRole().toString()).build();
		}
		throw new UsernameNotFoundException("User not found with this email: " + email);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = allUsers.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!"));
        return user;
    }

    @Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
		return allUsers.findByEmail(auth.getName()).orElse(null);
    }

    public List<Listing> addTravelerLoggedInEvent(User user) {
        Traveler traveler = allTravelers.findById(user.getId()).get();

		FetchListingRecomendationEvent event = new FetchListingRecomendationEvent(traveler, LocalDateTime.now());		
		
		kieSession.insert(event);
		// // kieSession.insert(traveler);
        // kieSession.setGlobal("listingService", listingService); 

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

    @Override
    public List<AdminUsersDTO> getForAdmin() {
        List<Traveler> users = this.allTravelers.findAll();
        List<AdminUsersDTO> dtos = new ArrayList<>();
        for (Traveler traveler : users) {
            System.out.println(traveler);
            AdminUsersDTO dto = new AdminUsersDTO(traveler.getName() + " " + traveler.getLastname(), 
                            traveler.getEmail(), traveler.isBlocked(), traveler.isIressponsible(), traveler.isMalicious());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void block(String email) {
        Traveler traveler = allTravelers.findByEmail(email);
        BlockingEvent event = new BlockingEvent(email, true);
        kieSession.insert(event);

        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

        for (Object object : kieSession.getObjects(new ClassObjectFilter(Traveler.class))) {
            Traveler t = (Traveler) object;
            if (t.getId() == traveler.getId()) {
				allTravelers.save(t);
				allTravelers.flush();
                break;
            }
        }
    }

    @Override
    public void unblock(String email) {
        Traveler traveler = allTravelers.findByEmail(email);
        BlockingEvent event = new BlockingEvent(email, false);
        kieSession.insert(event);

        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);

        for (Object object : kieSession.getObjects(new ClassObjectFilter(Traveler.class))) {
            Traveler t = (Traveler) object;
            if (t.getId() == traveler.getId()) {
				allTravelers.save(t);
				allTravelers.flush();
                break;
            }
        }
    }    

    public TravelerDetailsDTO getDetails(){
        Traveler traveler = allTravelers.findById(getCurrentUser().getId()).get();
        TravelerDetailsDTO dto = new TravelerDetailsDTO(traveler.getEmail(), traveler.getName(), traveler.getLastname(), traveler.getLevel());
        return dto;
    }
}
