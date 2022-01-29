package com.alloymobile.client.persistence.repository;

import com.alloymobile.client.persistence.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String>, ReactiveQuerydslPredicateExecutor<Client> {

    public Mono<Client> findClientByEmail(String email);
}
