package com.enterprise.auth.config;


import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(KeycloakProperties.class)
public class RestClientConfig {

    @Bean
    RestClient restClient(RestClientBuilderConfigurer configurer) {
        return configurer.configure(RestClient.builder()).build();
    }

}
