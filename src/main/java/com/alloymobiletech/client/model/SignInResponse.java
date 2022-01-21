package com.alloymobiletech.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SignInResponse extends Client implements Serializable {
    private String token;

    public SignInResponse(Client client, String token) {
        this.setId(client.getId());
        this.setFirstName(client.getFirstName());
        this.setLastName(client.getLastName());
        this.setEmail(client.getEmail());
        this.setPhone(client.getPhone());
        this.setRoles(client.getRoles());
        this.setAddresses(client.getAddresses());
        this.token = token;
    }
}
