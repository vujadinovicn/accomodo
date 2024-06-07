package com.ftn.sbnz.service.services.interfaces;

import java.util.Map;

public interface IGoogleMapsService {
    public Map<String, String> reverseGeocode(double lat, double lng) ;
}
