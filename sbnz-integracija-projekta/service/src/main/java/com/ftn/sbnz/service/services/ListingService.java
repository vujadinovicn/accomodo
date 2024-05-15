package com.ftn.sbnz.service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.service.repositories.ListingRepository;
import com.ftn.sbnz.service.services.interfaces.IListingService;

@Service
public class ListingService implements IListingService{
    
    @Autowired
    private ListingRepository allListings;

    @Override
	public Listing getById(long id) {
		Optional<Listing> found = allListings.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing does not exist!");

		}
		return found.get();
	}
}
