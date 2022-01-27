package com.alloymobiletech.client.integration.email;

import com.alloymobiletech.client.integration.email.model.EmailDTO;
import com.alloymobiletech.client.integration.sms.model.ResponseDTO;
import com.alloymobiletech.client.model.Client;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EmailServiceCaller {
    private String emailUrl;
    private String siteUrl;
    private final WebClient webClient;

    public EmailServiceCaller(Environment env, final WebClient.Builder webclient) {
        this.emailUrl = env.getProperty("client.emailUrl");
        this.siteUrl = env.getProperty("");
        this.webClient = webclient.build();
    }

    private Mono<ResponseDTO> sendEmail(String token,EmailDTO emailDTO){
        Mono<ResponseDTO> res =  this.webClient.post().uri(emailUrl)
                .header(HttpHeaders.AUTHORIZATION,token)
                .body(Mono.just(emailDTO), EmailDTO.class)
                .retrieve()
                .bodyToMono(ResponseDTO.class);
        res.subscribe();
        return res;
    }

    public Mono<ResponseDTO> sendRegistrationEmailLink(String token,Client dto){
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setToMailAddress(dto.getEmail());
        emailDTO.setSubject("Please verify your registration to use the application");

        String body = "";
        body = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a clicktracking=off href=\"[[URL]]\" target=\"_self\">Verify your registration</a></h3>"
                + "Thank you,<br>"
                + "alloymobile Inc.";

        body = body.replace("[[name]]", dto.getFirstName() + " " + dto.getLastName());
//        String verifyURL = this.siteUrl + "?code=" + dto.getEmailCode();
//        body = body.replace("[[URL]]", verifyURL);
        emailDTO.setBody(body);
        return this.sendEmail(token,emailDTO);
    }
}
