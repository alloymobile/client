package com.alloymobiletech.client.integration.sms;

import com.alloymobiletech.client.integration.sms.model.ResponseDTO;
import com.alloymobiletech.client.integration.sms.model.SmsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class SmsServiceCaller {
    private String smsUrl;
    private final WebClient webClient;

    public SmsServiceCaller(Environment env, final WebClient.Builder webclient) {
        this.smsUrl = env.getProperty("client.smsUrl");
        this.webClient = webclient.build();
    }

    public Mono<ResponseDTO> sendToken(String token, SmsDTO dto){
        AtomicReference<Long> startTime = new AtomicReference<>();
        Mono<ResponseDTO> res = this.webClient.post().uri(smsUrl)
                .header(HttpHeaders.AUTHORIZATION,token)
                .body(Mono.just(dto), SmsDTO.class)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,(response->{
//                    return Mono.error(new RuntimeException());
//                }))
                .bodyToMono(ResponseDTO.class)
                .flatMap(Mono::just);
//                .doOnSubscribe(x->{
//                    startTime.set(System.nanoTime());
//                    log.info(x.toString());
//                })
//                .doFinally(x->{
//                   final long milliSec = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime.get());
//                   log.info("Time taken is "+milliSec);
//                });
        log.info(""+res.subscribe());
        return res;
    }
}
