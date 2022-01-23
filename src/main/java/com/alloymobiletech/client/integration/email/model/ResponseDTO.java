package com.alloymobiletech.client.integration.email.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ResponseDTO {
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private Integer status = 200;
    private String error = "";
    private String message = "";
    private String path = "";
    private String controllerName = "";
    private String serviceName = "";
}
