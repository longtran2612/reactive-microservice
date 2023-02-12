package com.example.movieservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 10:12 AM 18-Jan-23
 * Long Tran
 */
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient getWebClientBuilder(){
        return WebClient.builder().build();
    }
}
