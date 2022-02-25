package com.alloymobile.client.persistence.repository;

import com.alloymobile.client.persistence.model.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CountryRepository extends ReactiveMongoRepository<Country, String>, ReactiveQuerydslPredicateExecutor<Country> {
}
