package com.shazzad.auditdemo.others;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build();
    }
}
