package com.ftn.sbnz.service.services.interfaces;

import java.util.Map;

public interface IReportsService {

    public Map<String, Long> getTopDestinationsForOwner();
    public Map<String, Long> getTopDestinationsForTraveler();
    public Map<String, Double> getSpentForTraveler();
    public Map<String, Double> getEarnedForOwner();
    public Map<String, Long> getTopOwners() ;
    public Map<String, Long> getTopTravelers(); 
    
}
