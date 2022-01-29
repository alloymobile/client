package com.alloymobile.client.persistence.repository;

import com.alloymobile.client.persistence.model.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveMongoRepository<Role, String>, ReactiveQuerydslPredicateExecutor<Role> {
}
