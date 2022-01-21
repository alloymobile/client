package com.alloymobiletech.client.resource;

import com.alloymobiletech.client.model.Country;
import com.alloymobiletech.client.service.CountryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static com.alloymobiletech.client.config.SecurityConstants.BASE_URL;

@RestController
@RequestMapping(BASE_URL +"/countries")
@Tag(name = "Country", description = "The country API")
public class CountryResource{

    private final CountryService countryService;

    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping( produces = "application/json")
    public Flux<Country> getAllCountry(){
        return this.countryService.findAllCountry();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", produces = "application/json")
    public Mono<Country> getCountryById(@PathVariable(name="id") String id){
        return this.countryService.findCountryById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Mono<Country> addCountry(@RequestBody Country country){
        return this.countryService.addCountry(country);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping( value="/all", consumes = "application/json", produces = "application/json")
    public Flux<Country> addCountries(@RequestBody Country[] countries){
        return this.countryService.addCountries(Arrays.asList(countries));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Mono<Country> updateCountry(@PathVariable(name="id") String id, @RequestBody Country country){
        return this.countryService.updateCountry(id,country);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public Mono<Void> deleteCountry(@PathVariable(name="id") String id){
        return this.countryService.deleteCountry(id);
    }
}
