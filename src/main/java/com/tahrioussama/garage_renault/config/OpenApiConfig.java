package com.tahrioussama.garage_renault.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI garageManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Renault Garage Management API")
                        .description("API for managing Renault garages, vehicles, and accessories")
                        .version("1.0.0")
                        .license(new License().name("Renault License")));
    }
}
