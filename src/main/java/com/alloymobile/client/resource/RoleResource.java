package com.alloymobile.client.resource;

import com.alloymobile.client.application.config.SecurityConstants;
import com.alloymobile.client.application.utils.PageData;
import com.alloymobile.client.persistence.model.Role;
import com.alloymobile.client.service.role.RoleBinding;
import com.alloymobile.client.service.role.RoleService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping(SecurityConstants.BASE_URL +"/roles")
@Tag(name = "Role", description = "The role API")
public class RoleResource implements IResource{

    private final RoleService roleService;

    private final PageData page;

    public RoleResource(RoleService roleService, PageData page) {
        this.roleService = roleService;
        this.page = page;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping( produces = "application/json")
    public Mono<Page<Role>> getAllRole(@QuerydslPredicate(root = Role.class,bindings = RoleBinding.class) Predicate predicate
            , @RequestParam(name = "search",required = false) String search
            , @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , @RequestParam(value = "sort", required = false) String sort){
        if(Objects.nonNull(search)){
            predicate = RoleBinding.createSearchQuery(search);
        }
        return this.roleService.findAllRole(predicate,this.page.getPage(page, size, sort));
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
