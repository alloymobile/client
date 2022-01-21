package com.alloymobiletech.client.service;

import com.alloymobiletech.client.model.Role;
import com.alloymobiletech.client.repository.RoleRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Flux<Role> findAllRole(){
        return roleRepository.findAll();
    }

    public Mono<Role> findRoleById(String id){
        return this.roleRepository.findById(id);
    }

    public Mono<Role> addRole(Role role){
        role.setId(new ObjectId().toString());
        return roleRepository.save(role);
    }

    public Mono<Role> updateRole(String id, Role role){
        return roleRepository.findById(id).flatMap(f->{
            role.setId(f.getId());
            return this.roleRepository.save(role);
        });
    }

    public Mono<Void> deleteRole(String id){
        return this.roleRepository.deleteById(id);
    }
}
