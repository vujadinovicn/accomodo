package com.ftn.sbnz.service.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.Destination;
import com.ftn.sbnz.model.models.Owner;
import com.ftn.sbnz.model.models.Traveler;
import com.ftn.sbnz.service.services.interfaces.IReportsService;

@Service
public class ReportsService implements IReportsService{

    @Autowired
	// @Qualifier("cepSession")
    private KieSession kSession;

    @Autowired
    private UserService userService;

    @Override
    public Map<String, Long> getTopDestinationsForOwner() {
        kSession.fireAllRules();
        
        QueryResults results = kSession.getQueryResults("Top Destinations For Owner", userService.getCurrentUser().getId());
        Map<String, Long> destinationMap = new HashMap<>();
        for (QueryResultsRow row : results) {
            Destination destination = (Destination) row.get("$destination");
            Long count = (Long) row.get("$count");
            destinationMap.put(destination.getName(), count);
        }

        Map<String, Long> result = destinationMap.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(
            Map.Entry::getKey, 
            Map.Entry::getValue, 
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        
        return result;
    }

    @Override
    public Map<String, Long> getTopDestinationsForTraveler() {
        kSession.fireAllRules();
        
        QueryResults results = kSession.getQueryResults("Top Destinations For Traveler", userService.getCurrentUser().getId());
        Map<String, Long> destinationMap = new HashMap<>();
        for (QueryResultsRow row : results) {
            Destination destination = (Destination) row.get("$destination");
            Long count = (Long) row.get("$count");

            destinationMap.put(destination.getName(), count);
        }

        Map<String, Long> result = destinationMap.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(
            Map.Entry::getKey, 
            Map.Entry::getValue, 
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        
        return result;
    }

    @Override
    public Map<String, Double> getSpentForTraveler(){
        kSession.fireAllRules();
        Map<String, Double> money = new HashMap<>();
        System.out.println(userService.getCurrentUser().getId());
        QueryResults results = kSession.getQueryResults("Spent For Traveler", userService.getCurrentUser().getId());
        for (QueryResultsRow row : results) {
            Double spentAllTime = (Double) row.get("$spentAllTime");
            System.out.println(spentAllTime);
            Double spent7Days = (Double) row.get("$spent7days");
            Double spentMonth = (Double) row.get("$spentMonth");
            Double spentSixMonths = (Double) row.get("$spentSixMonths");
            Double spentYear = (Double) row.get("$spentYear");
            money.put("allTime", spentAllTime);
            money.put("sevenDays", spent7Days);
            money.put("month", spentMonth);
            money.put("sixMonths", spentSixMonths);
            money.put("year", spentYear);
        }

        return money;
    }

    @Override
    public Map<String, Double> getEarnedForOwner(){
        kSession.fireAllRules();
        Map<String, Double> money = new HashMap<>();
        QueryResults results = kSession.getQueryResults("Earned By Owner", userService.getCurrentUser().getId());
        for (QueryResultsRow row : results) {
            Double spentAllTime = (Double) row.get("$spentAllTime");
            Double spent7Days = (Double) row.get("$spent7days");
            Double spentMonth = (Double) row.get("$spentMonth");
            Double spentSixMonths = (Double) row.get("$spentSixMonths");
            Double spentYear = (Double) row.get("$spentYear");
            money.put("allTime", spentAllTime);
            money.put("sevenDays", spent7Days);
            money.put("month", spentMonth);
            money.put("sixMonths", spentSixMonths);
            money.put("year", spentYear);

        }
        return money;
    }

    @Override
    public Map<String, Long> getTopOwners() {
        kSession.fireAllRules();
        
        QueryResults results = kSession.getQueryResults("Top Owners");
        Map<String, Long> map = new HashMap<>();
        for (QueryResultsRow row : results) {
            Owner owner = (Owner) row.get("$owner");
            Long count = (Long) row.get("$count");
            map.put(owner.getName() + " " + owner.getLastname(), count);
        }
        
        Map<String, Long> result = map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(
            Map.Entry::getKey, 
            Map.Entry::getValue, 
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return result;
    }

    @Override
    public Map<String, Long> getTopTravelers() {
        kSession.fireAllRules();
        
        QueryResults results = kSession.getQueryResults("Top Travelers");
        Map<String, Long> map = new HashMap<>();
        for (QueryResultsRow row : results) {
            Traveler owner = (Traveler) row.get("$traveler");
            Long count = (Long) row.get("$count");
            map.put(owner.getName() + " " + owner.getLastname(), count);
        }
        
        Map<String, Long> result = map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(
            Map.Entry::getKey, 
            Map.Entry::getValue, 
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return result;
    }
    
    
}
