package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.service.dtos.AddUserDTO;
import com.ftn.sbnz.service.services.interfaces.IRegistrationService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/register")
public class RegistrationController {
    @Autowired
    private final IRegistrationService registrationService;

	public RegistrationController(IRegistrationService registrationService) {
        this.registrationService = registrationService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addUser(@RequestBody AddUserDTO dto) {
        this.registrationService.addUser(dto);
	}
}
