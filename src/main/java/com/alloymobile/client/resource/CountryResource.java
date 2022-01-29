package com.alloymobile.client.resource;

import com.alloymobile.client.config.SecurityConstants;
import com.alloymobile.client.model.Client;
import com.alloymobile.client.model.Country;
import com.alloymobile.client.service.CountryService;
import com.alloymobile.client.utils.PageData;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/countries")
@Tag(name = "Country", description = "The country API")
public class CountryResource{

    private final CountryService countryService;

    private final PageData page;

    public CountryResource(CountryService countryService, PageData page) {
        this.countryService = countryService;
        this.page = page;
    }

    @GetMapping( produces = "application/json")
    public Mono<Page<Country>> getAllCountry(
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "size", required = false) Integer size,
        @RequestParam(value = "sort", required = false) String sort){
        return this.countryService.findAllCountry(this.page.getPage(page, size, sort));
    }

//    @PreAuthorize("hasRole('ADMIN')")
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
