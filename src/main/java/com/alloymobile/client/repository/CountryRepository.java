package com.alloymobile.client.repository;

import com.alloymobile.client.model.Country;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CountryRepository extends ReactiveMongoRepository<Country, String> {
}
