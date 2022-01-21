package com.alloymobiletech.client.integration;

import com.alloymobiletech.client.integration.model.ResponseDTO;
import com.alloymobiletech.client.integration.model.SmsDTO;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SmsServiceCaller {
    private String smsUrl;
    private final WebClient webClient;

    public SmsServiceCaller(Environment env, final WebClient.Builder webclient) {
        this.smsUrl = env.getProperty("client.smsUrl");
        this.webClient = webclient.build();
    }

    public void sendToken(){
        String token = "12345";
        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setNumber("+12893211564");
        smsDTO.setMessage("12345");
        Mono<ResponseDTO> res = this.webClient.post().uri(smsUrl)
                .header(HttpHeaders.AUTHORIZATION,"eyJhbGciOiJIUzUxMiJ9.eyJST0xFX0NMSUVOVCI6InJvbGUiLCJST0xFX0FETUlOIjoicm9sZSIsInN1YiI6ImltdGFwYXNAeWFob28uY29tIiwiaWF0IjoxNjQyNzkyMTg2LCJleHAiOjE2NDI4MjA5ODZ9.F7e_lJyKuznfwjqjeCcEtz8r-2JcECtBo04tUoD4XR2Fix4SITZ-nqj-adScoReQTLrvtlmOdjMLkwpGwC65Ig")
                .body(Mono.just(smsDTO), SmsDTO.class)
                .retrieve()
                .bodyToMono(ResponseDTO.class);
        System.out.println(res.subscribe());
    }
}
