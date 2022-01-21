package com.alloymobiletech.client.repository;

import com.alloymobiletech.client.model.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveMongoRepository<Role, String> {
}
