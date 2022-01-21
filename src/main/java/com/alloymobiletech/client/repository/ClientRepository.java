package com.alloymobiletech.client.repository;

import com.alloymobiletech.client.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

    public Mono<Client> findClientByEmail(String email);
}
