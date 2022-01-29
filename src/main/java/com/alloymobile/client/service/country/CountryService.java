package com.alloymobile.client.service.country;

import com.alloymobile.client.persistence.model.Country;
import com.alloymobile.client.persistence.repository.CountryRepository;
import com.querydsl.core.types.Predicate;
import org.bson.types.ObjectId;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    
    public Mono<Page<Country>> findAllCountry(Predicate predicate,Pageable pageable){
        return this.countryRepository.count()
                .flatMap(countryCount -> {
                        return this.countryRepository.findAll(predicate,pageable.getSort())
                                .buffer(pageable.getPageSize(),(pageable.getPageNumber() + 1))
                                .elementAt(pageable.getPageNumber(), new ArrayList<>())
                                .map(users -> new PageImpl<>(users, pageable, countryCount));
                });
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
