package com.alloymobile.client.repository;

import com.alloymobile.client.model.Country;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Flux;

public interface CountryRepository extends ReactiveMongoRepository<Country, String>, ReactiveQuerydslPredicateExecutor<Country> {
    Flux<Country> findAllByIdNotNull(Pageable pageable);
}
