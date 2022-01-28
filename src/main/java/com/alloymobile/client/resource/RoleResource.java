package com.alloymobile.client.resource;

import com.alloymobile.client.config.SecurityConstants;
import com.alloymobile.client.model.Role;
import com.alloymobile.client.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(SecurityConstants.BASE_URL +"/roles")
@Tag(name = "Role", description = "The role API")
public class RoleResource implements IResource{

    private final RoleService roleService;

    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping( produces = "application/json")
    public Flux<Role> getAllRole(){
        return this.roleService.findAllRole();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", produces = "application/json")
    public Mono<Role> getRoleById(@PathVariable(name="id") String id){
        return this.roleService.findRoleById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Mono<Role> addRole(@RequestBody Role role){
        return this.roleService.addRole(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Mono<Role> updateRole(@PathVariable(name="id") String id, @RequestBody Role role){
        return this.roleService.updateRole(id,role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public Mono<Void> deleteRole(@PathVariable(name="id") String id){
        return this.roleService.deleteRole(id);
    }
}
