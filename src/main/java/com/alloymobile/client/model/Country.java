package com.alloymobile.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "countries")
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Country {
    private String id;
    private String name;
    private String dialCode;
    private String code;
}
