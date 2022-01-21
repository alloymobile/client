package com.alloymobiletech.client.service;

import com.alloymobiletech.client.model.Country;
import com.alloymobiletech.client.repository.CountryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CountryService {
    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    
    public Flux<Country> findAllCountry(){
        return countryRepository.findAll();
    }

    public Mono<Country> findCountryById(String id){
        return this.countryRepository.findById(id);
    }

    public Mono<Country> addCountry(Country country){
        country.setId(new ObjectId().toString());
        return countryRepository.save(country);
    }

    public Flux<Country> addCountries(List<Country> countries){
        countries.forEach(c->c.setId(new ObjectId().toString()));
        return countryRepository.saveAll(countries);
    }

    public Mono<Country> updateCountry(String id, Country country){
        return countryRepository.findById(id).flatMap(f->{
            country.setId(f.getId());
            return this.countryRepository.save(country);
        });
    }

    public Mono<Void> deleteCountry(String id){
        return this.countryRepository.deleteById(id);
    }

}
