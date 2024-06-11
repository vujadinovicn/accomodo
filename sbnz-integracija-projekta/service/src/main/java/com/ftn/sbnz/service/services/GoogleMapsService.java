package com.ftn.sbnz.service.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.ftn.sbnz.service.services.interfaces.IGoogleMapsService;

@Service
public class GoogleMapsService implements IGoogleMapsService {
    
    @Override
    public Map<String, String> reverseGeocode(double lat, double lng) {
        String apiKey = "AIzaSyCkFF5-_eE4PVjPJfxJhejrZxYCb6T2J50";
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=" + apiKey;

        StringBuilder response = new StringBuilder();
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        Map<String, String> hierarchy = new HashMap<>();

        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Parse JSON string
            String jsonResponse = response.toString();
            int countryIndex = jsonResponse.indexOf("\"country\"");
            int stateIndex = jsonResponse.indexOf("\"administrative_area_level_1\"");
            int countyIndex = jsonResponse.indexOf("\"administrative_area_level_2\"");
            int cityIndex = jsonResponse.indexOf("\"locality\"");
            int streetIndex = jsonResponse.indexOf("\"route\"");
			int streets = jsonResponse.indexOf("\"street_number\"");

            if (countryIndex != -1) {
                String country = extractValue(jsonResponse, countryIndex);
                hierarchy.put("country", country);
            }
            if (stateIndex != -1) {
                String state = extractValue(jsonResponse, stateIndex);
                hierarchy.put("state", state);
            }
            if (countyIndex != -1) {
                String county = extractValue(jsonResponse, countyIndex);
                hierarchy.put("county", county);
            }
            if (cityIndex != -1) {
                String city = extractValue(jsonResponse, cityIndex);
                hierarchy.put("city", city);
            }
            if (streetIndex != -1) {
                String street = extractValue(jsonResponse, streetIndex);
                hierarchy.put("street", street);
            }
			if (streetIndex != -1) {
                String street = extractValue(jsonResponse, streets);
                hierarchy.put("streetno", street);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return hierarchy;
    }

    private String extractValue(String jsonResponse, int index) {
        int startIndex = jsonResponse.indexOf(":", index);
        int endIndex = jsonResponse.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = jsonResponse.indexOf("}", startIndex);
        }
        return jsonResponse.substring(startIndex + 3, endIndex - 1);
    }
}