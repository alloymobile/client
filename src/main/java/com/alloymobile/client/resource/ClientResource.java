package com.alloymobile.client.resource;

import com.alloymobile.client.application.config.SecurityConstants;
import com.alloymobile.client.application.utils.PageData;
import com.alloymobile.client.persistence.model.Address;
import com.alloymobile.client.persistence.model.Client;
import com.alloymobile.client.persistence.model.SignInRequest;
import com.alloymobile.client.persistence.model.SignInResponse;
import com.alloymobile.client.service.client.ClientBinding;
import com.alloymobile.client.service.client.ClientService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@RestController
@Tag(name = "Client", description = "This is a client api related to all operations to a client")
public class ClientResource {

    private final ClientService clientService;

    private final PageData page;

    private final Environment environment;

    public ClientResource(ClientService clientService, PageData page, Environment environment) {
        this.clientService = clientService;
        this.page = page;
        this.environment = environment;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all the clients", description = "It gets a list of all the clients in the system")
    @GetMapping(value= SecurityConstants.BASE_URL +"/clients", produces = "application/json")
    public Mono<Page<Client>> getAllClient(@QuerydslPredicate(root = Client.class,bindings = ClientBinding.class) Predicate predicate
            , @RequestParam(name = "search",required = false) String search
            , @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , @RequestParam(value = "sort", required = false) String sort){
        if(Objects.nonNull(search)){
            predicate = ClientBinding.createSearchQuery(search);
        }
        return this.clientService.findAllClient(predicate,this.page.getPage(page, size, sort));
    }

    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "404", description = "Client not found") })
    @GetMapping(value = SecurityConstants.BASE_URL +"/clients/{id}", produces = "application/json")
    public Mono<Client> getClientById(@PathVariable(name="id") String id){
        return this.clientService.findClientById(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping(value = SecurityConstants.BASE_URL +"/clients/{id}")
    public Mono<Client> updateClient(@PathVariable(name="id") String id, @RequestBody Client client){
        return this.clientService.updateClient(id,client);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = SecurityConstants.BASE_URL +"/clients/{id}")
    public Mono<Void> deleteClient(@PathVariable(name="id") String id){
        return this.clientService.deleteClient(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = SecurityConstants.BASE_URL +"/clients/{clientId}/roles/{roleId}", produces = "application/json")
    public Mono<Client> addRoleToClient(@PathVariable(name="clientId") String clientId, @PathVariable(name="roleId") String roleId){
        return this.clientService.addRoleToClient(clientId,roleId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = SecurityConstants.BASE_URL +"/clients/{clientId}/roles/{roleId}", produces = "application/json")
    public Mono<Client> deleteRoleToClient(@PathVariable(name="clientId") String clientId, @PathVariable(name="roleId") String roleId){
        return this.clientService.deleteRoleToClient(clientId,roleId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(value = SecurityConstants.BASE_URL +"/clients/{clientId}/addresses", produces = "application/json")
    public Mono<Client> addAddressToClient(@PathVariable(name="clientId") String clientId,@RequestBody Address address){
        return this.clientService.addAddressToClient(clientId,address);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping(value = SecurityConstants.BASE_URL +"/clients/{clientId}/addresses/{addressId}", produces = "application/json")
    public Mono<Client> updateAddressToClient(@PathVariable(name="clientId") String clientId, @PathVariable(name="addressId") String addressId, @RequestBody Address address){
        return this.clientService.updateAddressToClient(clientId,addressId,address);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping(value = SecurityConstants.BASE_URL +"/clients/{clientId}/addresses/{addressId}", produces = "application/json")
    public Mono<Client> deleteAddressToClient(@PathVariable(name="clientId") String clientId, @PathVariable(name="addressId") String addressId){
        return this.clientService.deleteAddressToClient(clientId,addressId);
    }

    //New user registration
    @PostMapping(value = SecurityConstants.FREE_URL+"/clients/signup", produces = "application/json")
    public Mono<Client> addClient(@Valid @RequestBody Client client){
        return this.clientService.addClient(client);
    }

    //Login user
    @PostMapping(value=SecurityConstants.FREE_URL+"/clients/signin", produces = "application/json")
    public Mono<SignInResponse> clientLogin(@RequestBody SignInRequest authRequest){
        return this.clientService.clientLogin(authRequest);
    }

    //Login user
    @GetMapping(value=SecurityConstants.FREE_URL+"/clients/linkedin")
    ResponseEntity<Void> linkedinLogin() {
        final String urls = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=78pyv56iocx2e1&redirect_uri=http://localhost:8081/v1/clients/linkedin/signin&state=foobar&scope=r_liteprofile";
        final String url = environment.getProperty("security.oauth2.client.registration.linkedin.auth-url")
                +"?response_type=code&client_id="+environment.getProperty("security.oauth2.client.registration.linkedin.clientId")
                +"&redirect_uri="+"&state=foobar&scope="+environment.getProperty("security.oauth2.client.registration.linkedin.scope");
                return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urls))
                .build();
    }

    //Login user
    @GetMapping(value=SecurityConstants.FREE_URL+"/clients/linkedin/signin")
    public String clientLoginLinkedin(@RequestParam(value = "state") String state, @RequestParam(value = "code") String code){
        return code;
    }


    //Login user
    @GetMapping(value=SecurityConstants.FREE_URL+"/clients/facebook")
    public Mono<String> clientLoginFacebook(ReactiveSecurityContextHolder principal){
        return principal.getContext()
                .map(s->s.getAuthentication().getPrincipal().toString());
    }

}
