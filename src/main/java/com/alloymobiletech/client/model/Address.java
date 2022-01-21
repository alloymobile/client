package com.alloymobiletech.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    private String id;
    private AddressType type;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Location location;
}
