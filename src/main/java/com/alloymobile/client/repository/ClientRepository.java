package com.alloymobile.client.repository;

import com.alloymobile.client.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

    public Mono<Client> findClientByEmail(String email);
}
