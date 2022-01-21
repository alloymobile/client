package com.alloymobiletech.client.repository;

import com.alloymobiletech.client.model.Country;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CountryRepository extends ReactiveMongoRepository<Country, String> {
}
