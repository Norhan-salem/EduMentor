package com.asu.EduMentor.controller.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CurrencyRateApiConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://data.fixer.io/api")
                .build();
    }
}
