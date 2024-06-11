package com.ftn.sbnz.service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.service.repositories.TravelerRepository;
import com.ftn.sbnz.service.services.interfaces.ITravelerService;

@Service
public class TravelerService implements ITravelerService {
    @Autowired
    private TravelerRepository allTravelers;

    @Override
	public Traveler getById(long id) {
		Optional<Traveler> found = allTravelers.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Traveler does not exist!");

		}
		return found.get();
	}
}
