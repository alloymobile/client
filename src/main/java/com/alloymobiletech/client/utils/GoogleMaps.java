package com.alloymobiletech.client.utils;

import com.alloymobiletech.client.model.Address;
import com.alloymobiletech.client.model.Location;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class GoogleMaps {
    private final String apiKey;

    public GoogleMaps(Environment env) {
        this.apiKey = env.getProperty("google.api.key");
    }

    public Address getGeoCodeAddress(Address address) throws InterruptedException, ApiException, IOException{
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(this.apiKey)
                .build();
        GeocodingResult[] results =  GeocodingApi.geocode(context,
                getAddressString(address)).await();

        Location location = new Location();
        List<Double> coordinate = new ArrayList<>();
        coordinate.add(results[0].geometry.location.lat);
        coordinate.add(results[0].geometry.location.lng);
        location.setType("Point");
        location.setCoordinates(coordinate);
        address.setLocation(location);
        return address;
    }

    private String getAddressString(Address address){
        return address.getAddress()
                + " " + address.getCity()
                + " " + address.getState()
                + " " + address.getCountry()
                + " " + address.getPostalCode();
    }
    // Invoke .shutdown() after your application is done making requests
}
