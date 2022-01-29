package com.alloymobile.client.service.role;

import com.alloymobile.client.persistence.model.Role;
import com.alloymobile.client.persistence.repository.RoleRepository;
import com.querydsl.core.types.Predicate;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Mono<Page<Role>> findAllRole(Predicate predicate, Pageable pageable){
        return roleRepository.count()
                .flatMap(roleCount -> {
                    return this.roleRepository.findAll(predicate,pageable.getSort())
                            .buffer(pageable.getPageSize(),(pageable.getPageNumber() + 1))
                            .elementAt(pageable.getPageNumber(), new ArrayList<>())
                            .map(roles -> new PageImpl<>(roles, pageable, roleCount));
                });
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
