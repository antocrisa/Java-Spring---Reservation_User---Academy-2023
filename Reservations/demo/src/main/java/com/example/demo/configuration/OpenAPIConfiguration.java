package com.example.demo.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public GroupedOpenApi all() {
        return GroupedOpenApi.builder()
                .displayName("Reservation Services")
                .group("reservations")
                .packagesToScan("com.example.demo")
                .build();
    }

    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reservation Services")
                        .description("A set of reservation services")
                        .version("0.0.1")
                    );
    }
}
