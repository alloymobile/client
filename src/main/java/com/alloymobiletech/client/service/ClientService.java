package com.alloymobiletech.client.service;

import com.alloymobiletech.client.model.*;
import com.alloymobiletech.client.repository.ClientRepository;
import com.alloymobiletech.client.security.TokenProvider;
import com.alloymobiletech.client.utils.GoogleMaps;
import com.alloymobiletech.client.utils.PasswordGenerator;
import com.google.maps.errors.ApiException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService{

    private final ClientRepository clientRepository;

    private final TokenProvider tokenProvider;

    private final PasswordGenerator passwordGenerator;

    private final RoleService roleService;

    private final GoogleMaps googleMaps;

    public ClientService(ClientRepository clientRepository, TokenProvider tokenProvider, PasswordGenerator passwordGenerator, RoleService roleService, GoogleMaps googleMaps) {
        this.clientRepository = clientRepository;
        this.tokenProvider = tokenProvider;
        this.passwordGenerator = passwordGenerator;
        this.roleService = roleService;
        this.googleMaps = googleMaps;
    }

    public Flux<Client> findAllClient(){
        return this.clientRepository.findAll();
    }

    public Mono<Client> findClientById(String id){
        return this.clientRepository.findById(id);
    }

    public Mono<Client> addClient(Client client){
        client.setId(new ObjectId().toString());
        client.setPassword(this.passwordGenerator.encode(client.getPassword()));
        return this.clientRepository.save(client);
    }

    public Mono<Client> updateClient(String id, Client client){
        return this.clientRepository.findById(id).flatMap(f->{
            client.setId(f.getId());
            return this.clientRepository.save(client);
        });
    }

    public Mono<Void> deleteClient(String id){
        return this.clientRepository.deleteById(id);
    }

    public Mono<SignInResponse> clientLogin(SignInRequest authRequest){
        return this.clientRepository.findClientByEmail(authRequest.getEmail())
                .filter(client-> this.passwordGenerator.encode(authRequest.getPassword()).equals(client.getPassword()))
                .map(c-> new SignInResponse(c,this.tokenProvider.generateToken(c)));
    }

    public Mono<Client> addRoleToClient(String clientId, String roleId){
        return this.roleService.findRoleById(roleId).flatMap(r->{
            return this.findClientById(clientId).flatMap(c->{
                c.getRoles().add(r);
                return this.updateClient(c.getId(),c);
            });
        });
    }

    public Mono<Client> deleteRoleToClient(String clientId, String roleId){
        return this.roleService.findRoleById(roleId).flatMap(role->{
            return this.findClientById(clientId).flatMap(c->{
                List<Role> roles = c.getRoles().stream().filter(r->!r.getId().equals(role.getId())).collect(Collectors.toList());
                c.setRoles(roles);
                return this.updateClient(c.getId(),c);
            });
        });
    }

    public Mono<Client> addAddressToClient(String clientId, Address address){
        return this.findClientById(clientId).flatMap(c->{
            address.setId(new ObjectId().toString());
            try {
                c.getAddresses().add(googleMaps.getGeoCodeAddress(address));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.updateClient(c.getId(),c);
        });
    }

    public Mono<Client> updateAddressToClient(String clientId, String addressId, Address address){
        return deleteAddressToClient(clientId, addressId).flatMap(client -> {
           return addAddressToClient(client.getId(), address);
        });
    }

    public Mono<Client> deleteAddressToClient(String clientId, String addressId){
        return this.findClientById(clientId).flatMap(c->{
            List<Address> addresses = c.getAddresses().stream().filter(a->!a.getId().equals(addressId)).collect(Collectors.toList());
            c.setAddresses(addresses);
            return this.updateClient(c.getId(),c);
        });
    }
}
