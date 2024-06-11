package com.ftn.sbnz.service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.models.Owner;
import com.ftn.sbnz.service.repositories.OwnerRepository;
import com.ftn.sbnz.service.services.interfaces.IOwnerService;

@Service
public class OwnerService implements IOwnerService{
    @Autowired
    private OwnerRepository allOwners;

    @Override
	public Owner getById(long id) {
		Optional<Owner> found = allOwners.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");

		}
		return found.get();
	}
}
