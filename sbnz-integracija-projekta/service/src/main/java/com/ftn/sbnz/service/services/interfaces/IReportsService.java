package com.ftn.sbnz.service.services.interfaces;

import java.util.Map;

public interface IReportsService {

    public Map<String, Object> getTopDestinationsForOwner();
    public Map<String, Object> getTopDestinationsForTraveler();
    public Map<String, Double> getSpentForTraveler();
    public Map<String, Double> getEarnedForOwner();
    public Map<String, Object> getTopOwners() ;
    public Map<String, Object> getTopTravelers(); 
    
}
