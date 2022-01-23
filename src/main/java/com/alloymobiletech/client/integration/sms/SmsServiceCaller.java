package com.alloymobiletech.client.integration.sms;

import com.alloymobiletech.client.integration.sms.model.ResponseDTO;
import com.alloymobiletech.client.integration.sms.model.SmsDTO;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SmsServiceCaller {
    private String smsUrl;
    private final WebClient webClient;

    public SmsServiceCaller(Environment env, final WebClient.Builder webclient) {
        this.smsUrl = env.getProperty("client.smsUrl");
        this.webClient = webclient.build();
    }

    public Mono<ResponseDTO> sendToken(String token, SmsDTO dto){
        return this.webClient.post().uri(smsUrl)
                .header(HttpHeaders.AUTHORIZATION,token)
                .body(Mono.just(dto), SmsDTO.class)
                .retrieve()
                .bodyToMono(ResponseDTO.class);
    }
}
