package com.alloymobiletech.client.integration.email.model;

import lombok.Data;

@Data
public class EmailDTO {
    private String toMailAddress;
    private String subject;
    private String body;
}
