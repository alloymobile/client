package com.alloymobile.client.persistence.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection  = "roles")
@Data
public class Role implements Serializable {
    @Id
    private String id;
    private String name;
}
