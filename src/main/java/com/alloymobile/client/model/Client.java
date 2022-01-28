package com.alloymobile.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection  = "clients")
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Client implements Serializable {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Email(message = "The email address is invalid.")
    private String email;
    @Size(min = 6,message = "Password must be min 6 characters")
    private String password;
    private String phone;
    private List<Role> roles = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();

}