package com.ftn.sbnz.service.services;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.enums.UserRole;
import com.ftn.sbnz.model.models.Owner;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.service.dtos.AddUserDTO;
import com.ftn.sbnz.service.repositories.OwnerRepository;
import com.ftn.sbnz.service.repositories.TravelerRepository;
import com.ftn.sbnz.service.services.interfaces.IRegistrationService;
import com.ftn.sbnz.service.services.interfaces.IUserService;

@Service
public class RegistrationService implements IRegistrationService{

    @Autowired
    private TravelerRepository allTravelers;
    
    @Autowired
    private OwnerRepository allOwners;

    @Autowired
    private IUserService userService;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private KieSession kieSession;

    @Override
    public void addUser(AddUserDTO dto) {
		if (this.doesUserExist(dto.getEmail())) {
			// throw new Exception("There is already a user with the same email!");
            System.out.println("There is already a user with the same email!");
            return;
		}

        if (dto.getRole() == UserRole.TRAVELER){
            Traveler traveler = new Traveler(dto.getEmail(), dto.getPassword(), dto.getName(), dto.getLastname(), dto.getDateOfBirth());
            traveler.setPassword(passwordEncoder.encode(dto.getPassword()));
            kieSession.insert(traveler);
            allTravelers.save(traveler);
            allTravelers.flush();
        }
        else if (dto.getRole() == UserRole.OWNER){
            Owner owner = new Owner(dto.getEmail(), dto.getPassword(), dto.getName(), dto.getLastname(), dto.getDateOfBirth());
            owner.setPassword(passwordEncoder.encode(dto.getPassword()));
            kieSession.insert(owner);
            allOwners.save(owner);
            allOwners.flush();
        }
        else {
            System.out.println("wtf");
        }
    }

    @Override
	public boolean doesUserExist(String email) {
		try {
			this.userService.getUserByEmail(email);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
}
