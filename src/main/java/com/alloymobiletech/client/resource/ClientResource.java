package com.alloymobiletech.client.resource;

import com.alloymobiletech.client.model.*;
import com.alloymobiletech.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.alloymobiletech.client.config.SecurityConstants.BASE_URL;

@RestController
@Tag(name = "Client", description = "This is a client api related to all operations to a client")
public class ClientResource {

    private final ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all the clients", description = "It gets a list of all the clients in the system")
    @GetMapping(value= BASE_URL +"/clients", produces = "application/json")
    public Flux<Client> getAllClient(){
        return this.clientService.findAllClient();
    }

//    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "404", description = "Client not found") })
    @GetMapping(value = "/clients/{id}", produces = "application/json")
    public Mono<Client> getClientById(@PathVariable(name="id") String id){
        return this.clientService.findClientById(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = BASE_URL +"/clients/{id}")
    public Mono<Client> updateClient(@PathVariable(name="id") String id, @RequestBody Client client){
        return this.clientService.updateClient(id,client);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = BASE_URL +"/clients/{id}")
    public Mono<Void> deleteClient(@PathVariable(name="id") String id){
        return this.clientService.deleteClient(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = BASE_URL +"/clients/{clientId}/roles/{roleId}", produces = "application/json")
    public Mono<Client> addRoleToClient(@PathVariable(name="clientId") String clientId, @PathVariable(name="roleId") String roleId){
        return this.clientService.addRoleToClient(clientId,roleId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = BASE_URL +"/clients/{clientId}/roles/{roleId}", produces = "application/json")
    public Mono<Client> deleteRoleToClient(@PathVariable(name="clientId") String clientId, @PathVariable(name="roleId") String roleId){
        return this.clientService.deleteRoleToClient(clientId,roleId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = BASE_URL +"/clients/{clientId}/addresses", produces = "application/json")
    public Mono<Client> addAddressToClient(@PathVariable(name="clientId") String clientId,@RequestBody Address address){
        return this.clientService.addAddressToClient(clientId,address);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = BASE_URL +"/clients/{clientId}/addresses/{addressId}", produces = "application/json")
    public Mono<Client> updateAddressToClient(@PathVariable(name="clientId") String clientId, @PathVariable(name="addressId") String addressId, @RequestBody Address address){
        return this.clientService.updateAddressToClient(clientId,addressId,address);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = BASE_URL +"/clients/{clientId}/addresses/{addressId}", produces = "application/json")
    public Mono<Client> deleteAddressToClient(@PathVariable(name="clientId") String clientId, @PathVariable(name="addressId") String addressId){
        return this.clientService.deleteAddressToClient(clientId,addressId);
    }

    //New user registration
    @PostMapping(value = "/clients/signup", produces = "application/json")
    public Mono<Client> addClient(@Valid @RequestBody Client client){
        return this.clientService.addClient(client);
    }

    //Login user
    @PostMapping(value="/clients/signin", produces = "application/json")
    public Mono<SignInResponse> clientLogin(@RequestBody SignInRequest authRequest){
        return this.clientService.clientLogin(authRequest);
    }
}
