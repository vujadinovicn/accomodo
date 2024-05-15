package com.ftn.sbnz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.sbnz.model.models.Destination;
import com.ftn.sbnz.service.repositories.DestinationRepository;

@Controller
public class SampleHomeController {

	@Autowired
	private DestinationRepository destinationRepository;

	public SampleHomeController(DestinationRepository destinationRepository) {
		this.destinationRepository = destinationRepository;
	}

	@RequestMapping("/")
	public String index() {
		Destination dest = new Destination("Kragujevac", "34000");
		destinationRepository.save(dest);
		destinationRepository.flush();
		
		return "index";
	}
}
