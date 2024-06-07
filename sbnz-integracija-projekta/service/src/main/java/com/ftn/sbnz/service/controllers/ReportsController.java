package com.ftn.sbnz.service.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.service.services.interfaces.IReportsService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private IReportsService reportsService;
    
    @GetMapping(value = "/top-owner")
	public Map<String, Object> getTopDestinationsForOwner() {
        return this.reportsService.getTopDestinationsForOwner();
	}

    @GetMapping(value = "/top-traveler")
	public Map<String, Object> getTopDestinationsForTraveler() {
        return this.reportsService.getTopDestinationsForTraveler();
	}

    @GetMapping(value = "/spent-traveler")
	public Map<String, Double> getSpentForTraveler() {
        return this.reportsService.getSpentForTraveler();
	}

    @GetMapping(value = "/earned-owner")
	public Map<String, Double> getEarnedForOwner() {
        return this.reportsService.getEarnedForOwner();
	}

    @GetMapping(value = "/top-owners")
	public Map<String, Object> getTopOwners() {
        return this.reportsService.getTopOwners();
	}

    @GetMapping(value = "/top-travelers")
	public Map<String, Object> getTopTravelers() {
        return this.reportsService.getTopTravelers();
	}

}
